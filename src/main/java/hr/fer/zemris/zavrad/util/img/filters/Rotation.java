package hr.fer.zemris.zavrad.util.img.filters;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Rotation implements ImageFilter {
    private double degree;
    private double angle;

    public Rotation(double angle) {
        degree = angle;

        angle /= 180;
        angle *= Math.PI;

        this.angle = angle;
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage... images) {
        if (images.length != 1){
            throw new ImageFilterException("Rotation filter expects 1 argument1!");
        }

        if (Math.abs(degree) < 1){
            return images[0];
        }

        BufferedImage img = images[0].toBufferedImage();

        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        img = op.filter(img, null);

        BufferedImage bim = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = bim.createGraphics();
        g.drawImage(img, 0, 0, null);

        return GrayScaleImage.fromBufferedImage(bim);
    }
}
