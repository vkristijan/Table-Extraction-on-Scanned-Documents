package hr.fer.zemris.zavrad.detection.adaboost;

import hr.fer.zemris.zavrad.detection.features.feature.haar.HaarFeature;
import hr.fer.zemris.zavrad.detection.training.Sample;
import hr.fer.zemris.zavrad.table.CornerValue;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.IntegralImage;

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
public class AdaBoost {
    public static void main(String[] args) {
        Path dataPath = Paths.get(args[0]);
        List<IntegralImage> positive = new ArrayList<>();
        List<IntegralImage> negative = new ArrayList<>();

        loadData(dataPath, positive, negative);

        ClassifierCreator creator = new ClassifierCreator(1000);
        double[] weights = new double[positive.size() + negative.size()];
        for (int i = 0; i < weights.length; ++i){
            weights[i] = 1;
        }

        WeakClassifier feature = creator.getClassifier(weights, positive, negative);
    }

    private static void loadData(Path dataPath, List<IntegralImage> positive, List<IntegralImage> negative) {
        for (CornerValue value : CornerValue.values()){
            Path path = dataPath.resolve(String.valueOf(value.getValue()));
            if (!Files.exists(path)) continue;

            File[] files = path.toFile().listFiles();
            if (files == null) continue;
            for (File file : files){
                try {
                    GrayScaleImage img = GrayScaleImage.load(file);
                    IntegralImage integral = IntegralImage.fromGrayscaleImage(img);

                    if (value.equals(CornerValue.NONE)) {
                        negative.add(integral);
                    } else {
                        positive.add(integral);
                    }
                } catch (IOException e) {
                    System.err.println("Unable to load image!");
                }
            }
        }
    }
}
