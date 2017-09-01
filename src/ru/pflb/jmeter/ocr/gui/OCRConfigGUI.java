/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.pflb.jmeter.ocr.gui;

import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.testelement.TestElement;
import ru.pflb.jmeter.ocr.OCRConfig;
import ru.pflb.jmeter.ocr.gui.utils.TableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Иванов
 */
public class OCRConfigGUI extends AbstractConfigGui {

    private static final String DISPLAY_NAME = "OCR config";

    private JTextField mOCRHandle;
    private JTable mTable;
    private TableModel mTableModel;
    private JCheckBox mSaveScreenshot;

    public OCRConfigGUI() {
        super();
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        add(makeContent(), BorderLayout.CENTER);
    }

    private Component makeContent() {
        JPanel pan = new JPanel(new BorderLayout());

        pan.add(makeHandleAndTable(), BorderLayout.NORTH);
        pan.add(makeButtons(), BorderLayout.SOUTH);

        return pan;
    }

    private Component makeButtons() {
        JPanel pan = new JPanel();

        JButton add, del, clear;
        add = new JButton("Add row");
        del = new JButton("Delete row");
        clear = new JButton("Clear table");
        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mTableModel.addRow();
                mTable.tableChanged(null);
            }
        });
        del.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mTableModel.deleteRow(mTable.getSelectedRow());
                mTable.tableChanged(null);
            }
        });
        clear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mTableModel.clear();
                mTable.tableChanged(null);
            }
        });

        pan.add(add);
        pan.add(del);
        pan.add(clear);

        return pan;
    }

    private Component makeHandleAndTable() {
        Box box = Box.createVerticalBox();

        JPanel handle = new JPanel(new BorderLayout());
        handle.add(new JLabel("OCR handle: "), BorderLayout.WEST);
        mOCRHandle = new JTextField();
        handle.add(mOCRHandle, BorderLayout.CENTER);

        box.add(handle);
        box.add(Box.createVerticalStrut(10));

        mTableModel = new TableModel(2, 1);
        mTable = new JTable(mTableModel);
        mTableModel.setValueAt("Filename", 0, 0);
        mTableModel.setValueAt("Char range", 0, 1);

        box.add(mTable);

        return box;
    }

    @Override
    public String getStaticLabel() {
        return DISPLAY_NAME;
    }

    @Override
    public TestElement createTestElement() {
        OCRConfig ocr = new OCRConfig();
        return ocr;
    }

    //сохранение изменений тест-элемента
    @Override
    public void modifyTestElement(TestElement te) {
        super.configureTestElement(te);
        saveTE((OCRConfig) te);
    }

    //загрузка тест-элемента в гуи
    @Override
    public void configure(TestElement mc) {
        super.configure(mc);
        loadTE((OCRConfig) mc);
    }

    private void saveTE(OCRConfig t) {
        t.setHandle(mOCRHandle.getText());
        t.setTable(genMap());
    }

    private HashMap<String, String> genMap() {
        HashMap<String, String> map = new HashMap<>();

        int count = mTableModel.getRowCount();

        for (int i = 1; i < count; ++i) {
            map.put((String) mTableModel.getValueAt(i, 0), (String) mTableModel.getValueAt(i, 1));
        }

        return map;
    }

    private void loadTE(OCRConfig t) {
        mOCRHandle.setText(t.getHandle());
        loadTable(t.getTable());
    }

    private void loadTable(HashMap<String, String> map) {
        mTableModel.clear();

        if (map == null) {
            return;
        }

        for (Map.Entry<String, String> e : map.entrySet()) {
            mTableModel.addRow(new String[]{e.getKey(), e.getValue()});
        }
        mTable.tableChanged(null);
    }

    @Override
    public String getLabelResource() {
        return null;
    }

}
