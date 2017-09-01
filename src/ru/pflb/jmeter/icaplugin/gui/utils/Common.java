package ru.pflb.jmeter.icaplugin.gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public abstract class Common {
    public static final int vertSeparator = 10;
    public static final int buttonsVertSeparator = 3;

    public static void setButtonSize(JComponent c) {
        Dimension d = new Dimension(120, c.getPreferredSize().height);
        c.setMinimumSize(d);
        c.setPreferredSize(d);
        c.setMaximumSize(d);
    }

    public static void setEditSize(JComponent c) {
        Dimension d = new Dimension(300, c.getPreferredSize().height);
        c.setMinimumSize(d);
        c.setPreferredSize(d);
        c.setMaximumSize(d);
    }
}
