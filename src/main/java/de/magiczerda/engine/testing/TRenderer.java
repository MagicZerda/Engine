package de.magiczerda.engine.testing;

import de.magiczerda.engine.core.io.KeyCallback;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;

import java.awt.event.KeyListener;

public class TRenderer extends Renderer {

    protected Camera camera;
    protected GameObject gameObject;

    public TRenderer(Camera camera, GameObject gameObject) {
        super(new ShaderProgram(new Shader(ShaderType.VERTEX, "testing/tVertex.glsl"),
                                new Shader(ShaderType.FRAGMENT, "testing/tFragment.glsl")));

        this.camera = camera;
        this.gameObject = gameObject;
    }

    @Override
    protected void afterRendering() {
        if(KeyCallback.isU()) gameObject.addTranslation(0, 0, 5);
    }

    @Override
    protected void sendUniforms() {
        sendMatrices(gameObject, camera);
    }

    public ShaderProgram getShader() {
        return currentShader;
    }

}
