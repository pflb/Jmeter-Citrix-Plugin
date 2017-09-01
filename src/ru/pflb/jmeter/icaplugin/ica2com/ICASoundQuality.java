package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.ComEnum;

/**
 * List of ICA sound qualities
 */
public enum ICASoundQuality implements ComEnum {

    SoundQualityNone(-1),
    SoundQualityHigh(0),
    SoundQualityMedium(1),
    SoundQualityLow(2),;

    private final int value;

    ICASoundQuality(int value) {
        this.value = value;
    }

    public int comEnumValue() {
        return value;
    }
}
