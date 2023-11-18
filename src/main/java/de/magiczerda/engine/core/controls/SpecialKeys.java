package de.magiczerda.engine.core.controls;

import de.magiczerda.engine.boxRenderer.BoxRenderer;
import de.magiczerda.engine.core.io.DisplayManager;
import de.magiczerda.engine.core.io.GameState;
import de.magiczerda.engine.core.io.GameStateMGR;
import de.magiczerda.engine.core.util.Settings;
import de.magiczerda.engine.testing.EField;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class SpecialKeys {

    public static boolean allowResume = true;

    public static Runnable toRunOnJ = null;

    public static int M_COUNT = 0;

    public static void involke(long window, int action, int key) {
        if(action == GLFW.GLFW_PRESS) {
            if(key == GLFW.GLFW_KEY_TAB) GLFW.glfwSetWindowShouldClose(window, true);

            if(key == GLFW.GLFW_KEY_J) DisplayManager.setWireframe(true);
            if(key == GLFW.GLFW_KEY_Q) FirstPerson.SPEED_MULTIPLIER = Settings.SPEED_MULTIPLIER_ACTIVATION;

            if(key == GLFW.GLFW_KEY_C) GL11.glDisable(GL11.GL_CULL_FACE);

            if(key == GLFW.GLFW_KEY_ESCAPE) {
                //if(!allowResume) { GameStateMGR.currentGameState = GameState.PLAYING; return; }
                if(GameStateMGR.getCurrentGameState() == GameState.MENU && allowResume)
                    GameStateMGR.setCurrentGameState(GameState.PLAYING);
                else {
                    GameStateMGR.setCurrentGameState(GameState.MENU);
                    allowResume = false;
                }
            }
        }

        if(action == GLFW.GLFW_RELEASE) {


            if(key == GLFW.GLFW_KEY_J) DisplayManager.setWireframe(false);
            if(key == GLFW.GLFW_KEY_Q) FirstPerson.SPEED_MULTIPLIER = 1;

            if(key == GLFW.GLFW_KEY_C) GL11.glEnable(GL11.GL_CULL_FACE);

            if(key == GLFW.GLFW_KEY_ESCAPE) {
                if(!allowResume) { allowResume = true; return; }
                else GameStateMGR.setCurrentGameState(GameState.PLAYING);
            }

            if(key == GLFW.GLFW_KEY_M) {
                M_COUNT++;
                EField.move();
            }

            if(toRunOnJ == null) return;
            if(key == GLFW.GLFW_KEY_J) toRunOnJ.run();
        }
    }

}
