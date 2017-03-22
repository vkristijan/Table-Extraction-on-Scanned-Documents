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

        ImageFilter wiener = new WienerFilter(9, 9);
        GrayScaleImage result = wiener.filter(img);

        Path output = Paths.get(args[1]);
        result.save(output.toFile());
    }
}
