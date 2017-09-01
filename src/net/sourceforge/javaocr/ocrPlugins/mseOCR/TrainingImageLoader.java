// TrainingImageLoader.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// Modified by William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocrPlugins.mseOCR;

import net.sourceforge.javaocr.scanner.DocumentScanner;
import net.sourceforge.javaocr.scanner.DocumentScannerListenerAdaptor;
import net.sourceforge.javaocr.scanner.PixelImage;

import java.awt.*;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Utility class to load an image file, break it into individual characters, and
 * create and store multiple TrainingImage objects from the characters. The
 * source image must contain a range of characters specified in the character
 * range passed into the load() method.
 *
 * @author Ronald B. Cemer
 */
public class TrainingImageLoader extends DocumentScannerListenerAdaptor {

    private static final Logger LOG = Logger.getLogger(TrainingImageLoader.class.getName());
    private int charValue = 0;
    private HashMap<Character, ArrayList<TrainingImage>> dest;
    private boolean debug = false;
    private DocumentScanner documentScanner = new DocumentScanner();

    /**
     * @return The <code>DocumentScanner</code> instance that is used to load
     * the training images. This is useful if the caller wants to adjust some of
     * the scanner's parameters.
     */
    public DocumentScanner getDocumentScanner() {
        return documentScanner;
    }

    /**
     * Load an image containing training characters, break it up into
     * characters, and build a training set. Each entry in the training set (a
     * <code>Map</code>) has a key which is a <code>Character</code> object
     * whose value is the character code. Each corresponding value in the
     * <code>Map</code> is an <code>ArrayList</code> of one or more
     * <code>TrainingImage</code> objects which contain images of the character
     * represented in the key.
     *
     * @param component     A <code>Component</code> compatible with the image. This
     *                      is used to instantiate a <code>MediaTracker</code> to wait for the image
     *                      to load.
     * @param imageFilename The filename of the image to load.
     * @param charRange     A <code>CharacterRange</code> object representing the
     *                      range of characters which is contained in this image.
     * @param dest          A <code>Map</code> which gets loaded with the training data.
     *                      Multiple calls to this method may be made with the same <code>Map</code>
     *                      to populate it with the data from several training images.
     * @throws IOException
     */
    public void load(Component component, String imageFilename, CharacterRange charRange, HashMap<Character, ArrayList<TrainingImage>> dest)
            throws IOException {

        ImageProducer imageProducer = (ImageProducer) (new File(imageFilename).toURL().getContent());
        Image image = Toolkit.getDefaultToolkit().createImage(imageProducer);
        if (image == null) {
            throw new IOException("Cannot find training image file: " + imageFilename);
        }
        load(component, image, charRange, dest, imageFilename);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void load(
            Component component,
            Image image,
            CharacterRange charRange,
            HashMap<Character, ArrayList<TrainingImage>> dest,
            String imageFilename)
            throws IOException {

        MediaTracker mt = new MediaTracker(component);
        mt.addImage(image, 0);
        try {
            mt.waitForAll();
        } catch (InterruptedException ex) {
        }
        PixelImage pixelImage = new PixelImage(image);
        pixelImage.toGrayScale(true);
        pixelImage.filter();
        charValue = charRange.min;
        this.dest = dest;
        documentScanner.scan(pixelImage, this, 0, 0, 0, 0);
        if (charValue != (charRange.max + 1)) {
            throw new IOException(
                    "Expected to decode " + ((charRange.max + 1) - charRange.min)
                            + " characters but actually decoded " + (charValue - charRange.min)
                            + " characters in training: " + imageFilename);
        }
    }

    @Override
    public void processChar(PixelImage pixelImage, int x1, int y1, int x2, int y2, int rowY1, int rowY2) {

        if (debug) {
            System.out.println(
                    "TrainingImageLoader.processChar: \'"
                            + (char) charValue + "\' " + x1 + "," + y1 + "-" + x2 + "," + y2);
        }
        int w = x2 - x1;
        int h = y2 - y1;
        int[] pixels = new int[w * h];
        for (int y = y1, destY = 0; y < y2; y++, destY++) {
            System.arraycopy(pixelImage.pixels, (y * pixelImage.width) + x1, pixels, destY * w, w);
        }
        if (debug) {
            for (int y = 0, idx = 0; y < h; y++) {
                for (int x = 0; x < w; x++, idx++) {
                    System.out.print((pixels[idx] > 0) ? ' ' : '*');
                }
                System.out.println();
            }
            System.out.println();
        }
        Character chr = new Character((char) charValue);
        ArrayList<TrainingImage> al = dest.get(chr);
        if (al == null) {
            al = new ArrayList<TrainingImage>();
            dest.put(chr, al);
        }
        al.add(new TrainingImage(pixels, w, h, y1 - rowY1, rowY2 - y2));
        charValue++;
    }
}
