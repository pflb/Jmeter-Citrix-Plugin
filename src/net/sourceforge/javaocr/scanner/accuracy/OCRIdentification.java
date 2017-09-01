// OCRIdentification.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// Modified by William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.scanner.accuracy;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Provides a data structure to manage an OCR character recognition attempt.
 *
 * @author William Whitney
 */
public class OCRIdentification {

    private static final Logger LOG = Logger.getLogger(OCRIdentification.class.getName());
    private OCRComp ocrType;
    private ArrayList<Character> chars;
    private ArrayList<Double> identErrors;

    public OCRIdentification(OCRComp ocrType) {
        chars = new ArrayList<Character>();
        identErrors = new ArrayList<Double>();
        this.ocrType = ocrType;
    }

    public void addChar(char newChar, double identError) {
        this.chars.add(newChar);
        this.identErrors.add(identError);
    }

    public int getNumChars() {
        return chars.size();
    }

    public char getCharIdx(int idx) {
        return chars.get(idx);
    }

    public double getIdentErrorIdx(int idx) {
        return identErrors.get(idx);
    }

    public OCRComp getOCRType() {
        return ocrType;
    }

    @Override
    public String toString() {
        String retStr = "----- OCRIdentification  -----\n";
        retStr += "OCR Type: " + ocrType + "\n";
        for (int i = 0; i < this.chars.size(); i++) {
            retStr += "Char: " + this.chars.get(i) + " " + this.identErrors.get(i) + "\n";
        }

        return retStr;
    }
}
