// TrainingImage.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocrPlugins.mseOCR;

import net.sourceforge.javaocr.scanner.PixelImage;

import java.util.logging.Logger;

/**
 * Class to hold a training image for a single character. A training image is a
 * representative image for a single character, and is used to determine the
 * likelihood that a given character image is a particular character.
 *
 * @author Ronald B. Cemer
 */
public class TrainingImage extends PixelImage {

    /**
     * This is the maximum variance of aspect ratio between a training image and
     * the actual image segment to be decoded. It is expressed as a fraction of
     * the calculated aspect ratios. Any training image which varies by more
     * than this fraction from the aspect ratio of the character to be decoded,
     * is not used to decode that character.
     */
    public static final float ASPECT_RATIO_TOLERANCE = 0.3f;
    /**
     * This is the maximum variance between the source character's top white
     * space fraction and the training image's top white space fraction. Any
     * training image which varies from the source character by more than this
     * tolerance, will not be considered a candidate.
     */
    public static final float TOP_WHITE_SPACE_FRACTION_TOLERANCE = 0.3f;
    /**
     * This is the maximum variance between the source character's bottom white
     * space fraction and the training image's bottom white space fraction. Any
     * training image which varies from the source character by more than this
     * tolerance, will not be considered a candidate.
     */
    public static final float BOTTOM_WHITE_SPACE_FRACTION_TOLERANCE = 0.3f;
    private static final Logger LOG = Logger.getLogger(TrainingImage.class.getName());
    /**
     * Fraction of the row height which is occupied by complete whitespace above
     * the character.
     */
    public final float topWhiteSpaceFraction;
    /**
     * Fraction of the row height which is occupied by complete whitespace below
     * the character.
     */
    public final float bottomWhiteSpaceFraction;
    private int myMaxX;
    /// private int xShift;
/// private int yShift;
    private int myMaxY;

    /**
     * Construct a new <code>TrainingImage</code> object from an array of gray
     * scale pixels.
     *
     * @param pixels                 An array of pixels in the range 0-255.
     * @param width                  The width of the image.
     * @param height                 The height of the image.
     * @param topWhiteSpacePixels    The number of scan lines at the top of this
     *                               character cell which are all white. These are excluded from the width and
     *                               height of the training image.
     * @param bottomWhiteSpacePixels The number of scan lines at the bottom of
     *                               this character cell which are all white. These are excluded from the
     *                               width and height of the training image.
     */
    public TrainingImage(
            int[] pixels,
            int width,
            int height,
            int topWhiteSpacePixels,
            int bottomWhiteSpacePixels) {

        super(pixels, width, height);
        int rowHeight
                = topWhiteSpacePixels + height + bottomWhiteSpacePixels;
        topWhiteSpaceFraction
                = (float) topWhiteSpacePixels / (float) rowHeight;
        bottomWhiteSpaceFraction
                = (float) bottomWhiteSpacePixels / (float) rowHeight;
        myMaxX = width - 1;
        myMaxY = height - 1;
/// xShift = width/20;
/// yShift = height/20;
    }

    /**
     * Calculate the error factor between a block of pixels and our image.
     *
     * @param theirPixels An array of grayscale pixels which contains the block
     *                    to be compared This should be in binary format, with each pixel having a
     *                    value of either <code>0</code> or <code>255</code>.
     * @param w           The width of the pixel array.
     * @param h           The height of the pixel array.
     * @param x1          The position of the left border of the rectangle to be
     *                    compared.
     * @param y1          The position of the top border of the rectangle to be compared.
     * @param x2          The position of the right border of the rectangle to be
     *                    compared. Note that pixels up to, but not including this position, will
     *                    be compared.
     * @param y2          The position of the bottom border of the rectangle to be
     *                    compared. Note that pixels up to, but not including this position, will
     *                    be compared.
     * @return A <code>double</code> representing the average per-pixel mean
     * square error. Lower numbers indicate a better match.
     */
    public double calcMSE(int[] theirPixels, int w, int h, int x1, int y1, int x2, int y2) {
        int theirXRange = Math.max((x2 - x1) - 1, 1);
        int theirYRange = Math.max((y2 - y1) - 1, 1);
        int theirNPix = (theirXRange + 1) * (theirYRange + 1);
        boolean firstCompare = true;
        int myX, myY;
        long thisError, totalError;
        long minError = Long.MAX_VALUE;
        int myLineIdx, theirIdx;
///for (int yo = -yShift; yo <= yShift; yo++) {
/// for (int xo = -xShift; xo <= xShift; xo++) {
        totalError = 0L;
        for (int theirY = y1, yScan = 0 /*yo */;
             theirY < y2; theirY++, yScan++) {
            theirIdx = (theirY * w) + x1;
            myY = ((yScan * myMaxY) / theirYRange);
            myLineIdx = myY * width;
            for (int theirX = x1, xScan = 0 /*xo */;
                 theirX < x2; theirX++, theirIdx++, xScan++) {
                myX = ((xScan * myMaxX) / theirXRange);
                if ((myX < 0) || (myX > myMaxX) || (myY < 0) || (myY > myMaxY)) {
                    thisError = theirPixels[theirIdx] - 255;
                } else {
                    thisError = theirPixels[theirIdx] - pixels[myLineIdx + myX];
                }
                totalError += (thisError * thisError);
            }
        }
        if ((firstCompare) || (totalError < minError)) {
            minError = totalError;
        }
        firstCompare = false;
/// }
///}
        return Math.sqrt((double) minError) / (double) theirNPix;
    }
}
