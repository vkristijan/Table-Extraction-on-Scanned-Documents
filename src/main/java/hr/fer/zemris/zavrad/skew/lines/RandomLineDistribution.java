package hr.fer.zemris.zavrad.skew.lines;

import hr.fer.zemris.zavrad.util.Rnd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class RandomLineDistribution implements LineDistribution {
    @Override
    public List<Integer> getLines(int width, int n) {
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < n; ++i){
            int x = Rnd.nextInt((int)(0.1 * width), (int)(0.9 * width));
            lines.add(x);
        }

        lines.sort(Comparator.naturalOrder());
        return lines;
    }
}
