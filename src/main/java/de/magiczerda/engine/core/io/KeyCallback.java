package de.magiczerda.engine.core.io;

import de.magiczerda.engine.core.controls.SpecialKeys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyCallback implements GLFWKeyCallbackI {

    private static boolean[] keyPressed;

    public KeyCallback() {
        final int NUMBER_OF_KEYS = GLFW.GLFW_KEY_LAST;

        keyPressed = new boolean[NUMBER_OF_KEYS];
        for(int ii = 0; ii < NUMBER_OF_KEYS; ii++) keyPressed[ii] = false;
    }


    /**
     * Will be called when a key is pressed, repeated or released.
     *
     * @param window   the window that received the event
     * @param key      the keyboard key that was pressed or released
     * @param scancode the platform-specific scancode of the key
     * @param action   the key action. One of:<br><table><tr><td>{@link GLFW#GLFW_PRESS PRESS}</td><td>{@link GLFW#GLFW_RELEASE RELEASE}</td><td>{@link GLFW#GLFW_REPEAT REPEAT}</td></tr></table>
     * @param mods     bitfield describing which modifiers keys were held down
     */
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if(window == DisplayManager.window) {
            if(action == GLFW.GLFW_PRESS)   keyPressed[key] = true;
            if(action == GLFW.GLFW_RELEASE) keyPressed[key] = false;


            SpecialKeys.involke(window, action, key);

        }
    }

    public static boolean isA() {
        return keyPressed[GLFW.GLFW_KEY_A];
    }

    public static boolean isB() {
        return keyPressed[GLFW.GLFW_KEY_B];
    }

    public static boolean isC() {
        return keyPressed[GLFW.GLFW_KEY_C];
    }

    public static boolean isD() {
        return keyPressed[GLFW.GLFW_KEY_D];
    }

    public static boolean isE() {
        return keyPressed[GLFW.GLFW_KEY_E];
    }

    public static boolean isF() {
        return keyPressed[GLFW.GLFW_KEY_F];
    }

    public static boolean isG() {
        return keyPressed[GLFW.GLFW_KEY_G];
    }

    public static boolean isH() {
        return keyPressed[GLFW.GLFW_KEY_H];
    }

    public static boolean isI() {
        return keyPressed[GLFW.GLFW_KEY_I];
    }

    public static boolean isJ() {
        return keyPressed[GLFW.GLFW_KEY_J];
    }

    public static boolean isK() {
        return keyPressed[GLFW.GLFW_KEY_K];
    }

    public static boolean isL() {
        return keyPressed[GLFW.GLFW_KEY_L];
    }

    public static boolean isM() {
        return keyPressed[GLFW.GLFW_KEY_M];
    }

    public static boolean isN() {
        return keyPressed[GLFW.GLFW_KEY_N];
    }

    public static boolean isO() {
        return keyPressed[GLFW.GLFW_KEY_O];
    }

    public static boolean isP() {
        return keyPressed[GLFW.GLFW_KEY_P];
    }

    public static boolean isQ() {
        return keyPressed[GLFW.GLFW_KEY_Q];
    }

    public static boolean isR() {
        return keyPressed[GLFW.GLFW_KEY_R];
    }

    public static boolean isS() {
        return keyPressed[GLFW.GLFW_KEY_S];
    }

    public static boolean isT() {
        return keyPressed[GLFW.GLFW_KEY_T];
    }

    public static boolean isU() {
        return keyPressed[GLFW.GLFW_KEY_U];
    }

    public static boolean isV() {
        return keyPressed[GLFW.GLFW_KEY_V];
    }

    public static boolean isW() {
        return keyPressed[GLFW.GLFW_KEY_W];
    }

    public static boolean isX() {
        return keyPressed[GLFW.GLFW_KEY_X];
    }

    public static boolean isY() {
        return keyPressed[GLFW.GLFW_KEY_Y];
    }

    public static boolean isZ() {
        return keyPressed[GLFW.GLFW_KEY_Z];
    }


    public static boolean isShift() {
        return keyPressed[GLFW.GLFW_KEY_LEFT_SHIFT] ||
                keyPressed[GLFW.GLFW_KEY_RIGHT_SHIFT];
    }

    public static boolean isSpace() { return keyPressed[GLFW.GLFW_KEY_SPACE]; }

    public static boolean isEscape() {return keyPressed[GLFW.GLFW_KEY_ESCAPE]; }

}
