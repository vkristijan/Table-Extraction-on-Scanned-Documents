package hr.fer.zemris.zavrad.gui;

import com.sun.org.apache.xpath.internal.operations.Bool;
import hr.fer.zemris.zavrad.table.Corner;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class JPictureBox extends JPanel {
    private boolean isSquare;
    private BufferedImage image;

    public void setPicture(Path filePath){
        try {
            image = ImageIO.read(filePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        repaint();
    }

    public void setPicture(GrayScaleImage img){
        image = img.toBufferedImage();
        repaint();
    }

    public void setSquare(boolean value){
        this.isSquare = value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        if (isSquare){
            int size = Math.min(getWidth(), getHeight());
            g.drawImage(image, 0, 0, size, size, Color.BLACK, this);
        } else {
            int w = getWidth();
            int h = getHeight();
            double ratio = (double) w / h;

            int imgW = image.getWidth();
            int imgH = image.getHeight();
            double imgRatio = (double) imgW / imgH;

            int width = w;
            int height = h;
            if (ratio > imgRatio){
                width = (int) (imgRatio * h);
            }

            int x = (getWidth() - width) / 2;
            g.drawImage(image, x, 0, width, height, Color.BLACK, this);
        }
    }
}
