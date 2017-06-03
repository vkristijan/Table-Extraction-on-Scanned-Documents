package hr.fer.zemris.zavrad.table;

import hr.fer.zemris.zavrad.util.Point;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Corner implements Comparable<Corner>{
    private CornerValue value;
    private Point position;

    public Corner(CornerValue value, Point position) {
        this.value = value;
        this.position = position;
    }

    public CornerValue getValue() {
        return value;
    }

    public void setValue(CornerValue value) {
        this.value = value;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double distance(Corner other){
        return position.distance(other.position);
    }

    @Override
    public int compareTo(Corner o) {
        return this.position.compareTo(o.position);
    }
}
