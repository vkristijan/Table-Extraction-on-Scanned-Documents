package hr.fer.zemris.zavrad.detection.sampling;

import hr.fer.zemris.zavrad.util.Point;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class PositiveSampling {
    private static int COUNTER = 0;

    private int sliderSize;
    private List<List<Point>> points;
    private Path outputPath;


    public PositiveSampling(int sliderSize, List<List<Point>> points, Path outputPath) {
        this.sliderSize = sliderSize;
        this.points = points;
        this.outputPath = outputPath;
    }

    public void getSamples(GrayScaleImage image) throws IOException {
        byte[][] data = image.getData();

        for (int i = 0; i < points.size(); ++i){
            Path currentPath = outputPath.resolve(String.valueOf(i));
            Files.deleteIfExists(currentPath);
            Files.createDirectory(currentPath);

            for (Point p : points.get(i)){
                GrayScaleImage sample = new GrayScaleImage(sliderSize, sliderSize);
                byte[][] sampleData = sample.getData();

                int fromX = p.getX() - sliderSize / 2;
                int fromY = p.getY() - sliderSize / 2;
                int toX = fromX + sliderSize;
                int toY = fromY + sliderSize;

                if (fromX < 0 || fromY < 0) continue;
                if (toX >= image.getWidth() || toY >= image.getHeight()) continue;

                for (int x = 0; x < sliderSize; ++x){
                    for (int y = 0; y < sliderSize; ++y){
                        sampleData[y][x] = data[y + fromY][x + fromX];
                    }
                }

                Path output = currentPath.resolve(String.valueOf(COUNTER) + ".png");
                COUNTER++;
                try {
                    sample.save(output.toFile());
                } catch (IOException e) {
                    System.err.println("Unable to save a generated sample!");
                }
            }
        }
    }
}
