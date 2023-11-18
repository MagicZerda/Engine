package de.magiczerda.engine.core.gui;

import de.magiczerda.engine.core.loading.TextureLoader;
import de.magiczerda.engine.core.rendering.Renderer;
import de.magiczerda.engine.core.shader.Shader;
import de.magiczerda.engine.core.shader.ShaderProgram;
import de.magiczerda.engine.core.shader.ShaderType;

public class GUIRenderer extends Renderer {

    protected GUI gui;

    public GUIRenderer() {
        super(new ShaderProgram(new Shader(ShaderType.VERTEX, "core/gui/guiVertex.glsl"),
                new Shader(ShaderType.FRAGMENT, "core/gui/guiFragment.glsl")));


        this.gui = new GUI();
        this.gui.setScale(0.5f, 0.5f, 1);

        int texture = TextureLoader.loadTexture(TextureLoader.loadImage("src/main/resources/x.jpg"));
        this.gui.getModel().setTextureID(texture);
    }

    @Override
    protected void sendUniforms() {
        gui.calcTrafoMat();
        currentShader.loadMatrix("transformationMatrix", gui.getTrafoMat());
    }


    public void render() {
        super.render(gui, true);
    }

    public GUI getGui() { return gui; }

}
