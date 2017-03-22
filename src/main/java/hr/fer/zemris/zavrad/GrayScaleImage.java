package hr.fer.zemris.zavrad;

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
    private int width;
    private int height;
    private byte[][] data;

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
        BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] buf = new int[1];
        WritableRaster r = bim.getRaster();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                buf[0] = (int) data[h][w] & 0xFF;
                r.setPixel(w, h, buf);
            }
        }
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
            throw new IOException("Slika nije grayscale.");
        }
        return im;
    }

}