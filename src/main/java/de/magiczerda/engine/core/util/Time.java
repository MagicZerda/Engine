package de.magiczerda.engine.core.util;

public class Time {

    public static final long START_TIME = System.nanoTime();
    public static final float e9 = 1000000000;


    public static long NOW = START_TIME;

    public static int LAST_SEC = 0;
    public static int NOW_SEC = 0;

    public static float dt;


    public static void updateNow() {
        NOW = System.nanoTime();

        NOW_SEC = (int) ((NOW - START_TIME) / e9);

    }

    public static float timeRunning() {
        return (NOW - START_TIME) / e9;
    }

}
