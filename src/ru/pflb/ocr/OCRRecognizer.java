/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.pflb.ocr;

import net.sourceforge.javaocr.ocrPlugins.mseOCR.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Иванов
 */
public class OCRRecognizer {

    private final ArrayList<TrainingImageSpec> trainingSpecs;
    OCRScanner ocr;
    HashMap<Character, ArrayList<TrainingImage>> trainingImages;

    public OCRRecognizer() {
        trainingSpecs = new ArrayList<>();
        ocr = new OCRScanner();
    }

    private static HashMap<Character, ArrayList<TrainingImage>> getTrainingImageHashMap(ArrayList<TrainingImageSpec> imgs) throws Exception {
        TrainingImageLoader loader = new TrainingImageLoader();
        HashMap<Character, ArrayList<TrainingImage>> trainingImages = new HashMap<>();
        Frame frame = new Frame();

        for (int i = 0; i < imgs.size(); i++) {
            loader.load(
                    frame,
                    imgs.get(i).getFileLocation(),
                    imgs.get(i).getCharRange(),
                    trainingImages);
        }

        return trainingImages;
    }

    public void addTrainingImage(String fileName, char startChar, char endChar) {
        TrainingImageSpec tis = new TrainingImageSpec();
        tis.setFileLocation(fileName);
        tis.setCharRange(new CharacterRange(startChar, endChar));

        trainingSpecs.add(tis);
    }

    public void train() throws Exception {
        trainingImages = getTrainingImageHashMap(trainingSpecs);
        ocr.addTrainingImages(trainingImages);
    }

    public String recognize(String targetFileName) throws IOException {
        BufferedImage targetImage = ImageIO.read(new File(targetFileName));
        return ocr.scan(targetImage, 0, 0, 0, 0, null);
    }
}
