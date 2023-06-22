/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.pflb.jmeter.ocr;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.testelement.TestIterationListener;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.ObjectProperty;
import ru.pflb.ocr.OCRRecognizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Иванов
 */
public class OCRConfig extends ConfigTestElement
        implements NoThreadClone, TestStateListener, TestIterationListener {

    public static final String PROPERTY_OCR_HANDLE = "OCRConfig.handle";
    public static final String PROPERTY_OCR_TABLE = "OCRConfig.table";

    private static final String DEFAULT_NAME = "OCR config";
    private static final String DEFAULT_OCR_HANDLE = "OCR_HANDLE";

    private static final HashMap<String, OCRRecognizer> OCR_ENGINES = new HashMap<>();

    public OCRConfig() {
        super();
        setName(DEFAULT_NAME);
        setComment("");
        setProperty(PROPERTY_OCR_HANDLE, DEFAULT_OCR_HANDLE);
        setProperty(new ObjectProperty(PROPERTY_OCR_TABLE, new HashMap<String, String>()));
    }

    public static String makeOCR(String handle, String ocrimage) throws IOException {
        if (!OCR_ENGINES.containsKey(handle)) {
            throw new Error("OCR handle not found!");
        }

        return OCR_ENGINES.get(handle).recognize(ocrimage);
    }

    @Override
    public void testStarted() {
        try {
            configureEngine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testEnded() {
        deleteEngine();
    }

    @Override
    public void testStarted(String string) {
        testStarted();
    }

    @Override
    public void testEnded(String string) {
        testEnded();
    }

    @Override
    public void testIterationStart(LoopIterationEvent loopIterationEvent) {
    }

    private void configureEngine() throws Exception {
        HashMap<String, String> map = (HashMap<String, String>) getProperty(PROPERTY_OCR_TABLE).getObjectValue();
        String handle = getProperty(PROPERTY_OCR_HANDLE).getStringValue();
        if (map.isEmpty() || OCR_ENGINES.containsKey(handle)) {
            throw new IllegalArgumentException("OCR handle is already registered or table of training image is empty");
        }
        OCRRecognizer ocr = new OCRRecognizer();
        for (Map.Entry<String, String> e : map.entrySet()) {
            String range = e.getValue();
            ocr.addTrainingImage(e.getKey(), range.charAt(0), range.charAt(2));
        }

        ocr.train();

        OCR_ENGINES.put(handle, ocr);
    }

    private void deleteEngine() {
        String handle = getProperty(PROPERTY_OCR_HANDLE).getStringValue();
        if (OCR_ENGINES.containsKey(handle)) {
            OCR_ENGINES.remove(handle);
        }
    }

    public String getHandle() {
        return getPropertyAsString(PROPERTY_OCR_HANDLE);
    }

    public void setHandle(String handle) {
        setProperty(PROPERTY_OCR_HANDLE, handle);
    }

    public HashMap<String, String> getTable() {
        return (HashMap<String, String>) getProperty(PROPERTY_OCR_TABLE).getObjectValue();
    }

    public void setTable(HashMap<String, String> table) {
        setProperty(new ObjectProperty(PROPERTY_OCR_TABLE, table));
    }
}
