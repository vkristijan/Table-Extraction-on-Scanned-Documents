package hr.fer.zemris.zavrad.table;

import hr.fer.zemris.zavrad.util.Point;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.draw.Geometry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Table {
    private double minDist = 45;

    private GrayScaleImage image;
    private List<List<Corner>> corners;

    public Table(GrayScaleImage image, List<List<Corner>> corners) {
        this.image = image;
        this.corners = corners;
    }

    public Table(List<Corner> cornerList, GrayScaleImage image){
        this.image = image;

        List<Corner> filteredCorners = filterCorners(cornerList);
        cornerList.sort(Comparator.naturalOrder());

        System.out.println(filteredCorners.size());
        for (Corner corner : filteredCorners){
            int x = corner.getPosition().getX();
            int y = corner.getPosition().getY();

            for (int ii = 0; ii < 9; ++ii){
                try {
                    Geometry.drawSquare(image, x - ii, y - ii, 2 * ii);
                } catch (Exception ignored){
                }
            }
        }




    }

    private List<Corner> filterCorners(List<Corner> cornerList) {
        List<Corner> filteredCorners = new ArrayList<>();
        for (Corner corner : cornerList){
            if (containsNearby(corner, filteredCorners)) continue;

            List<Corner> nearby = new ArrayList<>();
            for (Corner c : cornerList){
                if (c.getValue().equals(corner.getValue()) && c.distance(corner) < minDist){
                    nearby.add(c);
                }
            }
            if (nearby.size() == 1) continue;

            double maxDist = 0;
            Corner center = null;
            double bestDist = 0;

            for (Corner c1 : nearby){
                double total = 0;
                for (Corner c2 : nearby){
                    if (c1 == c2) continue;

                    double dist = c1.distance(c2);
                    total += dist;
                    if (dist > maxDist) maxDist = dist;
                }

                if (center == null || total < bestDist){
                    center = c1;
                    bestDist = total;
                }
            }

            if (maxDist > minDist || center == null) continue;
            if (containsNearby(center, filteredCorners)) continue;
            filteredCorners.add(center);
        }

        return filteredCorners;
    }

    private boolean containsNearby(Corner corner, List<Corner> cornerList) {
        for (Corner c : cornerList){
            if (c == corner || c.distance(corner) < minDist){
                return true;
            }
        }

        return false;
    }

    private Corner getCornerAverage(List<Corner> cornerList, CornerValue value){
        List<Corner> corners = cornerList.stream()
                .filter(c -> c.getValue().equals(value))
                .collect(Collectors.toList());

        Point position = new Point(0, 0);
        for (Corner corner : corners){
            position.setX(position.getX() + corner.getPosition().getX());
            position.setY(position.getY() + corner.getPosition().getY());
        }
        position.setX(position.getX() / corners.size());
        position.setY(position.getY() / corners.size());

        return new Corner(value, position);
    }

    public int getRowCount(){
        return corners.get(0).size() - 1;
    }

    public int getColumnCount(){
        return corners.size() - 1;
    }

    public GrayScaleImage getCellContent(int row, int column){
        Corner from = corners.get(column).get(row);
        Corner to = corners.get(column + 1).get(row + 1);

        int x = from.getPosition().getX();
        int y = from.getPosition().getY();
        int width = to.getPosition().getX() - x;
        int height = to.getPosition().getY() - y;

        return image.getSubset(x, y, width, height);
    }
}
