package hr.fer.zemris.zavrad.detection.features;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerExtractorDemo {
    public static void main(String[] args) throws IOException {
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);

        GrayScaleImage img = GrayScaleImage.load(input.toFile());
        CornerFeatureExtractor extractor = new CornerFeatureExtractor();
        double[] features = extractor.getFeatures(img);

        for (double feature : features){
            System.out.println(feature);
        }

        img.save(output.toFile());
    }
}
