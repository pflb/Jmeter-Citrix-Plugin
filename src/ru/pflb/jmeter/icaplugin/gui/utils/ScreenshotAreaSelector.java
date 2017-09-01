package ru.pflb.jmeter.icaplugin.gui.utils;

import ru.pflb.jmeter.icaplugin.CitrixIcaRecorder;
import ru.pflb.jmeter.icaplugin.ica.utils.Interaction;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Описание
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class ScreenshotAreaSelector extends JDialog {
    private final String mFilePath;
    private final CitrixIcaRecorder mRecorder;
    private String mAction;
    private BufferedImage mImage;
    private ScreenshotPanel mSelectionPanel;
    private JScrollPane mScrollPane;
    private JRadioButton jTakeScreenshot;
    private JRadioButton jMakeOCR;
    private Box mSettingsPanel;
    private JCheckBox jWaitSync;
    private JTextField jWaitSeconds;
    private JTextField jTryEvery;
    private JButton jSaveButton;
    private JCheckBox jSamplerError;
    private JTextField jErrorVar;
    private JTextField jDeltaX;
    private JTextField jDeltaY;
    private JTextField jOCRHandle;
    private JTextField jOCRResultVar;

    public ScreenshotAreaSelector(String filepath, CitrixIcaRecorder recorder) {
        mFilePath = filepath;
        mRecorder = recorder;
        mAction = null;

        loadImage();
        init();

        setLocation(100, 100);
        pack();
        setModal(true);
        setVisible(true);
    }

    private void init() {
        setTitle("Select sync area");

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        mSelectionPanel = new ScreenshotPanel(this, mImage);
        mScrollPane = new JScrollPane(mSelectionPanel);
        panel.add(mScrollPane, BorderLayout.CENTER);

        panel.add(makeCommandPanel(), BorderLayout.SOUTH);
        remakeCommandPanel();
        getContentPane().add(panel);
    }

    private void resetRB(JRadioButton b1, JRadioButton b2) {
        if (b1.isSelected()) {
            b2.setSelected(false);
            remakeCommandPanel();
        } else {
            b1.setSelected(true);
            remakeCommandPanel();
        }
    }

    private Component makeCommandPanel() {
        JPanel mCommandPanel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.CENTER;
        g.gridx = g.gridy = 0;

        jTakeScreenshot = new JRadioButton("Screenshot sync", true);
        jMakeOCR = new JRadioButton("Make OCR", false);

        jTakeScreenshot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetRB(jTakeScreenshot, jMakeOCR);
            }
        });

        jMakeOCR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetRB(jMakeOCR, jTakeScreenshot);
            }
        });

        mCommandPanel.add(jTakeScreenshot, g);
        g.gridx++;
        mCommandPanel.add(jMakeOCR, g);

        mSettingsPanel = Box.createVerticalBox();
        g.gridy++;
        g.gridx = 0;
        g.gridwidth = 2;
        mCommandPanel.add(mSettingsPanel, g);

        jSaveButton = new JButton("Save action");
        jSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTakeScreenshot.isSelected())
                    makeScreenshotAction();
                else if (jMakeOCR.isSelected()) {
                    makeOCRAction();
                }
            }
        });

        g.gridx = 0;
        g.gridy++;
        g.gridwidth = 2;
        mCommandPanel.add(jSaveButton, g);

        return mCommandPanel;
    }

    private void makeOCRAction() {
        Rectangle area = mSelectionPanel.getRectangle();

        if (area.getWidth() <= 0 || area.getHeight() <= 0) {
            JOptionPane.showMessageDialog(null, "No valid area selected. Please select an area before saving", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (mRecorder.isConnected() == 0)
            return;

        mAction = new String(Interaction.Command.OCR.name() + ":");
        mAction += area.x + "," + area.y + "," + area.width + "," + area.height + ",";
        mAction += jOCRHandle.getText() + "," + jOCRResultVar.getText();
        setVisible(false);
    }

    private void makeScreenshotAction() {
        Rectangle area = mSelectionPanel.getRectangle();

        if (area.getWidth() <= 0 || area.getHeight() <= 0) {
            JOptionPane.showMessageDialog(null, "No valid area selected. Please select an area before saving", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (mRecorder.isConnected() == 0)
            return;

        mAction = new String(Interaction.Command.Screenshot.name() + ":");
        mAction += mRecorder.makeScreenshot(area.x, area.y, area.width, area.height) + ",";
        mAction += area.x + "," + area.y + "," + area.width + "," + area.height + ",";
        mAction += jDeltaX.getText() + "," + jDeltaY.getText() + ",";
        mAction += jWaitSync.isSelected() + ",";
        mAction += jWaitSeconds.getText() + ",";
        mAction += jTryEvery.getText() + ",";
        mAction += jSamplerError.isSelected() + ",";
        mAction += jErrorVar.getText();
        setVisible(false);
    }

    private void remakeCommandPanel() {
        mSettingsPanel.removeAll();

        if (jTakeScreenshot.isSelected()) {
            makeTakeScreenshot();
        } else if (jMakeOCR.isSelected()) {
            makeOCR();
        }
        mSettingsPanel.revalidate();
    }

    private void makeOCR() {
        Box box = Box.createHorizontalBox();
        jOCRHandle = new JTextField("", 10);
        jOCRResultVar = new JTextField("", 10);

        box.add(new JLabel("OCR config handle: "));
        box.add(Box.createHorizontalStrut(2));
        box.add(jOCRHandle);
        box.add(Box.createHorizontalStrut(5));
        box.add(new JLabel("OCR result variable: "));
        box.add(Box.createHorizontalStrut(2));
        box.add(jOCRResultVar);
        mSettingsPanel.add(box);
    }

    private void makeTakeScreenshot() {
        Box box = Box.createHorizontalBox();
        jWaitSync = new JCheckBox("Wait sync", true);
        jWaitSync.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jWaitSeconds.setEnabled(jWaitSync.isSelected());
                jTryEvery.setEnabled(jWaitSync.isSelected());
            }
        });
        box.add(jWaitSync);
        box.add(Box.createHorizontalStrut(2));

        jWaitSeconds = new JTextField("30000", 5);
        box.add(jWaitSeconds);
        box.add(Box.createHorizontalStrut(2));

        box.add(new JLabel("ms, try every "));
        add(Box.createHorizontalStrut(2));

        jTryEvery = new JTextField("100", 5);
        box.add(jTryEvery);

        mSettingsPanel.add(box);

        box = Box.createHorizontalBox();
        box.add(new JLabel("Delta x, y:"));

        jDeltaX = new JTextField("0", 2);
        jDeltaY = new JTextField("0", 2);

        box.add(jDeltaX);
        box.add(jDeltaY);
        mSettingsPanel.add(box);

        box = Box.createHorizontalBox();

        jSamplerError = new JCheckBox("Set sampler error if failed", true);
        box.add(jSamplerError);
        box.add(Box.createHorizontalStrut(5));

        box.add(new JLabel("Write result in variable named: "));
        box.add(Box.createHorizontalStrut(2));

        jErrorVar = new JTextField("CitrixResult", 10);
        box.add(jErrorVar);
        mSettingsPanel.add(box);
    }

    private void loadImage() {
        try {
            mImage = ImageIO.read(new File(mFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scroll(Rectangle rectangle) {
        mScrollPane.getViewport().scrollRectToVisible(rectangle);
    }

    public String getAction() {
        return mAction;
    }
}
