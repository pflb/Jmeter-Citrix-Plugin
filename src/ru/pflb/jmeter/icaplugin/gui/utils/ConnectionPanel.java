package ru.pflb.jmeter.icaplugin.gui.utils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ConnectionPanel extends JPanel {
    private JTextField jICAFile;
    private JTextField jHost;
    private JTextField jPort;
    private JTextField jUser;
    private JTextField jPassword;
    private JTextField jDomain;
    private JTextField jApplication;
    private BrowseButton jBrowse;

    public ConnectionPanel() {
        super();
        setBorder(new TitledBorder(new EtchedBorder(), "Connection settings"));
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.FIRST_LINE_START;
        g.weightx = 0;
        g.gridx = 0;

        g.ipadx = 50;

        g.gridy = 0;
        add(new JLabel("ICA file location *"), g);
        g.gridy++;
        add(new JLabel("Host"), g);
        g.gridy++;
        add(new JLabel("Port **"), g);
        g.gridy++;
        add(new JLabel("User Name"), g);
        g.gridy++;
        add(new JLabel("User Password"), g);
        g.gridy++;
        add(new JLabel("Domain **"), g);
        g.gridy++;
        add(new JLabel("Application"), g);

        g.gridwidth = 2;
        g.ipadx = 0;

        g.gridy++;
        add(new JLabel("* - if specified, options below will be ignores"), g);

        g.gridy++;
        add(new JLabel("** - can be empty"), g);

        g.gridwidth = 1;
        g.gridx++;

        g.gridy = 0;
        jICAFile = new JTextField("");
        Common.setEditSize(jICAFile);
        jICAFile.setToolTipText("Enter path and name for .ica file");
        add(jICAFile, g);

        g.gridy++;
        jHost = new JTextField("");
        Common.setEditSize(jHost);
        jHost.setToolTipText("Enter hostname or IP for Citrix Server");
        add(jHost, g);

        g.gridy++;
        jPort = new JTextField("");
        Common.setEditSize(jPort);
        jPort.setToolTipText("Enter port number or leave it empty for default port");
        add(jPort, g);

        g.gridy++;
        jUser = new JTextField("");
        Common.setEditSize(jUser);
        jUser.setToolTipText("Enter username for Citrix session");
        add(jUser, g);

        g.gridy++;
        jPassword = new JTextField("");
        Common.setEditSize(jPassword);
        jPassword.setToolTipText("Enter password for username");
        add(jPassword, g);

        g.gridy++;
        jDomain = new JTextField("");
        Common.setEditSize(jDomain);
        jDomain.setToolTipText("Enter domain for username or leave it empty for none");
        add(jDomain, g);

        g.gridy++;
        jApplication = new JTextField("");
        Common.setEditSize(jApplication);
        jApplication.setToolTipText("Enter application name (not \"Friendly\" name)");
        add(jApplication, g);

        g.weightx = 1;

        g.gridx++;
        g.gridy = 0;
        g.ipadx = 0;
        jBrowse = new BrowseButton("Browse", false);
        jBrowse.setPreferredSize(new Dimension(jBrowse.getPreferredSize().width, 20));
        add(jBrowse, g);
    }

    public String getICAFile() {
        return jICAFile.getText();
    }

    public void setICAFile(String ICAFile) {
        jICAFile.setText(ICAFile);
    }

    public String getHost() {
        return jHost.getText();
    }

    public void setHost(String host) {
        jHost.setText(host);
    }

    public String getPort() {
        return jPort.getText();
    }

    public void setPort(String port) {
        jPort.setText(port);
    }

    public String getUser() {
        return jUser.getText();
    }

    public void setUser(String user) {
        jUser.setText(user);
    }

    public String getPassword() {
        return jPassword.getText();
    }

    public void setPassword(String password) {
        jPassword.setText(password);
    }

    public String getDomain() {
        return jDomain.getText();
    }

    public void setDomain(String domain) {
        jDomain.setText(domain);
    }

    public String getApplication() {
        return jApplication.getText();
    }

    public void setApplication(String application) {
        jApplication.setText(application);
    }

    public void clearGui() {
        jICAFile.setText("");
        jHost.setText("");
        jPort.setText("");
        jUser.setText("");
        jPassword.setText("");
        jDomain.setText("");
        jApplication.setText("");
    }

    public void setListeners() {
        jBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = jBrowse.browse();
                if (f != null) {
                    jICAFile.setText(f.getAbsolutePath());
                }
            }
        });
    }

    public void enableElements(boolean lock) {
        jICAFile.setEnabled(lock);
        jHost.setEnabled(lock);
        jPort.setEnabled(lock);
        jUser.setEnabled(lock);
        jPassword.setEnabled(lock);
        jDomain.setEnabled(lock);
        jApplication.setEnabled(lock);
        jBrowse.setEnabled(lock);
    }
}
