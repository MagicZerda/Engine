package de.magiczerda.engine.field;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.loading.Loader;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.RCShader;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;
import de.magiczerda.engine.marchingCubes.MarchingCubesField;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;

public class FieldRenderer extends Renderer {


    protected MarchingCubesField field;
    protected Camera camera;

    protected GameObject fGO;

    public FieldRenderer(Camera camera, MarchingCubesField field) {
        super(new ShaderProgram(new Shader(ShaderType.VERTEX, "field/fieldVertex.glsl"),
                new Shader(ShaderType.FRAGMENT, "field/fieldFragment.glsl")), GL11.GL_POINTS);

        GL11.glEnable(GL40.GL_PROGRAM_POINT_SIZE);

        this.field = field;
        //field = new EField();
        this.camera = camera;

        float[] fVertices = new float[3000 * (int) (field.getSegSize().y * field.getSegSize().z * field.getSegSize().x)];
        int index = 0;

        for(int ssy = 0; ssy < field.getSegSize().y; ssy++) {
            for(int ssz = 0; ssz < field.getSegSize().z; ssz++) {
                for(int ssx = 0; ssx < field.getSegSize().x; ssx++) {

                    for(int yy = 0; yy < 10; yy++) {
                        for(int zz = 0; zz < 10; zz++) {
                            for(int xx = 0; xx < 10; xx++) {

                                fVertices[3*index]   = ssx*10 + xx;
                                fVertices[3*index+1] = ssy*10 + yy;
                                fVertices[3*index+2] = ssz*10 + zz;

                                index++;
                            }
                        }
                    }

                }
            }
        }

        fGO = new GameObject(Loader.loadModel(fVertices, false));
        //field.getGameObject().setTranslation(15, 3, 5);

    }

    @Override
    protected void sendUniforms() {
        sendMatrices(fGO, camera);
    }

    @Override
    protected void beforeRendering() {
        //setPointSize(3);
        fGO.calcTrafoMat();
    }

    public void render() {
        render(fGO, false);
    }

    @Override
    protected void afterRendering() {
        //setPointSize(1);
    }

}
