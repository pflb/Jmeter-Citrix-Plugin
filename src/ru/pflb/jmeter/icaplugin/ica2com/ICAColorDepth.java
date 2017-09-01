package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.ComEnum;

/**
 * List of ICA color depths
 */
public enum ICAColorDepth implements ComEnum {

    Color16(1),
    Color256(2),
    Color16Bit(4),
    Color24Bit(8),;

    private final int value;

    ICAColorDepth(int value) {
        this.value = value;
    }

    public int comEnumValue() {
        return value;
    }
}
