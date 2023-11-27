package de.magiczerda.engine.field;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.loading.Loader;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.RCShader;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;
import org.lwjgl.opengl.GL11;

public class PointRenderer extends Renderer {

    protected static float[] vertices = new float[0];
    protected static GameObject object = new GameObject(Loader.loadModel(new float[] {}, false));

    protected Camera camera;

    public PointRenderer(Camera camera) {
        super(new ShaderProgram(
                new Shader(ShaderType.VERTEX, "field/pointVertex.glsl"),
                new Shader(ShaderType.FRAGMENT, "field/pointFragment.glsl")),
                GL11.GL_POINTS);

        setPointSize(30);
        //object = new GameObject(Loader.loadModel(new float[] {-2,-2,-2, 0}, false));
        this.camera = camera;
    }

    public static void updateVertices(float[] vertices) {
        PointRenderer.vertices = vertices;
        Loader.updateModel(PointRenderer.object.getModel(), 3, PointRenderer.vertices);
    }

    public static void addVertices(float[] vertices) {
        if (PointRenderer.vertices.length == 0) {
            PointRenderer.vertices = vertices;
            return;
        }

        float[] oldVertices = PointRenderer.vertices;
        PointRenderer.vertices = new float[oldVertices.length + vertices.length];

        int index = 0;
        for(float oo : oldVertices) { PointRenderer.vertices[index] = oo; index++; }
        for(float nn : vertices) { PointRenderer.vertices[index] = nn; index++; }

        updateVertices(vertices);
    }


    public void render() {
        setPointSize(10);
        super.render(object, false);
    }

    @Override
    protected void sendUniforms() {
        sendMatrices(object, camera);
    }
}
