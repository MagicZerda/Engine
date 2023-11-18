package de.magiczerda.engine.field;

import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;
import de.magiczerda.engine.testing.EField;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;

public class FieldRenderer extends Renderer {


    protected ScalarField field;
    protected Camera camera;

    public FieldRenderer(Camera camera, ScalarField field) {
        super(new ShaderProgram(new Shader(ShaderType.VERTEX, "field/fieldVertex.glsl"),
                new Shader(ShaderType.FRAGMENT, "field/fieldFragment.glsl")), GL11.GL_POINTS);

        GL11.glEnable(GL40.GL_PROGRAM_POINT_SIZE);

        this.field = field;
        //field = new EField();
        this.camera = camera;

        //field.getGameObject().setTranslation(15, 3, 5);

    }

    @Override
    protected void sendUniforms() {
        sendMatrices(field.getGameObject(), camera);
    }

    @Override
    protected void beforeRendering() {
        //setPointSize(3);
        field.getGameObject().calcTrafoMat();
    }

    public void render() {
        render(field.getGameObject(), false);
    }

    @Override
    protected void afterRendering() {
        //setPointSize(1);
    }

}
