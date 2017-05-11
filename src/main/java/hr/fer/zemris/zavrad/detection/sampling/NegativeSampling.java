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
public class NegativeSampling implements ISampling {
    private static int COUNTER = 7200;

    private int sliderSize;
    private List<Point> points;
    private Path outputPath;


    public NegativeSampling(int sliderSize, List<Point> points, Path outputPath) {
        this.sliderSize = sliderSize;
        this.points = points;
        this.outputPath = outputPath;
    }

    public void getSamples(GrayScaleImage image) throws IOException {
        for (int i = 0; i < points.size(); ++i){
            Path currentPath = outputPath;
            if (!Files.exists(currentPath)){
                Files.createDirectory(currentPath);
            }

            for (Point p : points){
                GrayScaleImage sample = new GrayScaleImage(sliderSize, sliderSize);

                int fromX = p.getX() - sliderSize / 2;
                int fromY = p.getY() - sliderSize / 2;

                for (int x = -50; x <= 50; x += 25){
                    for (int y = -50; y <= 50; y += 25){
                        extractImage(image, sample, fromX + x, fromY + y, currentPath);
                    }
                }
            }
        }
    }

    private void extractImage(GrayScaleImage image, GrayScaleImage sample, int fromX, int fromY, Path currentPath){
        byte[][] sampleData = sample.getData();
        byte[][] data = image.getData();
        int toX = fromX + sliderSize;
        int toY = fromY + sliderSize;

        if (fromX < 0 || fromY < 0) return;
        if (toX >= image.getWidth() || toY >= image.getHeight()) return;

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
