package ru.pflb.jmeter.icaplugin.gui.utils;

import com.sun.istack.internal.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Кнопка, позваляющая выбрать файл/директорию
 *
 * @author Иванов Владимир.
 * @version 1.0.0
 */
public class BrowseButton extends JButton {
    /**
     * Последний используемый путь
     */
    private static String mPath = null;
    /**
     * Указывает, выбрать файл или директорию
     */
    private final boolean mDirectoryOnly;

    /**
     * Конструктор кнопки.
     *
     * @param name      Заголовок кнопки
     * @param directory Выбрать файл (false) или директорию (true)
     */
    public BrowseButton(String name, boolean directory) {
        super(name);
        mDirectoryOnly = directory;
        Common.setButtonSize(this); //Устанавливаем размер кнопки
        setToolTipText("Click to browse and select file/folder");
    }

    /**
     * Вызвать диалог выбора файла/папки
     *
     * @return Файл/папку, выбранный в обозревателе или null, если отменено
     */
    public
    @Nullable
    File browse() {
        JFileChooser fc;
        if (mPath != null) {//проверяем, есть ли сохраненный путь
            fc = new JFileChooser(new File(mPath));
        } else {
            fc = new JFileChooser();
        }

        if (mDirectoryOnly)//проверяем, нужно ли выбрать директорию или файл
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//только директории
        else {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("ICA file", "ica");//фильтр по расширению
            fc.setFileFilter(filter);
        }
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {//если подтвержден выбор
            return fc.getSelectedFile();
        } else
            return null;
    }
}
