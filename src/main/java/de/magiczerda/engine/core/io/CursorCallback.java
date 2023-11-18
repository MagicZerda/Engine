package de.magiczerda.engine.core.io;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public class CursorCallback implements GLFWCursorPosCallbackI {

    public static Vector2f cursorPosition = new Vector2f();
    private boolean firstFrameAfterMenu = false;


    /**
     * Will be called when the cursor is moved.
     *
     * <p>The callback function receives the cursor position, measured in screen coordinates but relative to the top-left corner of the window client area. On
     * platforms that provide it, the full sub-pixel cursor position is passed on.</p>
     *
     * @param window the window that received the event
     * @param xpos   the new cursor x-coordinate, relative to the left edge of the content area
     * @param ypos   the new cursor y-coordinate, relative to the top edge of the content area
     */
    @Override
    public void invoke(long window, double xpos, double ypos) {
        if(GameStateMGR.getCurrentGameState() == GameState.PLAYING) {
            if(firstFrameAfterMenu) {
                firstFrameAfterMenu = false;
                GLFW.glfwSetCursorPos(window, cursorPosition.x, cursorPosition.y);
                return;
            }

            cursorPosition.set(xpos, ypos);
        }

        else if(GameStateMGR.getCurrentGameState() == GameState.MENU)
            firstFrameAfterMenu = true;
    }

}
