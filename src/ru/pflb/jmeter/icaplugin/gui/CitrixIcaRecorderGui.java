package ru.pflb.jmeter.icaplugin.gui;

import org.apache.jmeter.control.gui.AbstractControllerGui;
import org.apache.jmeter.gui.util.MenuFactory;
import org.apache.jmeter.testelement.TestElement;
import ru.pflb.jmeter.icaplugin.CitrixIcaRecorder;
import ru.pflb.jmeter.icaplugin.gui.utils.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CitrixIcaRecorderGui extends AbstractControllerGui {
    private static final Logger L = LoggerFactory.getLogger(CitrixIcaRecorderGui.class);
    private static final String STATIC_LABEL = "Citrix ICA Recorder";

    private ConnectionPanel mConnectionPanel;
    private RecordingSettingsPanel mRecordingSettingsPanel;
    private RecordingPanel mRecordingPanel;

    private CitrixIcaRecorder mRecorder;

    public CitrixIcaRecorderGui() {
        super();
        init();
    }

    private void init() {
        setName(STATIC_LABEL);
        setComment("");
        initGraph();
        setListeners();
    }

    private void setListeners() {
        mConnectionPanel.setListeners();
        mRecordingSettingsPanel.setListeners();
        mRecordingPanel.setListeners();
    }

    private void initGraph() {
        setLayout(new BorderLayout());
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        add(makeViewPanel(), BorderLayout.CENTER);
    }

    private Component makeViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        Box box = Box.createVerticalBox();
        mConnectionPanel = new ConnectionPanel();
        mRecordingSettingsPanel = new RecordingSettingsPanel(this);
        mRecordingPanel = new RecordingPanel(this);

        box.add(Box.createVerticalStrut(Common.vertSeparator));
        box.add(mConnectionPanel);
        box.add(Box.createVerticalStrut(Common.vertSeparator));
        box.add(mRecordingSettingsPanel);
        box.add(Box.createVerticalStrut(Common.vertSeparator));
        box.add(mRecordingPanel);

        panel.add(box, BorderLayout.NORTH);
        return panel;
    }

    private Component makeLabel(String s) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(s), BorderLayout.WEST);
        return p;
    }

    @Override
    public Collection<String> getMenuCategories() {
        return Collections.singletonList(MenuFactory.NON_TEST_ELEMENTS);
    }


    @Override
    public String getStaticLabel() {
        return STATIC_LABEL;
    }


    @Override
    public String getLabelResource() {
        return null;
    }


    @Override
    public TestElement createTestElement() {
        mRecorder = new CitrixIcaRecorder();
        modifyTestElement(mRecorder);
        return mRecorder;
    }


    @Override
    public void clearGui() {
        super.clearGui();
        mConnectionPanel.clearGui();
        mRecordingSettingsPanel.clearGui();
        mRecordingPanel.clearGui();
    }

    @Override
    public void configure(TestElement element) {
        if (element instanceof CitrixIcaRecorder) {
            super.configure(element);
            mRecorder = (CitrixIcaRecorder) element;
            mConnectionPanel.setICAFile(mRecorder.getICAFile());
            mConnectionPanel.setHost(mRecorder.getHost());
            mConnectionPanel.setPort(mRecorder.getPort());
            mConnectionPanel.setUser(mRecorder.getUser());
            mConnectionPanel.setPassword(mRecorder.getPassword());
            mConnectionPanel.setDomain(mRecorder.getDomain());
            mConnectionPanel.setApplication(mRecorder.getApplication());
            mRecordingSettingsPanel.setSaveScreenshot(mRecorder.getSaveScreenshot());
            mRecordingSettingsPanel.setScreenshotPath(mRecorder.getScreenshotPath());
            mRecordingSettingsPanel.setResolution(mRecorder.getResolution());
            mRecordingSettingsPanel.setColor(mRecorder.getColor());
            mRecordingSettingsPanel.setRecord(mRecorder.getRecord());
            mRecordingSettingsPanel.setICAConfig(mRecorder.getICAConfig());
            mRecordingSettingsPanel.setController(mRecorder.getController());

            switch (mRecorder.isConnected()) {
                case 0:
                    onStop();
                    break;
                case 1:
                    mConnectionPanel.enableElements(false);
                    mRecordingSettingsPanel.enableElements(false);
                    mRecordingPanel.onInitializeConnection();
                    break;
                case 2:
                    onConnecting();
                    break;
                case 3:
                    onLogon();
                    break;
            }

            mRecordingPanel.onRecord(mRecorder.isRecording() && mRecorder.isConnected() == 3);
        }
    }


    @Override
    public void modifyTestElement(TestElement testElement) {
        if (testElement instanceof CitrixIcaRecorder) {
            super.configureTestElement(testElement);
            mRecorder = (CitrixIcaRecorder) testElement;
            mRecorder.setICAFile(mConnectionPanel.getICAFile());
            mRecorder.setHost(mConnectionPanel.getHost());
            mRecorder.setPort(mConnectionPanel.getPort());
            mRecorder.setUser(mConnectionPanel.getUser());
            mRecorder.setPassword(mConnectionPanel.getPassword());
            mRecorder.setDomain(mConnectionPanel.getDomain());
            mRecorder.setApplication(mConnectionPanel.getApplication());
            mRecorder.setSaveScreenshot(mRecordingSettingsPanel.getSaveScreenshot());
            mRecorder.setScreenshotPath(mRecordingSettingsPanel.getScreenshotPath());
            mRecorder.setResolution(mRecordingSettingsPanel.getResolution());
            mRecorder.setColor(mRecordingSettingsPanel.getColor());
            mRecorder.setRecord(mRecordingSettingsPanel.getRecord());
            mRecorder.setICAConfig(mRecordingSettingsPanel.getICAConfig());
            mRecorder.setController(mRecordingSettingsPanel.getController());
            mRecorder.setControllerElement(mRecordingSettingsPanel.getControllerElement());
            mRecorder.setGui(this);
        }
    }

    public void invokeError(String s) {
        JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean checkControllerPath(String controller) {
        return mRecordingSettingsPanel.checkController(controller);
    }

    public void onConnectButtonPressed() {
        modifyTestElement(mRecorder);
        if (!mRecorder.checkConfig())
            return;
        mConnectionPanel.enableElements(false);
        mRecordingSettingsPanel.enableElements(false);
        mRecorder.connect();
    }

    public void onStopButtonPressed() {
        mRecorder.stop();
        mConnectionPanel.enableElements(true);
        mRecordingSettingsPanel.enableElements(true);
        mRecordingPanel.onStop();
    }

    public CitrixIcaRecorder getRecorder() {
        return mRecorder;
    }

    public void onAddStepButtonPressed() {
        mRecorder.newStep();
    }

    public void onRecordButtonPressed() {
        if (mRecorder.isConnected() == 0) {
            modifyTestElement(mRecorder);
            mRecordingPanel.onInitializeConnection();
        }
        mRecordingPanel.onRecord(mRecorder.isRecording());
        mRecorder.record();
    }

    public void onRestartStepButtonPressed() {
        mRecorder.restartStep();
    }

    public void onStartTagButtonPressed() {
        String tagName = JOptionPane.showInputDialog(CitrixIcaRecorderGui.this, "Tag Name:");
        if (tagName == null || tagName.trim().isEmpty())
            return;
        String sleepFactor = JOptionPane.showInputDialog(CitrixIcaRecorderGui.this, "Time between keys:");
        if (sleepFactor == null || sleepFactor.trim().isEmpty()) {
            sleepFactor = "500";
        }
        mRecorder.startTag(sleepFactor.trim(), tagName.trim());
        mRecordingPanel.setTag(true);
    }

    public void onEndTagButtonPressed() {
        mRecorder.stopTag();
        mRecordingPanel.setTag(false);
    }

    public void onSyncButtonPressed() {
        String filepath = mRecorder.getSaveScreenshot() ? mRecorder.getScreenshotPath() : System.getProperty("user.dir");
        filepath += "\\" + mRecorder.getStepName() + "_" + System.currentTimeMillis() + ".bmp";
        mRecorder.makeFullScreenshot(filepath);

        ScreenshotAreaSelector screenshotAreaSelector = new ScreenshotAreaSelector(filepath, mRecorder);
        String action = screenshotAreaSelector.getAction();

        if (!mRecorder.getSaveScreenshot()) {
            File f = new File(filepath);
            f.delete();
        }

        if (action == null) {
            return;
        }

        mRecorder.addAction(action);
    }

    public void onStop() {
        mConnectionPanel.enableElements(true);
        mRecordingSettingsPanel.enableElements(true);
        mRecordingPanel.onStop();
    }

    public void onConnecting() {
        mRecordingPanel.onConnecting();
    }

    public void onLogon() {
        mRecordingPanel.onLogon();
    }
}
