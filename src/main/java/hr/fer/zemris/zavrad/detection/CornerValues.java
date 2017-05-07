package hr.fer.zemris.zavrad.detection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public enum CornerValues {
    UL_CORNER(0),
    TOP(1),
    UR_CORNER(2),
    LEFT(3),
    CENTER(4),
    RIGHT(5),
    DL_CORNER(6),
    BOTTOM(7),
    DR_CORNER(8),
    NONE(9);

    private int value;
    private static Map<Integer, CornerValues> map;

    CornerValues(int value) {
        this.value = value;
    }

    static {
        map = new HashMap<>();
        for (CornerValues corner : CornerValues.values()){
            map.put(corner.value, corner);
        }
    }

    public int getValue(){
        return value;
    }

    public static CornerValues getCornerValue(int value){
        return map.get(value);
    }
}
