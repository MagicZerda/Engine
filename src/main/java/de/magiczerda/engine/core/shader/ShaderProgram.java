package de.magiczerda.engine.core.shader;

import de.magiczerda.engine.core.loading.Loader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgram {

    /**
     * The ID for this shader type
     * -> All that OpenGL will need to use this shader.
     */
    protected int programID;

    protected Shader[] shaders;

    /**
     * Used to optimize forwarding matrices to the shaders
     */
    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    /**
     * Uniform locations (shader variables) will be put
     * in this map automatically once they're involked.
     */
    protected Map<String, Integer> uniformLocations = new HashMap<>();

    /**
     * Creates a shader stage by linking the specified shaders.
     *
     * @param shaders The shaders to link
     */
    public ShaderProgram(Shader... shaders) {
        this.shaders = shaders;

        programID = GL20.glCreateProgram();
        for(Shader ss : shaders)
            GL20.glAttachShader(programID, ss.shaderID);

        GL20.glLinkProgram(programID);
        String linkInfo = GL20.glGetProgramInfoLog(programID);

        if(!linkInfo.isEmpty())
            System.err.println("Error linking shaders: \n" + linkInfo);

        start();

        for(Shader ss : shaders)
            GL20.glDeleteShader(ss.shaderID);
    }

    /**
     * Instructs OpenGL to start using this shader
     * (important line in your render code)
     */

    public void start() {
        GL20.glUseProgram(programID);
    }

    /**
     * Instructs OpenGL to stop using this shader
     */

    public void stop() {
        GL20.glUseProgram(0);
    }


    public void bindAttribute(int attributeID, String varName) {
        GL20.glBindAttribLocation(programID, attributeID, varName);
        GL20.glEnableVertexAttribArray(attributeID);
    }



    /**
     * Sends a float variable to the
     * specified uniform variable
     *
     * @param name The name of the uniform variable
     * @param value The value to send to it
     */

    public void loadFloat(String name, float value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        GL20.glUniform1f(uniformLocations.get(name), value);
    }

    public void loadInt(String name, int value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        GL20.glUniform1i(uniformLocations.get(name), value);
    }



    public void loadFloatArray(String name, float[] value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        GL20.glUniform1fv(uniformLocations.get(name), Loader.floatArrToBuffer(value));
    }



    /**
     * Sends a 2D- vector to the
     * specified uniform variable
     *
     * @param name The name of the uniform variable
     * @param value The value to send to it
     */

    public void loadVector2f(String name, Vector2f value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        GL20.glUniform2f(uniformLocations.get(name), value.x, value.y);

    }



    /**
     * Sends a 3D- vector to the
     * specified uniform variable
     *
     * @param name The name of the uniform variable
     * @param value The value to send to it
     */

    public void loadVector3f(String name, Vector3f value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        GL20.glUniform3f(uniformLocations.get(name), value.x, value.y, value.z);

    }

    /**
     * Sends a 4D- vector to the
     * specified uniform variable
     *
     * @param name The name of the unViform variable
     * @param value The value to send to it
     */

    public void loadVector4f(String name, Vector4f value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        GL20.glUniform4f(uniformLocations.get(name), value.x, value.y, value.z, value.w);
    }

    /**
     * Sends a boolean variable (1 or 0) to the
     * specified uniform variable
     *
     * @param name The name of the uniform variable
     * @param value The value to send to it
     */

    public void loadBoolean(String name, boolean value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        GL20.glUniform1f(uniformLocations.get(name), value?1:0);
    }

    /**
     * Sends a 4x4 matrix to the
     * specified uniform variable
     *
     * @param name The name of the uniform variable
     * @param value The value to send to it
     */

    public void loadMatrix(String name, Matrix4f value) {
        if(uniformLocations.get(name) == null || uniformLocations.get(name) == -1)
            addUniformLocation(name);

        value.get(matrixBuffer);
        GL20.glUniformMatrix4fv(uniformLocations.get(name), false, matrixBuffer);
        //matrixBuffer.clear();
    }


    private void addUniformLocation(String uniformName) {
        uniformLocations.put(uniformName, GL20.glGetUniformLocation(programID, uniformName));
    }

    /**
     * Cleanup call
     * Quite important as well because we need to
     * do all the memory management ourselves
     */

    public void cleanUp() {
        for(Shader ss : shaders) {
            GL20.glDetachShader(programID, ss.shaderID);
            GL20.glDeleteShader(ss.shaderID);
        }
        stop();

        GL20.glDeleteProgram(programID);
    }

}
