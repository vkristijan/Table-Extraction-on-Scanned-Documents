package hr.fer.zemris.zavrad.skew.lines;

import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface LineDistribution {
    /**
     * Returns a list of integer coordinates where a vertical line should be made.
     *
     * @param width the width of the image.
     * @param n the number of liens wanted.
     * @return a list of integers of the coordinates.
     */
    List<Integer> getLines(int width, int n);
}
