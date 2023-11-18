package de.magiczerda.engine.core.io;

import de.magiczerda.engine.core.util.CleanUp;
import de.magiczerda.engine.core.util.Settings;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

public class DisplayManager {


    // The window handle
    protected static long window = -1;
    protected static KeyCallback keyCallback;
    protected static CursorCallback cursorCallback;


    public DisplayManager() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        setupCallbacks();

        finishInitialization();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !GLFW.glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // the window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(Settings.INITIAL_WIDTH, Settings.INITIAL_HEIGHT, Settings.DISPLAY_TITLE, 0, 0);
        if (window == -1)
            throw new RuntimeException("Failed to create the GLFW window");


        // Get the thread stack and push a new frame
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            // Center the window
            GLFW.glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
    }


    private void setupCallbacks() {
        keyCallback = new KeyCallback();
        CleanUp.callbacks.add(GLFW.glfwSetKeyCallback(window, keyCallback));

        cursorCallback = new CursorCallback();
        CleanUp.callbacks.add(GLFW.glfwSetCursorPosCallback(window, cursorCallback));
    }

    private void finishInitialization() {

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);

        if(Settings.VSYNC) // Enable v-sync
            GLFW.glfwSwapInterval(1);

        // Make the window visible
        GLFW.glfwShowWindow(window);


        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        GL11.glClearColor(Settings.clearColor.x, Settings.clearColor.y, Settings.clearColor.z, Settings.clearColor.w);

        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void setWireframe(boolean wireframe) {
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, wireframe ? GL11.GL_LINE : GL11.GL_FILL);
    }

    public static
    void setCursorVisibility(boolean cursorVisible) {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, cursorVisible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
    }


    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window); // swap the color buffers
        GLFW.glfwPollEvents();
    }

    public void terminate() {
        // Free the window callbacks and destroy the window
        CleanUp.freeCallbacks();

        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public static long getWindow() {
        return window;
    }

}
