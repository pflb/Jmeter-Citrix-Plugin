package ru.pflb.jmeter.icaplugin.gui.utils;

import ru.pflb.jmeter.icaplugin.gui.CitrixIcaRecorderGui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Описание
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class RecordingPanel extends JPanel {
    private final CitrixIcaRecorderGui mGui;
    private JButton jAddStep;
    private JButton jRestartStep;
    private JButton jStartTag;
    private JButton jEndTag;
    private JButton jSync;
    private JButton jRecord;
    private JButton jStop;
    private JButton jConnect;
    private Box mButtons;

    public RecordingPanel(CitrixIcaRecorderGui gui) {
        super();

        mGui = gui;
        Box box = Box.createVerticalBox();

        JPanel recordSteps = new JPanel(new GridBagLayout());
        recordSteps.setBorder(new TitledBorder(new EtchedBorder(), "Step controls"));
        GridBagConstraints g = new GridBagConstraints();

        g.anchor = GridBagConstraints.FIRST_LINE_START;

        g.gridy = g.gridx = 0;
        g.weightx = 0;
        recordSteps.add(makeButtons(), g);

        box.add(recordSteps);
        box.add(Box.createVerticalStrut(Common.vertSeparator));

        box.add(makeControlButtons());

        setLayout(new BorderLayout());
        add(box, BorderLayout.NORTH);

        onStop();
    }

    public void setListeners() {
        jConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onConnectButtonPressed();
            }
        });

        jRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onRecordButtonPressed();
            }
        });

        jStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onStopButtonPressed();
            }
        });

        jAddStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onAddStepButtonPressed();
            }
        });

        jRestartStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onRestartStepButtonPressed();
            }
        });

        jStartTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onStartTagButtonPressed();
            }
        });

        jEndTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onEndTagButtonPressed();
            }
        });

        jSync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGui.onSyncButtonPressed();
            }
        });
    }

    private Component makeControlButtons() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Recording controls"));
        GridBagConstraints g = new GridBagConstraints();

        g.insets = new Insets(3, 3, 3, 3);

        g.gridx = g.gridy = 0;
        jConnect = new JButton("Connect");
        Common.setButtonSize(jConnect);
        jConnect.setToolTipText("Connect and start Citrix session");
        panel.add(jConnect, g);

        g.gridx++;
        jRecord = new JButton("Record");
        Common.setButtonSize(jRecord);
        jRecord.setToolTipText("Start/pause record");
        panel.add(jRecord, g);

        g.gridx++;
        jStop = new JButton("Stop");
        Common.setButtonSize(jStop);
        jStop.setToolTipText("Stop record and detach session from recorder");
        panel.add(jStop, g);

        return panel;
    }

    private Component makeButtons() {
        mButtons = Box.createHorizontalBox();

        jAddStep = new JButton("Add Step");
        jAddStep.setToolTipText("Add new Citrix sampler");
        mButtons.add(jAddStep);

        mButtons.add(Box.createHorizontalStrut(Common.buttonsVertSeparator));
        jRestartStep = new JButton("Restart Step");
        jRestartStep.setToolTipText("Delete current record and create new with the same name");
        mButtons.add(jRestartStep);

        mButtons.add(Box.createHorizontalStrut(Common.buttonsVertSeparator));
        jStartTag = new JButton("Start Tag");
        jStartTag.setToolTipText("Start entering text with parametrization support");
        mButtons.add(jStartTag);

        mButtons.add(Box.createHorizontalStrut(Common.buttonsVertSeparator));
        jEndTag = new JButton("End Tag");
        jEndTag.setEnabled(false);
        jEndTag.setToolTipText("End entering text");
        mButtons.add(jEndTag);

        mButtons.add(Box.createHorizontalStrut(Common.buttonsVertSeparator));
        jSync = new JButton("Synchronize");
        jSync.setToolTipText("Make new synchronize command (screenshot or OCR");
        mButtons.add(jSync);

        Common.setButtonSize(jAddStep);
        Common.setButtonSize(jRestartStep);
        Common.setButtonSize(jStartTag);
        Common.setButtonSize(jEndTag);
        Common.setButtonSize(jSync);

        return mButtons;
    }

    public void clearGui() {
        onStop();
    }

    public void onStop() {
        jConnect.setText("Connect");
        jConnect.setEnabled(true);
        jRecord.setText("Start Record");
        jRecord.setEnabled(true);
        jStop.setEnabled(false);
        jAddStep.setEnabled(false);
        jRestartStep.setEnabled(false);
        jStartTag.setEnabled(false);
        jEndTag.setEnabled(false);
        jSync.setEnabled(false);
    }

    public void onInitializeConnection() {
        jConnect.setEnabled(false);
        jConnect.setText("Initializing...");
        jRecord.setEnabled(false);
        jStop.setEnabled(true);
    }

    public void onConnecting() {
        jConnect.setText("Connecting...");
        jConnect.setEnabled(false);
        jRecord.setEnabled(false);
        jStop.setEnabled(true);
    }

    public void onLogon() {
        jConnect.setText("Connected");
        jConnect.setEnabled(false);
        jRecord.setEnabled(true);
    }

    public void onRecord(boolean recording) {
        if (recording) {
            jRecord.setText("Pause Record");
            jAddStep.setEnabled(true);
            jRestartStep.setEnabled(true);
            jStartTag.setEnabled(true);
            jEndTag.setEnabled(false);
            jStop.setEnabled(true);
            jSync.setEnabled(true);
        } else {
            jRecord.setText("Start Record");
            jAddStep.setEnabled(false);
            jRestartStep.setEnabled(false);
            jStartTag.setEnabled(false);
            jEndTag.setEnabled(false);
            jStop.setEnabled(mGui.getRecorder().isConnected() > 0);
            jSync.setEnabled(false);
        }
    }

    public void setTag(boolean b) {
        jAddStep.setEnabled(!b);
        jRestartStep.setEnabled(!b);
        jStartTag.setEnabled(!b);
        jEndTag.setEnabled(b);
        jSync.setEnabled(!b);
    }
}

