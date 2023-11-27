package de.magiczerda.engine.vectorVis;

import de.magiczerda.engine.core.gameObjects.Model;
import de.magiczerda.engine.core.loading.Loader;
import de.magiczerda.engine.core.math.Maths;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.RCShader;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class VectorRenderer extends Renderer {

    private static Model pointModel;// = Loader.loadModel(new float[] {0, 0, 0}, true);
    public static List<Vector> vectors = new ArrayList<>();


    protected Camera camera;

    public VectorRenderer(Camera camera) {
        super(new ShaderProgram(new Shader(ShaderType.VERTEX, "vectorVis/vectorVertex.glsl"),
                                new Shader(ShaderType.GEOMETRY, "vectorVis/vectorGeometry.glsl"),
                                new Shader(ShaderType.FRAGMENT, "vectorVis/vectorFragment.glsl")),
                GL11.GL_POINTS);

        pointModel = Loader.loadModel(new float[] {0, 0, 0}, true);

        this.camera = camera;

    }

    /*public static void addVector(float ox, float oy, float oz, float dx, float dy, float dz) {
        vectors.add(VectorLoader.loadVector(new float[]{0, 1, 0}, new float[]{1, -1, 0}, true));
    }*/

    @Override
    protected void sendUniforms() {}


    public void render() {
        if(vectors.isEmpty()) return;

        start(pointModel);
        setLineWidth(2);

        currentShader.loadMatrix("viewMatrix", camera.getViewMatrix());
        currentShader.loadMatrix("projectionMatrix", Maths.getProjectionMatrix());

        for(Vector vector : vectors) {
            currentShader.loadVector3f("vectorOrigin", vector.getOrigin());
            currentShader.loadVector3f("vectorDirection", vector.getDirection());

            renderArrays(1);
        }

        setLineWidth(1);
        finish(pointModel);
    }

    @Override
    protected void afterRendering() {
        setLineWidth(1);
    }
}
