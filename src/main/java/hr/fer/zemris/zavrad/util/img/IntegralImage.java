package hr.fer.zemris.zavrad.util.img;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class IntegralImage extends GrayScaleImage{
    private int[][] integral;

    public IntegralImage(int width, int height) {
        super(width, height);

        calculateIntegral();
    }

    public static IntegralImage fromGrayscaleImage(GrayScaleImage image){
        IntegralImage integralImage = new IntegralImage(image.width, image.height);
        integralImage.data = image.data;

        integralImage.calculateIntegral();
        return integralImage;
    }

    private void calculateIntegral() {
        int width = getWidth();
        int height = getHeight();

        byte[][] data = getData();

        integral = new int[height][width];

        if (data[0][0] == GrayScaleImage.BLACK){
            integral[0][0] = 1;
        } else {
            integral[0][0] = 0;
        }

        for (int j = 1; j < width; ++j){
            integral[0][j] = integral[0][j - 1];
            if (data[0][j] == GrayScaleImage.BLACK){
                integral[0][j]++;
            }
        }

        for (int i = 1; i < height; ++i){
            integral[i][0] = integral[i - 1][0];
            if (data[i][0] == GrayScaleImage.BLACK){
                integral[i][0]++;
            }

            for (int j = 1; j < width; ++j){
                integral[i][j] = integral[i - 1][j];
                integral[i][j] += integral[i][j - 1];
                integral[i][j] -= integral[i - 1][j - 1];

                if (data[i][j] == GrayScaleImage.BLACK){
                    integral[i][j]++;
                }
            }
        }
    }

    public int getSum(int w, int h){
        return integral[h][w];
    }

    public int getSum(int wFrom, int hFrom, int wTo, int hTo){
        int sum = integral[hTo][wTo];

        if (wFrom != 0){
            sum -= integral[hFrom][wTo - 1];
        }

        if (hFrom != 0){
            sum -= integral[hFrom - 1][wTo];
        }

        if (wFrom != 0 && hFrom != 0){
            sum += integral[hFrom - 1][wFrom - 1];
        }

        return sum;
    }
}
