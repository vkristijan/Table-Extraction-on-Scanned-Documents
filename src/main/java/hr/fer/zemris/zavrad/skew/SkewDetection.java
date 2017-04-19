package hr.fer.zemris.zavrad.skew;

import hr.fer.zemris.zavrad.GrayScaleImage;
import hr.fer.zemris.zavrad.skew.lines.LineDistribution;
import hr.fer.zemris.zavrad.skew.lines.RandomLineDistribution;
import hr.fer.zemris.zavrad.util.Rnd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class SkewDetection {
    private LineDistribution lineDistribution = new RandomLineDistribution();

    private int maxAngle = 12;
    private int maxTry = 10;
    private int lineCount = 10;
    private int pointsToCheck = 4;

    public SkewDetection() {
    }

    public SkewDetection(int lineCount, int pointsToCheck) {
        this.lineCount = lineCount;
        this.pointsToCheck = pointsToCheck;
    }

    public int getMaxAngle() {
        return maxAngle;
    }

    public void setMaxAngle(int maxAngle) {
        this.maxAngle = maxAngle;
    }

    public int getMaxTry() {
        return maxTry;
    }

    public void setMaxTry(int maxTry) {
        this.maxTry = maxTry;
    }

    private byte[][] data1;
    public double getAngle(GrayScaleImage img){
        data1 = img.getData();
        if (img.getWidth() > img.getHeight()){
            List<Integer> lines = lineDistribution.getLines(img.getWidth(), lineCount);
            List<List<Integer>> points = calculatePointsVertical(lines, img);
            removeRedundant(points, lines);

            return getAngle(points, lines, false);
        } else {
            List<Integer> lines = lineDistribution.getLines(img.getHeight(), lineCount);
            List<List<Integer>> points = calculatePointsHorizontal(lines, img);
            //visualize(img, lines);
            removeRedundant(points, lines);
            visualize1(img, lines);

            return getAngle(points, lines, true);
        }
    }

    private double getAngle(List<List<Integer>> points, List<Integer> lines, boolean horizontal) {
        double angle = 0;

        for (int iteration = 0; iteration < maxTry; ++iteration){
            //all the angles from +90 to -90 (including 0) with a step of 0.5
            int[] angles = new int[361];
            for (int i = 0; i < pointsToCheck; ++i){
                calculateAngles(angles, points, lines, horizontal);
            }

            int max = 0;
            int value = -1;
            for (int i = 180 - 2 * maxAngle; i <= 180 + 2 * maxAngle; ++i){
                if (angles[i] > max){
                    max = angles[i];
                    value = i;
                }
            }
            angle = (value - 180) / 2.0;

            boolean hasValue = true;
            for (int i = 180 - 2 * maxAngle; i <= 180 + 2 * maxAngle; ++i){
                if (Math.abs(value - i) > 3 && angles[i] > 0.6 * max){
                    hasValue = false;
                    break;
                }
            }

            if (hasValue) return angle;
        }

        return angle;
    }

    private void calculateAngles(int[] angles, List<List<Integer>> points, List<Integer> lines, boolean horizontal) {
        int pointX = lines.get(0);
        int pointY = points.get(0).get(Rnd.nextInt(points.get(0).size()));

        int lineCount = lines.size();

        for (int i = 1; i < lineCount; ++i){
            int x = lines.get(i);
            for (int y : points.get(i)){
                //the angle should be [90, -90]
                double angle;
                if (horizontal){
                    //coordinates swapped because of horizontal lines
                    angle = calculateAngle(pointX, pointY, x, y);
                } else {
                    angle = -calculateAngle(pointX, pointY, x, y);
                }
                int index = (int)(angle * 2);
                index += 180;

                if (index < 0 || index >= 361) continue;
                angles[index]++;
            }
        }
    }

    private double calculateAngle(int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double angle = Math.atan(dy / dx);
        angle /= (Math.PI / 2);
        angle *= 90;

        return angle;
    }

    /**
     * Removes the lines that cross vertical table lines, or do not cross the table.
     *
     * @param points collection of all the crossing points.
     * @param lines collection of all the lines.
     */
    private void removeRedundant(List<List<Integer>> points, List<Integer> lines) {
        List<Integer> count = new ArrayList<>();

        for (int i = 0; i < lineCount; ++i){
            count.add(points.get(i).size());
        }
        count.sort(Comparator.naturalOrder());

        int median = count.get(count.size() / 2);

        for (int i = lineCount - 1; i >= 0; --i){
            int x = points.get(i).size();
            if (x < median / 2 || x > median * 2) {
                lines.remove(i);
                points.remove(i);
            }
        }
    }

    private List<List<Integer>> calculatePointsVertical(List<Integer> lines, GrayScaleImage img) {
        List<List<Integer>> points = new ArrayList<>();

        int height = img.getHeight();
        byte[][] data = img.getData();
        for (int i = 0; i < lineCount; ++i){
            int x = lines.get(i);

            List<Integer> linePoints = new ArrayList<>();
            for (int y = 0; y < height; ++y){
                if (data[y][x] == GrayScaleImage.BLACK){
                    linePoints.add(y);
                    y += 5;
                }
            }
            points.add(linePoints);
        }

        return points;
    }

    private List<List<Integer>> calculatePointsHorizontal(List<Integer> lines, GrayScaleImage img) {
        List<List<Integer>> points = new ArrayList<>();

        int width = img.getWidth();
        byte[][] data = img.getData();
        for (int i = 0; i < lineCount; ++i){
            int y = lines.get(i);

            List<Integer> linePoints = new ArrayList<>();
            for (int x = 0; x < width; ++x){
                if (data[y][x] == GrayScaleImage.BLACK){
                    linePoints.add(x);
                    x += 4;
                }
            }
            points.add(linePoints);
        }

        return points;
    }

    private void visualize(GrayScaleImage img, List<Integer> lines) {
        byte[][] data = img.getData();

        for (int i = 0; i < lines.size(); ++i){
            int y = lines.get(i);

            for (int x = 0; x < img.getWidth(); ++x){
                data[y][x] = (byte) GrayScaleImage.BLACK;
            }
        }
    }

    private void visualize1(GrayScaleImage img, List<Integer> lines) {
        byte[][] data = img.getData();

        for (int i = 0; i < lines.size(); ++i){
            int y = lines.get(i);

            for (int x = 0; x < img.getWidth(); ++x){
                data[y-1][x] = (byte) GrayScaleImage.BLACK;
                data[y+1][x] = (byte) GrayScaleImage.BLACK;
                data[y][x] = (byte) GrayScaleImage.BLACK;
            }
        }
    }
}
