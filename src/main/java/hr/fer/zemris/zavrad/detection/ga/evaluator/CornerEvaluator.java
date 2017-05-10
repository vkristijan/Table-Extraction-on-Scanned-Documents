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
    private Path dataPath;

    private List<Sample> samples;
    private CornerDetection detection;
    private IFeatureExtractor extractor;

    public CornerEvaluator(Path dataPath, CornerDetection detection, IFeatureExtractor extractor) {
        this.dataPath = dataPath;
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
                double[] input = extractor.getFeatures(img);
                Sample sample = new Sample(value, input);
                samples.add(sample);
            } catch (IOException e) {
                System.err.println("Unable to load the image!");
            }
        }
    }

    @Override
    public IEvaluator copy() {
        return new CornerEvaluator(dataPath, detection.copy(), extractor);
    }

    @Override
    public void evaluate(Chromosome chromosome) {
        double fitness = 0;
        int n = CornerValues.values().length;
        double[] detected = new double[n];
        double[] total = new double[n];

        detection.setWeights(chromosome.getData());
        for (Sample sample : samples){
            CornerValues detectedValue = detection.detect(sample.input);

            total[sample.value.getValue()]++;
            if (detectedValue.equals(sample.value)){
                detected[detectedValue.getValue()]++;
            }
        }

        int count = 0;
        for (int i = 0; i < n; ++i){
            if (total[i] > 0){
                fitness += (detected[i] / total[i]);
                count++;
            }
        }
        fitness /= count;
        chromosome.setFitness(fitness);
    }

    @Override
    public void showError(Chromosome chromosome) {
        detection.setWeights(chromosome.getData());
        for (Sample sample : samples){
            CornerValues detectedValue = detection.detect(sample.input);

            if (!detectedValue.equals(sample.value)){
                System.out.println("Got " + detectedValue + ", expected " + sample.value);
            }
        }
    }

    private class Sample {
        private CornerValues value;
        private double[] input;

        Sample(CornerValues value, double[] input) {
            this.value = value;
            this.input = input;
        }
    }
}
