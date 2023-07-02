package ru.pflb.jmeter.icaplugin.ica.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.0.1
 * @autor lines91
 */

public class Interaction {
    private static final Logger L = LoggerFactory.getLogger(Interaction.class);

    public enum Type {
        Keyboard, MouseClick, MouseMove
    }

    public enum Command {
        //TODO while - wend, if - endif, repeat count - endr
        KeyUp, KeyDown, MouseMove, MouseDown, MouseUp,
        MouseDClick,//TODO notNeed
        SleepTime, StartTag, EndTag, Screenshot, OCR
    }

}
