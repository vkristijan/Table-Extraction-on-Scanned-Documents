package hr.fer.zemris.zavrad;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class BackgroundEstimation implements Interpolator {
    private int dx;
    private int dy;

    public BackgroundEstimation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public GrayScaleImage interpolate(GrayScaleImage img1, GrayScaleImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()){
            throw new IllegalArgumentException("The given images do not match!");
        }

        int width = img1.getWidth();
        int height = img2.getHeight();

        GrayScaleImage result = new GrayScaleImage(img1.getWidth(), img1.getHeight());
        byte[][] resultData = result.getData();
        byte[][] data1 = img1.getData();
        byte[][] data2 = img2.getData();

        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                if (((int)data2[i][j] & 0xFF) == 255){
                    resultData[i][j] = data1[i][j];
                } else {
                    double a = 0;
                    double b = 0;

                    for (int x = i - dx; x <= i + dx; ++x){
                        for (int y = j - dy; y <= j + dy; ++y){
                            if (x < 0 || x >= height) continue;
                            if (y < 0 || y >= width) continue;

                            if (((int)data2[x][y] & 0xFF) == 255){
                                a += ((int)data1[x][y] & 0xFF);
                                b++;
                            }
                        }
                    }

                    double x = a/b;
                    if (x < 0) x = 0;
                    if (x > 255) x = 255;
                    resultData[i][j] = (byte)x;
                }
            }
        }

        return result;
    }
}
