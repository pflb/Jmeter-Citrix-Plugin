package ru.pflb.jmeter.icaplugin.gui.utils;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.AbstractThreadGroup;
import ru.pflb.jmeter.icaplugin.gui.CitrixIcaRecorderGui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Описание
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class RecordingSettingsPanel extends JPanel {
    private final CitrixIcaRecorderGui mGui;
    private JCheckBox jSaveScreenshot;
    private JTextField jScreenshotPath;
    private BrowseButton jBrowse;
    private JComboBox jController;
    private JTextField jResolution;
    private JComboBox<String> jColor;
    private JCheckBox jRecordKeyboard;
    private JCheckBox jRecordMouseClicks;
    private JCheckBox jRecordMouseMove;
    private JTextField jICAConfig;
    private ArrayList<String> mControllersNames;
    private ArrayList<JMeterTreeNode> mControllersNodes;

    public RecordingSettingsPanel(CitrixIcaRecorderGui gui) {
        super();
        mGui = gui;

        setBorder(new TitledBorder(new EtchedBorder(), "Recording settings"));
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        g.anchor = GridBagConstraints.FIRST_LINE_START;
        g.weightx = 0;
        g.gridx = 0;
        g.gridy = 0;

        jSaveScreenshot = new JCheckBox("Save screenshots", true);
        jSaveScreenshot.setToolTipText("If checked, screenshots will be saved while recording");
        jScreenshotPath = new JTextField("");
        Common.setEditSize(jScreenshotPath);
        jScreenshotPath.setToolTipText("Folder, where screenshots will be saved");
        jBrowse = new BrowseButton("Browse", true);

        add(jSaveScreenshot, g);
        g.gridy++;
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
        add(new JLabel("Resolution (WxH)"), g);
        g.ipadx = 0;

        g.gridx++;
        jResolution = new JTextField("800x600");
        Common.setEditSize(jResolution);
        jResolution.setToolTipText("Specify window width and height");
        add(jResolution, g);
        g.weightx = 0;

        g.gridx++;
        jColor = new JComboBox<>(new String[]{"Color16", "Color256", "Color16Bit", "Color24Bit"});
        jColor.setSelectedIndex(3);
        jColor.setPreferredSize(new Dimension(jColor.getPreferredSize().width, 20));
        Common.setButtonSize(jColor);
        jColor.setToolTipText("Specify Citrix Color Depth");
        add(jColor, g);

        g.gridx = 0;
        g.gridy++;
        add(new JLabel("Record:"), g);
        g.gridx++;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridwidth = 2;
        add(makeRecordOptions(), g);
        g.fill = GridBagConstraints.NONE;

        g.gridy++;
        g.gridx = 0;
        add(new JLabel("Default ICA Config"), g);
        jICAConfig = new JTextField("");
        Common.setEditSize(jICAConfig);
        jICAConfig.setToolTipText("Specify default Citrix Config for recorded samplers");
        g.gridx++;
        add(jICAConfig, g);

        g.gridx = 0;
        g.gridy++;
        g.weightx = 0;
        add(new JLabel("Select controller"), g);


        g.gridwidth = 2;
        g.gridx++;
        g.ipadx = 300;
        g.fill = GridBagConstraints.HORIZONTAL;
        jController = new JComboBox();
        jController.setToolTipText("Specify controller, where recorded samplers will be put");
        add(jController, g);
    }

    private Component makeRecordOptions() {
        Box b = Box.createHorizontalBox();
        jRecordKeyboard = new JCheckBox("Keyboard", true);
        jRecordKeyboard.setToolTipText("Record keyboard events");
        jRecordMouseClicks = new JCheckBox("Mouse Clicks", true);
        jRecordMouseClicks.setToolTipText("Record mouse click events");
        jRecordMouseMove = new JCheckBox("Mouse Move", false);
        jRecordMouseMove.setToolTipText("Record mouse move events");
        b.add(jRecordKeyboard);
        b.add(Box.createHorizontalStrut(3));
        b.add(jRecordMouseClicks);
        b.add(Box.createHorizontalStrut(3));
        b.add(jRecordMouseMove);
        b.add(Box.createHorizontalStrut(3));
        return b;
    }

    private void getControllers(ArrayList<String> names, ArrayList<JMeterTreeNode> nodes) {
        JMeterTreeNode root = (JMeterTreeNode) GuiPackage.getInstance().getTreeModel().getTestPlan().getArray()[0];

        getChildren(names, nodes, root, null);
    }

    private void getChildren(ArrayList<String> names, ArrayList<JMeterTreeNode> nodes, JMeterTreeNode node, String curPath) {
        if (!node.getAllowsChildren())
            return;

        boolean check = false;
        Class o = node.getTestElement().getClass();
        while (!check) {
            if (o.equals(Object.class))
                break;
            if (o.equals(GenericController.class) || o.equals(AbstractThreadGroup.class))
                check = true;
            else
                o = o.getSuperclass();
        }
        String path = curPath == null ? node.getName() : curPath + ">" + node.getName();
        if (curPath != null && check) {
            names.add(path);
            nodes.add(node);
        }
        for (int i = 0; i < node.getChildCount(); ++i) {
            getChildren(names, nodes, (JMeterTreeNode) node.getChildAt(i), path);
        }
    }

    public boolean getSaveScreenshot() {
        return jSaveScreenshot.isSelected();
    }

    public void setSaveScreenshot(boolean save) {
        jSaveScreenshot.setSelected(save);
        jScreenshotPath.setEnabled(save);
        jBrowse.setEnabled(save);
    }

    public String getScreenshotPath() {
        return jScreenshotPath.getText();
    }

    public void setScreenshotPath(String screenshotPath) {
        jScreenshotPath.setText(screenshotPath);
    }

    public String getController() {
        return (String) jController.getSelectedItem();
    }

    public void setController(String controller) {
        jController.removeAllItems();
        jController.addItem("");
        mControllersNames = new ArrayList<>();
        mControllersNodes = new ArrayList<>();

        getControllers(mControllersNames, mControllersNodes);

        for (String mControllersName : mControllersNames) {
            jController.addItem(mControllersName);
        }
        if (mControllersNames.contains(controller))
            jController.setSelectedItem(controller);
        else
            jController.setSelectedIndex(0);
    }

    public void clearGui() {
        jSaveScreenshot.setSelected(true);
        jScreenshotPath.setText("");
        jController.removeAllItems();
        jResolution.setText("800x600");
        jColor.setSelectedItem(3);
        jRecordKeyboard.setSelected(true);
        jRecordMouseClicks.setSelected(true);
        jRecordMouseMove.setSelected(false);
    }

    public String getResolution() {
        return jResolution.getText();
    }

    public void setResolution(String resolution) {
        jResolution.setText(resolution);
    }

    public String getColor() {
        return (String) jColor.getSelectedItem();
    }

    public void setColor(String color) {
        jColor.setSelectedItem(color);
    }

    public int getRecord() {
        int record;
        record = (jRecordKeyboard.isSelected() ? 1 : 0) |
                (jRecordMouseClicks.isSelected() ? 1 : 0) << 1 |
                (jRecordMouseMove.isSelected() ? 1 : 0) << 2;
        return record;
    }

    public void setRecord(int record) {
        jRecordKeyboard.setSelected((record & 1) == 1);
        jRecordMouseClicks.setSelected((record & 2) >> 1 == 1);
        jRecordMouseMove.setSelected((record & 4) >> 2 == 1);
    }

    public String getICAConfig() {
        return jICAConfig.getText();
    }

    public void setICAConfig(String icaConfig) {
        jICAConfig.setText(icaConfig);
    }

    public boolean checkController(String controller) {
        ArrayList<String> Names = new ArrayList<>();
        ArrayList<JMeterTreeNode> Nodes = new ArrayList<>();
        getControllers(Names, Nodes);
        return Names.contains(controller);
    }

    public void setListeners() {
        jBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = jBrowse.browse();
                if (f != null) {
                    jScreenshotPath.setText(f.getAbsolutePath());
                }
            }
        });

        jController.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                String curVal = (String) jController.getSelectedItem();
                jController.removeAllItems();
                jController.addItem("");
                mControllersNames = new ArrayList<>();
                mControllersNodes = new ArrayList<>();

                getControllers(mControllersNames, mControllersNodes);

                for (String mControllersName : mControllersNames) {
                    jController.addItem(mControllersName);
                }
                if (mControllersNames.contains(curVal))
                    jController.setSelectedItem(curVal);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        jSaveScreenshot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jScreenshotPath.setEnabled(jSaveScreenshot.isSelected());
                jBrowse.setEnabled(jSaveScreenshot.isSelected());
            }
        });

        jRecordKeyboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.getRecorder().setRecord(getRecord());
            }
        });
        jRecordMouseClicks.addActionListener(jRecordKeyboard.getActionListeners()[0]);
        jRecordMouseMove.addActionListener(jRecordKeyboard.getActionListeners()[0]);
    }

    public void enableElements(boolean lock) {
        jSaveScreenshot.setEnabled(lock);
        jScreenshotPath.setEnabled(lock && jSaveScreenshot.isSelected());
        jBrowse.setEnabled(lock && jSaveScreenshot.isSelected());
        jController.setEnabled(lock);
        jResolution.setEnabled(lock);
        jColor.setEnabled(lock);
        jICAConfig.setEnabled(lock);
    }

    public TestElement getControllerElement() {
        if (checkController((String) jController.getSelectedItem())) {
            return mControllersNodes.get(jController.getSelectedIndex() - 1).getTestElement();
        } else {
            return null;
        }
    }
}
