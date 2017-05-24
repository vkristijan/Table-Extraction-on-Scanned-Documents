package hr.fer.zemris.zavrad.util.img;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Representation of a grayscale image.
 *
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GrayScaleImage {
    public static int BLACK = 0;

    protected int width;
    protected int height;
    protected byte[][] data;

    public GrayScaleImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new byte[height][width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void clear(byte color) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                data[h][w] = color;
            }
        }
    }

    public byte[][] getData() {
        return data;
    }

    public void save(File file) throws IOException {
        BufferedImage bim = toBufferedImage();

        try {
            ImageIO.write(bim, "png", file);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public static GrayScaleImage load(File file) throws IOException {
        BufferedImage bim = ImageIO.read(file);
        if (bim.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new IOException("Slika nije grayscale.");
        }

        return fromBufferedImage(bim);
    }

    public double getLocalMean(int h, int w, int n, int m){
        double mean = 0;

        int fromH = h - n / 2;
        int toH = fromH + n;

        int fromW = w - m / 2;
        int toW = fromW + m;

        if (fromH < 0) fromH = 0;
        if (fromW < 0) fromW = 0;
        if (toH > height) toH = height;
        if (toW > width) toW = width;

        for (int i = fromH; i < toH; ++i){
            for (int j = fromW; j < toW; ++j){
                mean += (int)data[i][j] & 0xFF;
            }
        }
        mean /= (toH - fromH);
        mean /= (toW - fromW);

        return mean;
    }

    public double getLocalVariance(int h, int w, int n, int m){
        double variance = 0;

        int fromH = h - n / 2;
        int toH = fromH + n;

        int fromW = w - m / 2;
        int toW = fromW + m;

        if (fromH < 0) fromH = 0;
        if (fromW < 0) fromW = 0;
        if (toH > height) toH = height;
        if (toW > width) toW = width;

        double mean = getLocalMean(h, w, n, m);
        for (int i = fromH; i < toH; ++i){
            for (int j = fromW; j < toW; ++j){
                int pixel = (int)data[i][j] & 0xFF;
                variance += pixel * pixel;
            }
        }
        variance /= (toW - fromW);
        variance /= (toH - fromH);
        variance -= mean * mean;

        return variance;
    }

    public double getNoiseVariance(int n, int m){
        double noise = 0;

        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                noise += getLocalVariance(i, j, n, m);
            }
        }

        return noise / (width * height);
    }

    public BufferedImage toBufferedImage() {
        BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] buf = new int[1];
        WritableRaster r = bim.getRaster();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                buf[0] = (int) data[h][w] & 0xFF;
                r.setPixel(w, h, buf);
            }
        }

        return bim;
    }

    public static GrayScaleImage fromBufferedImage(BufferedImage bim) {
        GrayScaleImage im = new GrayScaleImage(bim.getWidth(), bim.getHeight());
        try {
            int[] buf = new int[1];
            WritableRaster r = bim.getRaster();
            for (int h = 0; h < im.height; h++) {
                for (int w = 0; w < im.width; w++) {
                    r.getPixel(w, h, buf);
                    im.data[h][w] = (byte) buf[0];
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Slika nije grayscale.");
        }

        return im;
    }

    public GrayScaleImage getSubset(int fromX, int fromY, int w, int h){
        GrayScaleImage sample = new GrayScaleImage(w, h);
        byte[][] sampleData = sample.getData();

        int toX = fromX + w;
        int toY = fromY + h;

        if (fromX < 0 || fromY < 0
                || toX >= getWidth()
                || toY >= getHeight()){
            throw new IndexOutOfBoundsException("The given coordinates are outside the image!");
        }

        for (int x = 0; x < w; ++x){
            for (int y = 0; y < h; ++y){
                sampleData[y][x] = data[y + fromY][x + fromX];
            }
        }

        return sample;
    }
}