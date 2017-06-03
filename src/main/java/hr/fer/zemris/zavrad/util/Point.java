package hr.fer.zemris.zavrad.util;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Point implements Comparable<Point>{
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(Point other){
        double dist = (x - other.x) * (x - other.x);
        dist += (y - other.y) * (y - other.y);

        return Math.sqrt(dist);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public int compareTo(Point o) {
        if (Math.abs(o.x - this.x) < 45){
            return Integer.compare(this.x, o.x);
        }
        return Integer.compare(this.y, o.y);
    }
}
