package hr.fer.zemris.zavrad.detection.sampling;

import hr.fer.zemris.zavrad.util.Point;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.filters.ImageFilter;
import hr.fer.zemris.zavrad.util.img.filters.threshold.ThresholdBinarization;

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
public class NegativeSampler {
    private static int threshold = 127;
    private static ImageFilter binarization = new ThresholdBinarization(threshold);

    public static void main(String[] args) throws IOException {
        List<Point> points = getPoints();

        Path path = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        NegativeSampling sampling = new NegativeSampling(75, points, output);

        Files.list(path).forEach(
                p -> {
                    GrayScaleImage img;
                    try {
                        img = GrayScaleImage.load(p.toFile());
                        img = binarization.filter(img);
                        sampling.getSamples(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static List<Point> getPoints() {
        List<Point> none = new ArrayList<>();
        none.add(new Point(2198, 229));
        none.add(new Point(2362, 227));
        none.add(new Point(2535, 234));
        none.add(new Point(2198, 396));
        none.add(new Point(2354, 399));
        none.add(new Point(2519, 410));
        none.add(new Point(2195, 565));
        none.add(new Point(2359, 561));
        none.add(new Point(2522, 564));
        none.add(new Point(2195, 737));
        none.add(new Point(2362, 740));
        none.add(new Point(2520, 739));
        none.add(new Point(2191, 907));
        none.add(new Point(2358, 906));
        return none;
    }
}
