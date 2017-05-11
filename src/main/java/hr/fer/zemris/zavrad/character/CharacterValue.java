package hr.fer.zemris.zavrad.character;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public enum CharacterValue {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    CROSS(6),
    BLANK(7);

    private int value;
    private static Map<Integer, CharacterValue> map;

    CharacterValue(int value) {
        this.value = value;
    }

    static {
        map = new HashMap<>();
        for (CharacterValue character : CharacterValue.values()){
            map.put(character.value, character);
        }
    }

    public int getValue(){
        return value;
    }

    public static CharacterValue getCharacterValue(int value){
        return map.get(value);
    }
}
