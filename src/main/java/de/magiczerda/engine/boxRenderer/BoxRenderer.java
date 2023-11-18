package de.magiczerda.engine.boxRenderer;

import de.magiczerda.engine.core.io.KeyCallback;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;
import org.lwjgl.opengl.GL11;

public class BoxRenderer extends Renderer {


    public static Box box;
    protected Camera camera;

    public BoxRenderer(Camera camera) {
        super(new ShaderProgram(
                new Shader(ShaderType.VERTEX, "boxRenderer/boxVertex.glsl"),
                new Shader(ShaderType.FRAGMENT, "boxRenderer/boxFragment.glsl")));

        setRenderMode(GL11.GL_LINES);
        setLineWidth(10);

        box = new Box();
        this.camera = camera;
    }

    public void render() {
        super.render(box, true);
    }

    @Override
    protected void sendUniforms() {
        box.calcTrafoMat();
        sendMatrices(box, camera);
    }

}
