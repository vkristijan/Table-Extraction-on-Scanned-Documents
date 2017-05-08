package hr.fer.zemris.zavrad.detection.ga.evaluator;

import hr.fer.zemris.zavrad.detection.CornerDetection;
import hr.fer.zemris.zavrad.detection.CornerValues;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.detection.ga.Chromosome;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerEvaluator implements IEvaluator {
    private List<Sample> samples;
    private CornerDetection detection;
    private IFeatureExtractor extractor;

    public CornerEvaluator(Path dataPath, CornerDetection detection, IFeatureExtractor extractor) {
        this.detection = detection;
        this.extractor = extractor;
        this.samples = new ArrayList<>();

        for (CornerValues value : CornerValues.values()){
            Path path = dataPath.resolve(String.valueOf(value.getValue()));
            loadSamples(path, value);
        }
    }

    private void loadSamples(Path path, CornerValues value) {
        if (!Files.exists(path)) return;

        File[] files = path.toFile().listFiles();
        if (files == null) return;
        for (File file : files){
            try {
                GrayScaleImage img = GrayScaleImage.load(file);
                Sample sample = new Sample(img, value);
                samples.add(sample);
            } catch (IOException e) {
                System.err.println("Unable to load the image!");
            }
        }
    }

    @Override
    public void evaluate(Chromosome chromosome) {
        double fitness = 0;
        double detected = 0;

        for (Sample sample : samples){
            double[] input = extractor.getFeatures(sample.img);
            CornerValues detectedValue = detection.detect(input);

            if (detectedValue.equals(sample.value)){
                detected++;
            }
        }

        fitness = detected / samples.size();
        chromosome.setFitness(fitness);
    }

    private class Sample {
        private GrayScaleImage img;
        private CornerValues value;

        Sample(GrayScaleImage img, CornerValues value) {
            this.img = img;
            this.value = value;
        }
    }
}
