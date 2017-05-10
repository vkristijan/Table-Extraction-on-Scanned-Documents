package hr.fer.zemris.zavrad.detection;

import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.table.Corner;
import hr.fer.zemris.zavrad.table.CornerValue;
import hr.fer.zemris.zavrad.util.Point;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class SlidingWindow {
    private int windowSize;
    private int step;

    private CornerDetection detection;
    private IFeatureExtractor extractor;

    public SlidingWindow(int windowSize, int step, CornerDetection detection, IFeatureExtractor extractor) {
        this.windowSize = windowSize;
        this.step = step;
        this.detection = detection;
        this.extractor = extractor;
    }

    public List<Corner> detectCorners(GrayScaleImage img){
        List<Corner> corners = new ArrayList<>();

        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height - windowSize; y += step){
            for (int x = 0; x < width - windowSize; x += step){
                double[] features = extractor.getFeatures(img, x, y, windowSize, windowSize);
                CornerValue value = detection.detect(features);
                if (value.equals(CornerValue.NONE)) continue;

                Point position = new Point(x + windowSize / 2, y + windowSize / 2);
                boolean shouldConsider = true;
                for (int i = corners.size() - 1; i >= 0; --i){
                    if (corners.get(i).getPosition().getY() + windowSize/2 > position.getY()){
                        break;
                    }

                    if (corners.get(i).getPosition().getX() + windowSize/2 > position.getX()){
                        shouldConsider = false;
                        break;
                    }
                }
                Corner corner = new Corner(value, position);
                corners.add(corner);

                x += windowSize / 2;
            }
        }

        return corners;
    }
}
