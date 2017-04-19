package hr.fer.zemris.zavrad.skew.lines;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class UniformLineDistribution implements LineDistribution {
    @Override
    public List<Integer> getLines(int width, int n) {
        List<Integer> lines = new ArrayList<>();

        int distance = width / (n + 1);
        for (int i = 0; i < n; ++i){
            int x = (i + 1) * distance;
            lines.add(x);
        }

        return lines;
    }
}
