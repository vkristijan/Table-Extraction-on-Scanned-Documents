package hr.fer.zemris.zavrad;

import hr.fer.zemris.zavrad.filters.ImageFilter;
import hr.fer.zemris.zavrad.filters.threshold.ThresholdBinarization;
import hr.fer.zemris.zavrad.skew.SkewDetection;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Starter {
    public static void main(String[] args) throws IOException {
        long start_time = System.nanoTime();
        Path path = Paths.get(args[0]);
        GrayScaleImage img = GrayScaleImage.load(path.toFile());

        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;
        start_time = end_time;
        System.out.println("Loading time: " + difference + "ms");

        ImageFilter binarization = new ThresholdBinarization(200);
        img = binarization.filter(img);

        SkewDetection skew = new SkewDetection();
        double angle = skew.getAngle(img);
        System.out.println(angle);

        end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;
        start_time = end_time;
        System.out.println("Skew time: " + difference + "ms");

        Path output = Paths.get(args[1]);
        img.save(output.toFile());

        end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;
        System.out.println("Saving time: " + difference + "ms");
    }
}
