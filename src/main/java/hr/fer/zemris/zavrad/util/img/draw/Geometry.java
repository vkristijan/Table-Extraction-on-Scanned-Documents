package hr.fer.zemris.zavrad.util.img.draw;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Geometry {
    public static void drawRectangle(GrayScaleImage img, int x, int y, int width, int height){
        drawRectangle(img, x, y, width, height, (byte)GrayScaleImage.BLACK);
    }

    public static void drawSquare(GrayScaleImage img, int x, int y, int size, byte color){
        drawRectangle(img, x, y, size, size, color);
    }

    public static void drawSquare(GrayScaleImage img, int x, int y, int size){
        drawSquare(img, x, y, size, (byte)GrayScaleImage.BLACK);
    }

    public static void drawRectangle(GrayScaleImage img, int x, int y, int width, int height, byte color){
        byte[][] data = img.getData();

        int fromX = Math.max(x, 0);
        int toX = Math.min(x + width, img.getWidth());

        int fromY = Math.max(y, 0);
        int toY = Math.min(y + height, img.getHeight());

        if (fromX >= toX || fromY >= toY) return;

        for (int i = fromY; i < toY; ++i){
            for (int j = fromX; j < toX; ++j){
                data[i][j] = color;
            }
        }
    }
}
