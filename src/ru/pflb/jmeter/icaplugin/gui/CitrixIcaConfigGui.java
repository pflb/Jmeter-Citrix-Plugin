package ru.pflb.jmeter.icaplugin.gui;

import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.testelement.TestElement;
import ru.pflb.jmeter.icaplugin.CitrixIcaConfig;
import ru.pflb.jmeter.icaplugin.gui.utils.CitrixPanel;
import ru.pflb.jmeter.icaplugin.gui.utils.Common;
import ru.pflb.jmeter.icaplugin.gui.utils.ConnectionPanel;
import ru.pflb.jmeter.icaplugin.ica.IcaConnector;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class CitrixIcaConfigGui extends AbstractConfigGui {
    private static final Logger L = LoggerFactory.getLogger(CitrixIcaConfigGui.class);

    private static final String STATIC_LABEL = "Citrix Config";

    private JTextField mHandle;
    private ConnectionPanel mConnectionPanel;
    private CitrixPanel mCitrixPanel;

    public CitrixIcaConfigGui() {
        super();
        init();
    }

    public static JTextField makeHandle(Box b) {
        JTextField handle;
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EtchedBorder());
        GridBagConstraints g = new GridBagConstraints();

        g.gridx = g.gridy = 0;
        g.ipadx = 37;
        g.anchor = GridBagConstraints.FIRST_LINE_START;

        p.add(new JLabel("Citrix Config Handle"), g);

        g.weightx = 1;
        g.gridx++;
        g.ipadx = 0;
        handle = new JTextField("");
        p.add(handle, g);
        Common.setEditSize(handle);

        b.add(p);

        return handle;
    }

    private void init() {
        setName(STATIC_LABEL);
        setComment("");
        initGraph();
    }

    private void initGraph() {
        setLayout(new BorderLayout());
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        add(makeViewPanel(), BorderLayout.CENTER);
        mConnectionPanel.setListeners();
    }

    private Component makeViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        Box box = Box.createVerticalBox();
        mConnectionPanel = new ConnectionPanel();
        mCitrixPanel = new CitrixPanel();

        box.add(Box.createVerticalStrut(Common.vertSeparator));
        mHandle = makeHandle(box);

        box.add(Box.createVerticalStrut(Common.vertSeparator));
        box.add(mConnectionPanel);

        box.add(Box.createVerticalStrut(Common.vertSeparator));
        box.add(mCitrixPanel);

        box.add(makeLockButtons());

        panel.add(box, BorderLayout.NORTH);
        return panel;
    }

    private Component makeLockButtons() {
        Box box = Box.createHorizontalBox();
        JButton b = new JButton("Unlock all sessions");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IcaConnector.unlock();
            }
        });
        box.add(b);
        box.add(Box.createHorizontalStrut(5));

        b = new JButton("Lock all sessions");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IcaConnector.lock();
            }
        });
        box.add(b);
        return box;
    }

    @Override
    public void configure(TestElement element) {
        if (element instanceof CitrixIcaConfig) {
            super.configure(element);
            CitrixIcaConfig config = (CitrixIcaConfig) element;
            mHandle.setText(config.getHandle());
            mConnectionPanel.setICAFile(config.getICAFile());
            mConnectionPanel.setHost(config.getHost());
            mConnectionPanel.setPort(config.getPort());
            mConnectionPanel.setUser(config.getUser());
            mConnectionPanel.setPassword(config.getPassword());
            mConnectionPanel.setDomain(config.getDomain());
            mConnectionPanel.setApplication(config.getApplication());

            mCitrixPanel.setSaveScreenshots(config.getSaveScreenshots());
            mCitrixPanel.setScreenshotsPath(config.getScreenshotsPath());
            mCitrixPanel.setResolution(config.getResolution());
            mCitrixPanel.setColor(config.getColor());
            mCitrixPanel.setOutputMode(config.getOutputMode());
            mCitrixPanel.setSleepTime(config.getSleepTime());
            mCitrixPanel.setSleepMultiple(config.getSleepMultiple());
            mCitrixPanel.setNewSession(config.getNewSession());
            mCitrixPanel.setReplayMode(config.getReplayMode());
        }
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
        CitrixIcaConfig te = new CitrixIcaConfig();
        modifyTestElement(te);
        return te;
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        if (testElement instanceof CitrixIcaConfig) {
            super.configureTestElement(testElement);
            CitrixIcaConfig config = (CitrixIcaConfig) testElement;
            config.setHandle(mHandle.getText());
            config.setICAFile(mConnectionPanel.getICAFile());
            config.setHost(mConnectionPanel.getHost());
            config.setPort(mConnectionPanel.getPort());
            config.setUser(mConnectionPanel.getUser());
            config.setPassword(mConnectionPanel.getPassword());
            config.setDomain(mConnectionPanel.getDomain());
            config.setApplication(mConnectionPanel.getApplication());

            config.setSaveScreenshot(mCitrixPanel.getSaveScreenshots());
            config.setScreenshotPath(mCitrixPanel.getScreenshotPath());
            config.setResolution(mCitrixPanel.getResolution());
            config.setColor(mCitrixPanel.getColor());
            config.setOutputMode(mCitrixPanel.getOutputMode());
            config.setSleepTime(mCitrixPanel.getSleepTime());
            config.setSleepMultiple(mCitrixPanel.getSleepMMultiple());
            config.setNewSession(mCitrixPanel.getNewSession());
            config.setReplayMode(mCitrixPanel.getReplayMode());
        }
    }

    @Override
    public void clearGui() {
        super.clearGui();
        mHandle.setText("");
        mConnectionPanel.clearGui();
        mCitrixPanel.clearGui();
    }
}
