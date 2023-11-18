package de.magiczerda.engine.core.main;

import de.magiczerda.engine.core.controls.SpecialKeys;
import de.magiczerda.engine.core.io.DisplayManager;
import de.magiczerda.engine.core.io.GameStateMGR;
import de.magiczerda.engine.core.scene.Scene;
import de.magiczerda.engine.core.util.Time;
import org.lwjgl.glfw.GLFW;

public class GameLoop {

    protected DisplayManager display = new DisplayManager();


    protected Scene currentScene;

    public GameLoop(Scene currentScene) {

        currentScene.init();

        long last_prnt = System.currentTimeMillis();
        long now_prnt = System.currentTimeMillis();
        int fps = 0;
        while(!GLFW.glfwWindowShouldClose(DisplayManager.getWindow())) {
            //System.out.println(Time.NOW_SEC);
            display.clear();

            currentScene.loop();
            fps++;

            display.swapBuffers();

            Time.updateNow();

            if(now_prnt >= last_prnt + 1000) {
                System.out.println("FPS: " + fps);

                last_prnt = now_prnt;
                fps = 0;
            } now_prnt = System.currentTimeMillis();

        }

        currentScene.terminate();
        display.terminate();
    }

}
