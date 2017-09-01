// PixelImage.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.scanner;

import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.util.logging.Logger;

/**
 * Class to contain a pixel representation of an image.
 *
 * @author Ronald B. Cemer
 */
public class PixelImage {
    // 10-tap, lowpass Finite Impulse Response (FIR) filter.

    protected static final float[] FILTER_FIR_COEFFS
            = {
            0.05001757311983922f,
            -0.06430830829693616f,
            -0.0900316316157106f,
            0.1500527193595177f,
            0.45015815807855303f,
            0.45015815807855303f,
            0.1500527193595177f,
            -0.0900316316157106f,
            -0.06430830829693616f,
            0.05001757311983922f,};
    private static final Logger LOG = Logger.getLogger(PixelImage.class.getName());
    /**
     * An array of pixels. This can be in RGBA or grayscale. By default, it is
     * RGBA, but if the <code>toGrayScale()</code> method has been called, each
     * pixel will be in the range of 0-255 grayscale.
     */
    public final int[] pixels;
    /**
     * Width of the image, in pixels.
     */
    public final int width;
    /**
     * Height of the image, in pixels.
     */
    public final int height;
    /**
     * Total number of pixels in the image (<code>width*height</code>).
     */
    public final int npix;
    /**
     * Aspect ratio of the image (<code>width/height</code>).
     */
    public final float aspectRatio;

    /**
     * Construct a new <code>PixelImage</code> object from an array of pixels.
     *
     * @param pixels An array of pixels. This can be in RGBA or grayscale. By
     *               default, it is RGBA, but if the <code>toGrayScale()</code> method has
     *               been called, each pixel will be in the range of 0-255 grayscale.
     * @param width  Width of the image, in pixels.
     * @param height Height of the image, in pixels.
     */
    public PixelImage(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        npix = width * height;
        aspectRatio = ((float) width) / ((float) height);
    }

    /**
     * Construct a new <code>PixelImage</code> object from an
     * <code>Image</code>.
     *
     * @param image An <code>Image</code> from which to get the pixels. The
     *              image must be fully loaded. Use a <code>MediaTracker</code> to ensure
     *              this, if necessary.
     */
    public PixelImage(Image image) {
        width = image.getWidth(null);
        height = image.getHeight(null);
        npix = width * height;
        aspectRatio = ((float) width) / ((float) height);
        pixels = new int[npix];
        PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try {
            grabber.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int rgbToGrayScale(int pix) {
        int r = (pix >> 16) & 0xff;
        int g = (pix >> 8) & 0xff;
        int b = pix & 0xff;
        int Y = ((r * 306) + (g * 601) + (b * 117)) >> 10;
        if (Y < 0) {
            Y = 0;
        } else if (Y > 255) {
            Y = 255;
        }
        return Y;
    }

    public Image rgbToImage(int[] rgbPixels, int width, int height, Component comp) {
        return comp.createImage(new MemoryImageSource(width, height, rgbPixels, 0, width));
    }

    /**
     * Get the index of a pixel at a specific <code>x,y</code> position.
     *
     * @param x The pixel's x position.
     * @param y The pixel's y position.
     * @return The pixel index (the index into the <code>pixels</code> array) of
     * the pixel.
     */
    public final int getPixelIndex(int x, int y) {
        return (y * width) + x;
    }

    /**
     * Get the value of a pixel at a specific <code>x,y</code> position.
     *
     * @param x The pixel's x position.
     * @param y The pixel's y position.
     * @return The value of the pixel.
     */
    public final int getPixel(int x, int y) {
        return pixels[(y * width) + x];
    }

    /**
     * Convert all pixels to grayscale from RGB or RGBA. Do not call this method
     * if the pixels are not currently RGB or RGBA.
     *
     * @param normalize <code>true</code> to normalize the image after
     *                  converting to grayscale, such that the darkest pixel in the image is all
     *                  black and the lightest pixel in the image is all white.
     */
    public final void toGrayScale(boolean normalize) {
        if (npix == 0) {
            return;
        }
        if (!normalize) {
            for (int i = 0; i < npix; i++) {
                pixels[i] = rgbToGrayScale(pixels[i]);
            }
        } else {
            int pix;
            pixels[0] = pix = rgbToGrayScale(pixels[0]);
            int min = pix, max = pix;
            for (int i = 1; i < npix; i++) {
                pixels[i] = pix = rgbToGrayScale(pixels[i]);
                min = Math.min(min, pix);
                max = Math.max(max, pix);
            }
            int range = max - min;
            if (range < 1) {
                for (int i = 0; i < npix; i++) {
                    pixels[i] = 255;
                }
            } else {
                for (int i = 0; i < npix; i++) {
                    pixels[i]
                            = Math.min(255,
                            Math.max(0,
                                    ((pixels[i]
                                            - min) * 255) / range));
                }
            }
        }
    }

    public final int[] grayScaleToRGB(int[] pixels) {
        int[] newPixels = new int[pixels.length];
        for (int i = 0; i < newPixels.length; i++) {
            int pix = pixels[i] & 0xff;
            newPixels[i] = pix | (pix << 8) | (pix << 16) | 0xff000000;
        }
        return newPixels;
    }

    public final void filter() {
        filter(pixels, width, height);
    }

    public final void filter(int[] pixels, int width, int height) {
        float[] firSamples = new float[FILTER_FIR_COEFFS.length];
        float c;
        int lastPos = firSamples.length - 1;
        // Filter horizontally.
        for (int y = 0; y < height; y++) {
            for (int i = 0; i < firSamples.length; i++) {
                firSamples[i] = 255.0f;
            }
            int outX = -(firSamples.length / 2);
            for (int x = 0; x < width; x++, outX++) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = getPixel(x, y);
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                if (outX >= 0) {
                    pixels[getPixelIndex(outX, y)] = (int) c;
                }
            }
            while (outX < width) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = 255.0f;
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                pixels[getPixelIndex(outX, y)] = (int) c;
                outX++;
            }
        }
        // Filter vertically.
        for (int x = 0; x < width; x++) {
            for (int i = 0; i < firSamples.length; i++) {
                firSamples[i] = 255.0f;
            }
            int outY = -(firSamples.length / 2);
            for (int y = 0; y < height; y++, outY++) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = getPixel(x, y);
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                if (outY >= 0) {
                    pixels[getPixelIndex(x, outY)] = (int) c;
                }
            }
            while (outY < height) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = 255.0f;
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                pixels[getPixelIndex(x, outY)] = (int) c;
                outY++;
            }
        }
    }
}
