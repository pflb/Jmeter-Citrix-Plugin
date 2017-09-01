package ru.pflb.jmeter.icaplugin.gui.utils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Панель с настройками Citrix подключения (разрешение, цветность и т.д.).
 *
 * @author Иванов Владимир.
 * @version 0.1.0
 */
public class CitrixPanel extends JPanel {
    /**
     * Указывает, сохранять ли скриншоты
     */
    private final JCheckBox jSaveScreenshots;
    /**
     * Кнопка выбора пути к папке для сохранения скриншотов
     */
    private final BrowseButton jBrowse;
    /**
     * Разрешение сессии при воспроизведение
     */
    private final JTextField jResolution;
    /**
     * Выбор количества цветов
     */
    private final JComboBox<String> jColor;
    /**
     * Указывает, нужна ли новая сессия при каждой итерации
     */
    private final JCheckBox jNewSession;
    private final JCheckBox jReplayMode;
    /**
     * Путь для сохранения скриншотов
     */
    private JTextField jScreenshotPath;
    /**
     * Режим отображения сессии
     */
    private JRadioButton jOMNormal, jOMWindowless;
    /**
     * Указывает, воспроизводить ли задержки
     */
    private JCheckBox jSleepTimes;
    /**
     * Множитель задержек
     */
    private JTextArea jSleepMultiple;

    /**
     * Конструктор панели
     */
    public CitrixPanel() {
        super();
        setBorder(new TitledBorder(new EtchedBorder(), "Replay settings"));//Задаем рамку
        setLayout(new GridBagLayout());//задаем layout
        GridBagConstraints g = new GridBagConstraints();//создаем constraint для layout

        g.gridx = g.gridy = 0;//Обнуляем позицию (левый верхний угол)
        g.anchor = GridBagConstraints.FIRST_LINE_START;
        jSaveScreenshots = new JCheckBox("Save screenshots", true);
        jSaveScreenshots.setToolTipText("If checked, screenshots will be saved while recording");
        jScreenshotPath = new JTextField("");
        Common.setEditSize(jScreenshotPath);//задаем размер поля
        jScreenshotPath.setToolTipText("Folder, where screenshots will be saved");
        jBrowse = new BrowseButton("Browse", true);//создаем кнопку Обзор

        add(jSaveScreenshots, g);//Добавляем элементт
        g.gridy++;//Переходим на следующую строку
        add(new JLabel("Screenshot folder"), g);

        g.gridx++;
        g.ipadx = 0;
        add(jScreenshotPath, g);

        g.gridx++;
        g.weightx = 1;
        g.ipadx = 0;
        jBrowse.setPreferredSize(new Dimension(jBrowse.getPreferredSize().width, 20));
        add(jBrowse, g);

        g.gridy++;
        g.gridx = 0;
        g.weightx = 0;
        g.ipadx = 50;
        add(new JLabel("Resolution (WxH)"), g);//Разрешение
        g.ipadx = 0;

        g.gridx++;
        jResolution = new JTextField("800x600");//Разрешение по умолчанию
        Common.setEditSize(jResolution);
        jResolution.setToolTipText("Specify window width and height");
        add(jResolution, g);
        g.weightx = 0;

        g.gridx++;
        jColor = new JComboBox<>(new String[]{"Color16", "Color256", "Color16Bit", "Color24Bit"});//Выбор режима цветопередачи
        jColor.setSelectedIndex(3);
        jColor.setPreferredSize(new Dimension(jColor.getPreferredSize().width, 20));
        Common.setButtonSize(jColor);
        jColor.setToolTipText("Specify Citrix Color Depth");
        add(jColor, g);

        g.gridy++;
        g.gridx = 0;
        g.ipadx = 50;
        add(new JLabel("Output mode"), g);
        g.ipadx = 0;

        //Создание переключателей режима отображения
        jOMNormal = new JRadioButton("Normal", true);
        jOMNormal.setToolTipText("If selected, citrix client will be run in normal mode");
        jOMWindowless = new JRadioButton("Windowless", false);
        jOMWindowless.setToolTipText("If selected, citrix client will be run without any GUI, so you can`t see application");
        Box b = Box.createHorizontalBox();
        b.add(jOMNormal);
        b.add(jOMWindowless);

        g.gridx++;
        add(b, g);

        g.gridy++;
        g.gridx = 0;

        //Создание настроек задержек
        jSleepTimes = new JCheckBox("Use sleep times", true);
        jSleepTimes.setToolTipText("If checked, sleep times will be replayed");
        jSleepMultiple = new JTextArea("1.0");
        jSleepMultiple.setToolTipText("The multiple for sleep times");
        Common.setEditSize(jSleepMultiple);
        add(jSleepTimes, g);

        g.gridx++;
        add(jSleepMultiple, g);

        g.gridx = 2;
        g.gridy = 0;
        jReplayMode = new JCheckBox("Replay mode", true);
        jReplayMode.setToolTipText("Lock window input where starting new session");
        add(jReplayMode, g);

        //Новая сессия при каждой итерации
        g.gridy = 0;
        g.gridx = 1;
        jNewSession = new JCheckBox("New session each iteration", true);
        jNewSession.setToolTipText("On new iteration, make new Citrix session");
        add(jNewSession, g);

        makeListeners();//Устанавливаем листенеры
    }

    /**
     * Задает листенеры графическим элементам
     */
    private void makeListeners() {
        jSaveScreenshots.addActionListener(new ActionListener() {
            /**
             * Листенер нажатия на чекбокс сохранения скриншотов
             *
             * @param e сведения о событии
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //Включаем/отключаем поле ввода папки для скриншотов и кнопку Обзор
                jScreenshotPath.setEnabled(jSaveScreenshots.isSelected());
                jBrowse.setEnabled(jSaveScreenshots.isSelected());
            }
        });

        jBrowse.addActionListener(new ActionListener() {
            /**
             * Листенер нажатия на кнопку Обзор
             * @param e сведения о событии
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = jBrowse.browse();//получаем файл
                if (f != null) {//если не нажата отмена,
                    jScreenshotPath.setText(f.getAbsolutePath());//устанавливаем путь в поле ввода
                }
            }
        });

        jOMNormal.addActionListener(new ActionListener() {
            /**
             * Листенер нажатия на переключатель режима отображения Нормальный
             * @param e сведения о событии
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jOMNormal.isSelected()) {
                    jOMWindowless.setSelected(false);
                } else {
                    jOMNormal.setSelected(true);
                }
            }
        });

        jOMWindowless.addActionListener(new ActionListener() {
            /**
             * Листенер нажатия на переключатель режима отображения Без отображения
             * @param e сведения о событии
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jOMWindowless.isSelected()) {
                    jOMNormal.setSelected(false);
                } else {
                    jOMWindowless.setSelected(true);
                }
            }
        });

        jSleepTimes.addChangeListener(new ChangeListener() {
            /**
             * Листенер нажатия на переключатель Воспроизводить задержки
             * @param e сведения о событии
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                jSleepMultiple.setEnabled(jSleepTimes.isSelected());
            }
        });
    }

    /**
     * Очистка GUI
     */
    public void clearGui() {
        jSaveScreenshots.setSelected(true);
        jResolution.setText("800x600");
        jColor.setSelectedIndex(3);
        jScreenshotPath.setText("");
        jOMNormal.setSelected(true);
        jOMWindowless.setSelected(false);
    }

    public boolean getSaveScreenshots() {
        return jSaveScreenshots.isSelected();
    }

    public void setSaveScreenshots(boolean saveScreenshots) {
        jSaveScreenshots.setSelected(saveScreenshots);
        jScreenshotPath.setEnabled(saveScreenshots);
        jBrowse.setEnabled(saveScreenshots);
    }

    public String getScreenshotPath() {
        return jScreenshotPath.getText();
    }

    public String getResolution() {
        return jResolution.getText();
    }

    public void setResolution(String resolution) {
        jResolution.setText(resolution);
    }

    public boolean getOutputMode() {
        return jOMNormal.isSelected();
    }

    public void setOutputMode(boolean outputMode) {
        jOMNormal.setSelected(outputMode);
        jOMWindowless.setSelected(!outputMode);
    }

    public boolean getSleepTime() {
        return jSleepTimes.isSelected();
    }

    public void setSleepTime(boolean sleepTime) {
        jSleepTimes.setSelected(sleepTime);
    }

    public String getSleepMMultiple() {
        return jSleepMultiple.getText();
    }

    public String getColor() {
        return (String) jColor.getSelectedItem();
    }

    public void setColor(String color) {
        jColor.setSelectedItem(color);
    }

    public void setScreenshotsPath(String screenshotsPath) {
        jScreenshotPath.setText(screenshotsPath);
    }

    public void setSleepMultiple(String sleepMultiple) {
        jSleepMultiple.setText(sleepMultiple);
    }

    public boolean getNewSession() {
        return jNewSession.isSelected();
    }

    public void setNewSession(boolean state){
        jNewSession.setSelected(state);
    }

    public boolean getReplayMode() {
        return jReplayMode.isSelected();
    }

    public void setReplayMode(boolean replayMode) {
        jReplayMode.setSelected(replayMode);
    }
}
