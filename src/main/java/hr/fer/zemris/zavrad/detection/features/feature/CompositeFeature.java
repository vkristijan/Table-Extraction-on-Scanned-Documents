package hr.fer.zemris.zavrad.detection.features.feature;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CompositeFeature implements IFeature {
    private List<IFeature> features;

    public CompositeFeature(){
        features = new ArrayList<>();
    }

    public CompositeFeature(List<IFeature> features){
        this.features = features;
    }

    @Override
    public int getFeature(byte[][] data, int x, int y, int w, int h, double[] features, int index) {
        int count = 0;

        for (IFeature feature : this.features) {
            int tmpCount = feature.getFeature(data, x, y, w, h, features, index);
            count += tmpCount;
            index += tmpCount;
        }

        return count;
    }

    public void addFeature(IFeature feature){
        this.features.add(feature);
    }

    public void removeFeature(IFeature feature){
        this.features.remove(feature);
    }
}
