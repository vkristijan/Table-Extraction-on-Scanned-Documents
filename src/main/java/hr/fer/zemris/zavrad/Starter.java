package hr.fer.zemris.zavrad;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Starter {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        GrayScaleImage img = GrayScaleImage.load(path.toFile());

        ImageFilter wiener = new WienerFilter(5, 5);
        GrayScaleImage wienerResult = wiener.filter(img);

        ImageFilter niblack = new NiblackThreshold(20, 20, -0.2);
        GrayScaleImage niblackResult = niblack.filter(img);

        ImageFilter interpolator = new BackgroundEstimation(3, 3);
        GrayScaleImage background = interpolator.filter(wienerResult, niblackResult);

        ImageFilter finalThreshold = new AdaptiveThreshold(0.8);
        GrayScaleImage threshold = finalThreshold.filter(background, wienerResult, niblackResult);

        int n = 5;
        ImageFilter shrink = new ShrinkFilter(n, 0.8);
        GrayScaleImage shrinked = shrink.filter(threshold);

        ImageFilter swell1 = new Swell(n, 0.1);
        GrayScaleImage swelled = swell1.filter(shrinked);

        Path output = Paths.get(args[1]);
        swelled.save(output.toFile());
    }
}
