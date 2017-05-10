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

        for (int i = x; i < x + width; ++i){
            data[y][i] = color;
            data[y + height - 1][i] = color;
        }

        for (int i = y; i < y + height; ++i){
            data[i][x] = color;
            data[i][x + width - 1] = color;
        }
    }
}
