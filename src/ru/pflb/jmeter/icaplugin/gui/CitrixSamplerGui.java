package ru.pflb.jmeter.icaplugin.gui;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import ru.pflb.jmeter.icaplugin.CitrixIcaSampler;
import ru.pflb.jmeter.icaplugin.gui.utils.Common;

import javax.swing.*;
import java.awt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class CitrixSamplerGui extends AbstractSamplerGui {
    private static final Logger L = LoggerFactory.getLogger(CitrixSamplerGui.class);
    private static final String STATIC_LABEL = "Citrix ICA Sampler";
    private JTextField jCitrixHandle;
    private JTextArea jSamplerRawEditor;

    public CitrixSamplerGui() {
        super();
        init();
    }

    private void init() {
        setName(STATIC_LABEL);
        setComment("");
        initGraph();
    }

    private void initGraph() {
        setBorder(makeBorder());
        setLayout(new BorderLayout());
        Box b = Box.createVerticalBox();
        b.add(makeTitlePanel());
        b.add(makeViewPanel());
        add(b, BorderLayout.NORTH);
        JPanel pan = makeEditSamplerPanel();
        add(pan, BorderLayout.CENTER);
        add(Box.createVerticalStrut(pan.getPreferredSize().height), BorderLayout.WEST);
    }

    private Component makeViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        Box b = Box.createVerticalBox();

        b.add(Box.createVerticalStrut(Common.vertSeparator));
        jCitrixHandle = CitrixIcaConfigGui.makeHandle(b);

        b.add(Box.createVerticalStrut(Common.vertSeparator));

        panel.add(b, BorderLayout.NORTH);
        return panel;
    }

    private JPanel makeEditSamplerPanel() {
        jSamplerRawEditor = new JTextArea(20, 20);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(jSamplerRawEditor), BorderLayout.CENTER);

        return panel;
    }

    @Override
    public String getLabelResource() {
        return null;
    }

    @Override
    public TestElement createTestElement() {
        CitrixIcaSampler sampler = new CitrixIcaSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        CitrixIcaSampler sampler = (CitrixIcaSampler) testElement;
        sampler.setHandle(jCitrixHandle.getText());
        sampler.setRawContent(jSamplerRawEditor.getText());
        super.configureTestElement(testElement);
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        CitrixIcaSampler sampler = (CitrixIcaSampler) element;
        jCitrixHandle.setText(sampler.getHandle());
        jSamplerRawEditor.setText(sampler.getRawContent());
        jSamplerRawEditor.setCaretPosition(0);
    }

    @Override
    public void clearGui() {
        super.clearGui();
        setName(STATIC_LABEL);
        setComment("");
        jCitrixHandle.setText("");
        jSamplerRawEditor.setText("");
    }

    @Override
    public String getStaticLabel() {
        return STATIC_LABEL;
    }
}
