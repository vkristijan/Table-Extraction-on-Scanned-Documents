package hr.fer.zemris.zavrad.filters.threshold;

import hr.fer.zemris.zavrad.GrayScaleImage;
import hr.fer.zemris.zavrad.filters.misc.BackgroundEstimation;
import hr.fer.zemris.zavrad.filters.misc.ShrinkFilter;
import hr.fer.zemris.zavrad.filters.misc.Swell;
import hr.fer.zemris.zavrad.filters.*;
import hr.fer.zemris.zavrad.filters.misc.WienerFilter;

/**
 *
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Binarization implements ImageFilter {
    @Override
    public GrayScaleImage filter(GrayScaleImage ... image) {
        if (image.length != 1){
            throw new ImageFilterException("Binarization filter expects 1 argument!");
        }
        ImageFilter wiener = new WienerFilter(5, 5);
        GrayScaleImage wienerResult = wiener.filter(image);

        ImageFilter niblack = new NiblackThreshold(20, 20, -0.2);
        GrayScaleImage niblackResult = niblack.filter(image);

        ImageFilter interpolator = new BackgroundEstimation(3, 3);
        GrayScaleImage background = interpolator.filter(wienerResult, niblackResult);

        ImageFilter finalThreshold = new AdaptiveThreshold(0.8);
        GrayScaleImage threshold = finalThreshold.filter(background, wienerResult, niblackResult);

        int n = 5;
        ImageFilter shrink = new ShrinkFilter(n, 0.8);
        GrayScaleImage shrinked = shrink.filter(threshold);

        ImageFilter swell1 = new Swell(n, 0.1);
        GrayScaleImage swelled = swell1.filter(shrinked);

        return swelled;
    }
}
