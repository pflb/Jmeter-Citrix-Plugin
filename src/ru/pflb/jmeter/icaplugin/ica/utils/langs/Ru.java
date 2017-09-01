package ru.pflb.jmeter.icaplugin.ica.utils.langs;

import ru.pflb.jmeter.icaplugin.ica.utils.Replacer;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class Ru implements Replacer {
    private static final Logger L = Logger.getLogger(Ru.class.getCanonicalName());

    private static final HashMap<Character, Character> mKeys = new HashMap<>();

    static {
        mKeys.put('Ё', (char) 168);
        mKeys.put('ё', (char) 184);
        mKeys.put('№', (char) 185);
        char c;
        for (int i = 192; i < 256; ++i) {
            c = (char) (848 + i);
            mKeys.put(c, (char) i);
        }
    }

    public Ru() {
    }

    @Override
    public char replace(char c) {
        if (mKeys.containsKey(c))
            return mKeys.get(c);
        return c;
    }
}
