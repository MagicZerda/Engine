package de.magiczerda.engine.core.rendering;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.math.Maths;
import de.magiczerda.engine.core.gameObjects.Model;
import de.magiczerda.engine.core.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;

public abstract class Renderer {
    protected ShaderProgram currentShader;
    protected int renderMode = GL11.GL_TRIANGLES;

    public static final Matrix4f identityMatrix = new Matrix4f().identity();


    public Renderer(ShaderProgram shader) {
        this.currentShader = shader;
    }

    public Renderer(ShaderProgram shader, int renderMode) {
        this.currentShader = shader;
        this.renderMode = renderMode;
    }


    public void setRenderMode(int renderMode) {
        this.renderMode = renderMode;
    }


    public void render(GameObject gameObject, boolean renderElements) {
        render(gameObject.getModel(), renderElements);
    }

    private void render(Model model, boolean renderElements) {
        start(model);

        sendUniforms();

        beforeRendering();
        if(renderElements) renderElements(model.getVertexCount());
        else               renderArrays(model.getVertexCount());
        afterRendering();


        finish(model);
    }

    /*public void render(Model model, boolean renderElements) {
        start(model);

        sendUniforms();

        beforeRendering();
        if(renderElements) renderElements(model.getVertexCount());
        else               renderArrays(model.getVertexCount());
        afterRendering();

        finish(model);
    }*/


    protected void setPointSize(int pointSize) {
        GL11.glPointSize(pointSize);
    }

    protected void setLineWidth(int lineWidth) {
        GL11.glLineWidth(lineWidth);
    }

    protected void start(Model model) {
        if(!model.isCullBack()) GL11.glDisable(GL11.GL_CULL_FACE);

        GL30.glBindVertexArray(model.getVaoID());
        currentShader.start();

        if(model.hasTexture()) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTextureID());
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
        }

    }


    /**
     * Overwrite this method in case you need to do something
     * before rendering
     */
    protected void beforeRendering() {}

    protected abstract void sendUniforms();

    /*protected void sendMatrices() {
        currentShader.loadMatrix("viewMatrix", Camera.getViewMatrix());
        currentShader.loadMatrix("transformationMatrix", identityMatrix);
        currentShader.loadMatrix("projectionMatrix", Maths.getProjectionMatrix());
    }*/

    protected void sendMatrices(GameObject gameObject, Camera camera) {
        currentShader.loadMatrix("viewMatrix", camera.getViewMatrix());
        currentShader.loadMatrix("transformationMatrix", gameObject.getTrafoMat());
        currentShader.loadMatrix("projectionMatrix", Maths.getProjectionMatrix());
    }

    protected void renderArrays(int vertexCount) {
        GL11.glDrawArrays(renderMode, 0, vertexCount);
    }

    protected void renderElements(int vertexCount) {
        GL15.glDrawElements(renderMode, vertexCount, GL11.GL_UNSIGNED_INT, 0);
    }


    /**
     * Overwrite this method in case you need to do something
     * after rendering/ cleaning up what you did before
     * rendering
     */
    protected void afterRendering() {}


    protected void finish(Model model) {
        if(model.hasTexture()) GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL33.glBindVertexArray(0);
        if(!model.isCullBack()) GL11.glEnable(GL11.GL_CULL_FACE);
    }

}
