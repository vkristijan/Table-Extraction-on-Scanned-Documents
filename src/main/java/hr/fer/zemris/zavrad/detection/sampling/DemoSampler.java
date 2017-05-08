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
public class DemoSampler {
    private static int threshold = 127;
    private static ImageFilter binarization = new ThresholdBinarization(threshold);

    public static void main(String[] args) throws IOException {
        List<List<Point>> points = getPoints();

        Path path = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        PositiveSampling sampling = new PositiveSampling(50, points, output);

        Files.list(path).forEach(
                p -> {
                    GrayScaleImage img = null;
                    try {
                        img = GrayScaleImage.load(p.toFile());
                        img = binarization.filter(img);
                        sampling.getSamples(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static List<List<Point>> getPoints() {
        List<List<Point>> points = new ArrayList<>();
        List<Point> ul = new ArrayList<>();
        ul.add(new Point(2110, 148));
        ul.add(new Point(2110, 1390));
        points.add(ul);

        List<Point> top = new ArrayList<>();
        top.add(new Point(2272, 148));
        top.add(new Point(2438, 148));
        top.add(new Point(2272, 1390));
        top.add(new Point(2438, 1390));
        points.add(top);

        List<Point> ur = new ArrayList<>();
        ur.add(new Point(2603, 148));
        ur.add(new Point(2603, 1390));
        points.add(ur);

        List<Point> l = new ArrayList<>();
        l.add(new Point(2110, 315));
        l.add(new Point(2110, 485));
        l.add(new Point(2110, 655));
        l.add(new Point(2110, 822));
        l.add(new Point(2110, 989));
        l.add(new Point(2110, 1558));
        l.add(new Point(2110, 1727));
        l.add(new Point(2110, 1895));
        l.add(new Point(2110, 2063));
        points.add(l);

        List<Point> c = new ArrayList<>();
        c.add(new Point(2276, 315));
        c.add(new Point(2438, 315));
        c.add(new Point(2276, 485));
        c.add(new Point(2438, 485));
        c.add(new Point(2276, 655));
        c.add(new Point(2438, 655));
        c.add(new Point(2276, 822));
        c.add(new Point(2438, 822));
        c.add(new Point(2276, 989));
        c.add(new Point(2438, 989));
        c.add(new Point(2276, 1558));
        c.add(new Point(2438, 1558));
        c.add(new Point(2276, 1727));
        c.add(new Point(2438, 1727));
        c.add(new Point(2276, 1895));
        c.add(new Point(2438, 1895));
        c.add(new Point(2276, 2063));
        c.add(new Point(2438, 2063));
        points.add(c);

        List<Point> r = new ArrayList<>();
        r.add(new Point(2602, 315));
        r.add(new Point(2602, 485));
        r.add(new Point(2602, 655));
        r.add(new Point(2602, 822));
        r.add(new Point(2602, 989));
        r.add(new Point(2602, 1558));
        r.add(new Point(2602, 1727));
        r.add(new Point(2602, 1895));
        r.add(new Point(2602, 2063));
        points.add(r);

        List<Point> dl = new ArrayList<>();
        dl.add(new Point(2110, 1158));
        dl.add(new Point(2110, 2230));
        points.add(dl);

        List<Point> b = new ArrayList<>();
        b.add(new Point(2276, 1158));
        b.add(new Point(2438, 1158));
        b.add(new Point(2276, 2230));
        b.add(new Point(2438, 2230));
        points.add(b);

        List<Point> dr = new ArrayList<>();
        dr.add(new Point(2602, 1158));
        dr.add(new Point(2602, 2230));
        points.add(dr);

        return points;
    }
}
