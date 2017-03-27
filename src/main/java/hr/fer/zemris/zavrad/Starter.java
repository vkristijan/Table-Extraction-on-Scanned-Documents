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

        Interpolator interpolator = new BackgroundEstimation(3, 3);
        GrayScaleImage background = interpolator.interpolate(wienerResult, niblackResult);

        Path output = Paths.get(args[1]);
        background.save(output.toFile());
    }
}
