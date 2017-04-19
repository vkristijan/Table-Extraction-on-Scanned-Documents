package hr.fer.zemris.zavrad.util;

import java.util.Random;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Rnd {
    private static Rnd instance = new Rnd();

    private Random rnd;

    private Rnd(){
        rnd = new Random();
    }

    public static double nextDouble(){
        return instance.rnd.nextDouble();
    }

    public static double nextDouble(double from, double to){
        return from + instance.rnd.nextDouble() * (to - from);
    }

    public static int nextInt(){
        return instance.rnd.nextInt();
    }

    public static int nextInt(int bound){
        return instance.rnd.nextInt(bound);
    }

    public static int nextInt(int from, int to){
        return from + instance.rnd.nextInt(to - from);
    }
}
