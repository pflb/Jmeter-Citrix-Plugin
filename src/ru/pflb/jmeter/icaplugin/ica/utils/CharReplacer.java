package ru.pflb.jmeter.icaplugin.ica.utils;

import ru.pflb.jmeter.icaplugin.ica.utils.langs.Ru;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public abstract class CharReplacer {
    private static final Logger L = Logger.getLogger(CharReplacer.class.getCanonicalName());

    private static final ArrayList<Replacer> mReplacers = new ArrayList<>();

    static {
        addReplacer(new Ru());
    }

    public static void addReplacer(Replacer replacer) {
        mReplacers.add(replacer);
    }

    public static char replaceChar(char c) {
        char result = c;
        for (Replacer replacer : mReplacers) {
            result = replacer.replace(c);
            if (c != result)
                break;
        }
        return result;
    }
}
