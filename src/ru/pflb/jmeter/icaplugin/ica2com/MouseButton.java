package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.ComEnum;

/**
 * Mouse buttons
 */
public enum MouseButton implements ComEnum {

    MouseButtonLeft(1),
    MouseButtonRight(2),
    MouseButtonMiddle(4),;

    private final int value;

    MouseButton(int value) {
        this.value = value;
    }

    public int comEnumValue() {
        return value;
    }
}
