package de.magiczerda.engine.marchingCubes;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.loading.Loader;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;
import de.magiczerda.engine.field.ScalarField;
import de.magiczerda.engine.testing.MCField;

public class MarchingCubesRenderer extends Renderer {

    protected MarchingCubes marchingCubes;
    protected Camera camera;

    public MarchingCubesRenderer(Camera camera) {
        super(new ShaderProgram(
                new Shader(ShaderType.VERTEX, "marchingCubes/marchingCubesVertex.glsl"),
                new Shader(ShaderType.FRAGMENT, "marchingCubes/marchingCubesFragment.glsl")));

        this.marchingCubes = new MarchingCubes(new MCField());
        //setRenderMode(GL11.GL_POINTS);

        this.camera = camera;
    }

    public void render() {
        setPointSize(30);

        super.render(marchingCubes.getGameObject(), false);
        marchingCubes.update();
        setPointSize(1);

        marchingCubes.updateGameObject(marchingCubes.getVertices(), marchingCubes.getNormals());
    }

    @Override
    protected void sendUniforms() {
        sendMatrices(marchingCubes.getGameObject(), camera);
    }

    public MarchingCubesField getField() { return marchingCubes.marchingCubesField; }

}
