// CharacterRange.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocrPlugins.mseOCR;

import java.util.logging.Logger;

/**
 * Class to represent a range of character codes.
 *
 * @author Ronald B. Cemer
 */
public class CharacterRange {

    private static final Logger LOG = Logger.getLogger(CharacterRange.class.getName());
    /**
     * The minimum character value in this range.
     */
    public final int min;
    /**
     * The maximum character value in this range.
     */
    public final int max;

    /**
     * Construct a new <code>CharacterRange</code> object for a range of
     * character codes.
     *
     * @param min The minimum character value in this range.
     * @param max The maximum character value in this range.
     */
    public CharacterRange(int min, int max) {
        if (min > max) {
            throw new InternalError("max must be >= min");
        }
        this.min = min;
        this.max = max;
    }

    /**
     * Construct a new <code>CharacterRange</code> object for a single character
     * code.
     *
     * @param c The character code for this range. This code will be both the
     *          minimum and maximum for this range.
     */
    public CharacterRange(int c) {
        this(c, c);
    }
}
