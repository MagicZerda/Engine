package de.magiczerda.engine.core.util;

import org.joml.Vector4f;

public class Settings {

    public static final String DISPLAY_TITLE = "LWJGL Engine";
    public static final int INITIAL_WIDTH  = 1080;
    public static final int INITIAL_HEIGHT = 720;

    public static int CURRENT_WIDTH  = Settings.INITIAL_WIDTH;
    public static int CURRENT_HEIGHT = Settings.INITIAL_HEIGHT;


    public static float SPEED_MULTIPLIER_ACTIVATION = 20f;


    //The following constant is for the shaders
    public static final String MAIN_PACKAGE_STRUCTURE = "src/main/java/de/magiczerda/engine/";


    public static Vector4f clearColor = new Vector4f(0, 0, 1, 0);
    public static boolean VSYNC = false;
    public static int TARGET_FPS = 10;


    public static float FOV = 70;
    public static float NEAR_PLANE = 0.01f;
    public static float FAR_PLANE = 10000f;

}
