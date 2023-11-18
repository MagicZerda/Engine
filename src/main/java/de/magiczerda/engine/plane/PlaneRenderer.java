package de.magiczerda.engine.plane;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;

public class PlaneRenderer extends Renderer {

    //Plane plane;
    Terrain terrain;
    GameObject planeGO;

    Camera camera;

    public PlaneRenderer(Camera camera) {
        super(new ShaderProgram(new Shader(ShaderType.VERTEX, "plane/planeVertex.glsl"),
                new Shader(ShaderType.FRAGMENT, "plane/planeFragment.glsl")));

        //this.plane = new Plane();

        this.camera = camera;

        terrain = new Terrain();
        this.planeGO = terrain.plane.planeGameObject;
    }

    @Override
    protected void sendUniforms() {
        sendMatrices(planeGO, camera);
    }

    @Override
    protected void beforeRendering() {
        setPointSize(10);
    }

    public void render() {
        super.render(planeGO, true);
    }

    @Override
    protected void afterRendering() {
        setPointSize(1);
    }
}
