package hr.fer.zemris.zavrad.table;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Table {
    private GrayScaleImage image;
    private List<List<Corner>> corners;

    public Table(GrayScaleImage image, List<List<Corner>> corners) {
        this.image = image;
        this.corners = corners;
    }

    public Table(Corner[] cornerList, GrayScaleImage image){
        //TODO some fancy stuff
        this.image = image;
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
