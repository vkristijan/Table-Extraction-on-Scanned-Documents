package hr.fer.zemris.zavrad.detection.training;

import hr.fer.zemris.zavrad.detection.CornerDetection;
import hr.fer.zemris.zavrad.detection.features.DistanceFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.detection.training.backprop.BackPropagation;
import hr.fer.zemris.zavrad.table.CornerValue;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerDetectionBackprop {
    private static final double LEARNING_RATE = 0.10;
    private static final int MAX_ITERATION = 100;

    public static void main(String[] args) {
        Path dataPath = Paths.get(args[0]);

        CornerDetection detection = new CornerDetection();
        detection.setRandomWeights();
        IFeatureExtractor extractor = new DistanceFeatureExtractor();
        BackPropagation backPropagation = new BackPropagation(detection.getFfann(), LEARNING_RATE);

        List<Sample> samples = loadSamples(dataPath, extractor);

        for (int i = 0; i < MAX_ITERATION; ++i){

            for (Sample sample : samples){
                RealVector input = new ArrayRealVector(sample.input);
                int expectedOutput = sample.value.getValue();
                backPropagation.propagate(input, expectedOutput);
            }

            double correct = 0;
            for (Sample sample : samples){
                CornerValue expectedOutput = sample.value;
                if (expectedOutput.equals(detection.detect(sample.input))){
                    correct++;
                }
            }
            System.out.println("Epoch #" + i + ":  " + correct / samples.size());
        }
    }



    private static List<Sample> loadSamples(Path dataPath, IFeatureExtractor extractor) {
        List<Sample> samples = new ArrayList<>();

        for (CornerValue value : CornerValue.values()){
            Path path = dataPath.resolve(String.valueOf(value.getValue()));

            if (!Files.exists(path)) return samples;

            File[] files = path.toFile().listFiles();
            if (files == null) return samples;
            for (File file : files){
                try {
                    GrayScaleImage img = GrayScaleImage.load(file);
                    double[] input = extractor.getFeatures(img);
                    Sample sample = new Sample(value, input);
                    samples.add(sample);
                } catch (IOException e) {
                    System.err.println("Unable to load image!");
                }
            }
        }
        return samples;
    }
}
