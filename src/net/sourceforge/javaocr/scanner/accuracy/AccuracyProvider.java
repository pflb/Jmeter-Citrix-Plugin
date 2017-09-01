// AccuracyProvider.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// Modified by William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.scanner.accuracy;

/**
 * Interface to be used by OCR recognizers to accept an accuracy listener to
 * report information to.
 *
 * @author William Whitney
 */
public interface AccuracyProvider {

    void acceptAccuracyListener(AccuracyListener listener);
}
