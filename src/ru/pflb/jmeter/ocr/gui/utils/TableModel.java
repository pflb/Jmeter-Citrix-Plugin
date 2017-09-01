/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.pflb.jmeter.ocr.gui.utils;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Иванов
 */
public class TableModel extends AbstractTableModel {

    private final List<List<Object>> mCols = new ArrayList<>();

    private final int mColCount;
    private int mRowCount;

    public TableModel(int cols, int rows) {
        super();
        mColCount = cols;
        mRowCount = rows;

        List<Object> v;
        for (int i = 0; i < cols; ++i) {
            v = new ArrayList<>(rows);
            for (int j = 0; j < rows; ++j) {
                v.add(null);
            }
            mCols.add(v);
        }
    }

    @Override
    public int getRowCount() {
        return mRowCount;
    }

    @Override
    public int getColumnCount() {
        return mColCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return mCols.get(columnIndex).get(rowIndex);
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        mCols.get(columnIndex).set(rowIndex, value);
    }

    public void setHeader(Object[] value) {
        for (int i = 0; i < value.length; ++i) {
            mCols.get(i).set(0, value[i]);
        }
    }

    public void addRow() {
        for (int i = 0; i < mColCount; ++i) {
            mCols.get(i).add(null);
        }
        mRowCount++;
    }

    public void addRow(Object[] values) {
        for (int i = 0; i < mColCount; ++i) {
            mCols.get(i).add(values[i]);
        }
        mRowCount++;
    }

    public void deleteRow(int index) {
        if (index == -1) {
            return;
        }
        for (int i = 0; i < mColCount; ++i) {
            mCols.get(i).remove(index);
        }
        mRowCount--;
    }

    public void clear() {
        List<Object> v;
        for (int i = 0; i < mColCount; ++i) {
            v = mCols.get(i);
            for (int j = 1; j < v.size(); ++j) {
                v.remove(1);
            }
        }
        mRowCount = 1;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return rowIndex > 0 && rowIndex < mRowCount && columnIndex >= 0 && columnIndex < mColCount;
    }
}
