package de.magiczerda.engine.core.shader;

public class Shader extends RCShader {


    public Shader(ShaderType shaderType, String sourcePath) {
        super(shaderType, sourcePath);
    }

    @Override
    protected String[] insertConstants() {
        return null;
    }
}
