// DocumentScannerListener.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.scanner;

/**
 * Listener interface for the DocumentScanner utility class.
 *
 * @author Ronald B. Cemer
 */
public interface DocumentScannerListener {

    /**
     * This method is called when scanning of the document begins.
     *
     * @param pixelImage The <code>PixelImage</code> containing the document
     *                   which is being scanned.
     */
    void beginDocument(PixelImage pixelImage);

    /**
     * This method is called when scanning of a row of text within the document
     * begins.
     *
     * @param pixelImage The <code>PixelImage</code> containing the document
     *                   which is being scanned.
     * @param y1         The y position of the top pixel row of the row of text that is
     *                   being scanned.
     * @param y2         The y position of the pixel row immediately below the last
     *                   pixel row of the row of text that is being scanned. This is always one
     *                   more than the last pixel row of the row of text that is being scanned.
     */
    void beginRow(PixelImage pixelImage, int y1, int y2);

    /**
     * This method is called to process a character or group of characters
     * within the document.<p>
     * Note that when two or more characters are connected together without
     * enough whitespace to determine where the first one ends and the next one
     * begins, the document scanner may not be able to split the characters
     * properly. If this occurs, then two or more characters may be included in
     * a single call to this method. In this case, it is the responsibility of
     * this method to process all characters included in the area passed into
     * this method.
     *
     * @param pixelImage The <code>PixelImage</code> containing the document
     *                   which is being scanned.
     * @param x1         The x position of the first pixel column of the character or
     *                   group of characters being scanned.
     * @param y1         The y position of the top pixel row of the row of text that is
     *                   being scanned.
     * @param x2         The x position of the pixel column immediately to the right of
     *                   the last pixel column of the character or group of characters being
     *                   scanned. This is always one more than the last pixel column of the
     *                   character or group of characters that is being scanned.
     * @param y2         The y position of the pixel row immediately below the last
     *                   pixel row of the row of text that is being scanned. This is always one
     *                   more than the last pixel row of the row of text that is being scanned.
     * @param rowY1      The y position of the top scan line of the row.
     * @param rowY2      The y position of the scan line immediately below the bottom
     *                   of the row.
     */
    void processChar(PixelImage pixelImage, int x1, int y1, int x2, int y2, int rowY1, int rowY2);

    /**
     * This method is called to process a space within the document.<p>
     * A space is simply an area of whitespace between two characters that is
     * deemed by the document scanner to be wide enough to represent a space
     * character.
     *
     * @param pixelImage The <code>PixelImage</code> containing the document
     *                   which is being scanned.
     * @param x1         The x position of the first pixel column of the character or
     *                   group of characters being scanned.
     * @param y1         The y position of the top pixel row of the row of text that is
     *                   being scanned.
     * @param x2         The x position of the pixel column immediately to the right of
     *                   the last pixel column of the character or group of characters being
     *                   scanned. This is always one more than the last pixel column of the
     *                   character or group of characters that is being scanned.
     * @param y2         The y position of the pixel row immediately below the last
     *                   pixel row of the row of text that is being scanned. This is always one
     *                   more than the last pixel row of the row of text that is being scanned.
     */
    void processSpace(PixelImage pixelImage, int x1, int y1, int x2, int y2);

    /**
     * This method is called when scanning of a row of text within the document
     * is complete.
     *
     * @param pixelImage The <code>PixelImage</code> containing the document
     *                   which is being scanned.
     * @param y1         The y position of the top pixel row of the row of text that is
     *                   being scanned.
     * @param y2         The y position of the pixel row immediately below the last
     *                   pixel row of the row of text that is being scanned. This is always one
     *                   more than the last pixel row of the row of text that is being scanned.
     */
    void endRow(PixelImage pixelImage, int y1, int y2);

    /**
     * This method is called when scanning of the document is complete.
     *
     * @param pixelImage The <code>PixelImage</code> containing the document
     *                   which is being scanned.
     */
    void endDocument(PixelImage pixelImage);
}
