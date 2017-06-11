package hr.fer.zemris.zavrad.gui;

import hr.fer.zemris.zavrad.skew.SkewDetection;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.IntegralImage;
import hr.fer.zemris.zavrad.util.img.filters.ImageFilter;
import hr.fer.zemris.zavrad.util.img.filters.Rotation;
import hr.fer.zemris.zavrad.util.img.filters.threshold.ThresholdBinarization;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class TableDetector extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TableDetector::new);
    }

    private JPictureBox picture;
    private SkewDetection skewDetection;
    private IntegralImage img;
    ImageFilter binarization;

    public TableDetector(){
        skewDetection = new SkewDetection();
        binarization = new ThresholdBinarization(127);

        setTitle("Table Detection");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initGui();

        setVisible(true);
    }

    private void initGui(){
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        picture = new JPictureBox();
        Path imgPath = Paths.get("C:\\GitHub\\Table-Extraction-on-Scanned-Documents\\src\\main\\resources\\img.png");
        picture.setPicture(imgPath);

        cp.add(picture, BorderLayout.CENTER);

        JPanel methods = new JPanel();
        methods.setLayout(new BorderLayout());
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 1));
        cp.add(methods, BorderLayout.WEST);

        JButton load = new JButton("Load image");
        load.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Load");
            if (fc.showOpenDialog(TableDetector.this) != JFileChooser.APPROVE_OPTION){
                return;
            }

            Path imagePath = fc.getSelectedFile().toPath();
            try {
                GrayScaleImage grayScaleImage = GrayScaleImage.load(imagePath.toFile());
                grayScaleImage = binarization.filter(grayScaleImage);
                img = IntegralImage.fromGrayscaleImage(grayScaleImage);
            } catch (IOException ignored) {
            }
            picture.setPicture(img);
        });
        buttons.add(load);

        JButton skew = new JButton("Skew detection");
        skew.addActionListener(e -> {
            double angle = skewDetection.getAngle(img);
            ImageFilter rotation = new Rotation(angle);
            GrayScaleImage grayScaleImage = rotation.filter(img);
            img = IntegralImage.fromGrayscaleImage(grayScaleImage);
            picture.setPicture(img);
        });
        buttons.add(skew);

        JButton neural = new JButton("Neural network");
        neural.addActionListener(e -> {

        });
        buttons.add(neural);

        JButton ada = new JButton("AdaBoost");
        ada.addActionListener(e -> {

        });
        buttons.add(ada);

        JButton combined = new JButton("Neural network + AdaBoost");
        combined.addActionListener(e -> {

        });
        buttons.add(combined);

        JButton detect = new JButton("Detect table");
        detect.addActionListener(e -> {

        });
        buttons.add(detect);
        methods.add(buttons, BorderLayout.NORTH);

        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new BorderLayout());

        JPanel cellIDNumbers = new JPanel();
        cellIDNumbers.setLayout(new GridLayout(1,2 ));
        JTextField rowNumber = new JTextField();
        cellIDNumbers.add(rowNumber);
        JTextField colNumber = new JTextField();
        cellIDNumbers.add(colNumber);

        cellPanel.add(cellIDNumbers, BorderLayout.NORTH);
        JPictureBox cell = new JPictureBox();
        cell.setSquare(true);
        cell.setPicture(imgPath);
        cellPanel.add(cell, BorderLayout.CENTER);

        methods.add(cellPanel);
    }
}
