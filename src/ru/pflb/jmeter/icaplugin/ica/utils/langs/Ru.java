package ru.pflb.jmeter.icaplugin.ica.utils.langs;
import ru.pflb.jmeter.icaplugin.ica.utils.Replacer;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class Ru implements Replacer {
    private static final Logger L = LoggerFactory.getLogger(Ru.class);

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
