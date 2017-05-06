package hr.fer.zemris.zavrad.detection;

import hr.fer.zemris.zavrad.util.Line;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class LineDetection {
    private List<Line> horizontalLines;
    private List<Line> verticalLines;

    public LineDetection(GrayScaleImage img){
        horizontalLines = new ArrayList<>();
        verticalLines = new ArrayList<>();

        int width = img.getWidth();
        int height = img.getHeight();

        for (int i = 0; i < width; ++i){

        }
    }
}
