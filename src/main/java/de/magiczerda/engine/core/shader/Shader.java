package de.magiczerda.engine.core.shader;

import de.magiczerda.engine.core.util.Settings;
import org.lwjgl.opengl.*;

import java.io.*;

public class Shader {

    protected int shaderID = -1;

    public Shader(ShaderType shaderType, String sourcePath) {
        compile(sourcePath, shaderType);
    }



    /**
     * Creates a new shader (shaderID),
     * reads in all lines from the file
     * specified by the "sourcePath"
     * argument
     * @param sourcePath The path to the file that's to be compiled
     * @param shaderType The type of shader to create (vertex, geometry, fragment, compute)
     */

    public void compile(String sourcePath, ShaderType shaderType) {
        try {
            String source = "";
            BufferedReader br = new BufferedReader(
                    new FileReader(new File(Settings.MAIN_PACKAGE_STRUCTURE + sourcePath)));

            String currentLine;
            while((currentLine =br.readLine()) != null)
                source += currentLine+"\n";

            switch(shaderType) {
                case VERTEX -> shaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
                //case TESSELLATION_CONTROL -> shaderID = GL20.glCreateShader(GL40.GL_TESS_CONTROL_SHADER);
                //case TESSELLATION_EVALUATION -> shaderID = GL20.glCreateShader(GL40.GL_TESS_EVALUATION_SHADER);
                case GEOMETRY -> shaderID = GL20.glCreateShader(GL32.GL_GEOMETRY_SHADER);
                case FRAGMENT -> shaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
                case COMPUTE -> shaderID = GL20.glCreateShader(GL43.GL_COMPUTE_SHADER);
                default -> System.err.println("Unknown Shader Type " + shaderType.name() +
                        " please include special handling instructions inside Shader.java");
            }

            GL33.glShaderSource(shaderID, source);
            GL33.glCompileShader(shaderID);

            String compile_info = GL30.glGetShaderInfoLog(shaderID);
            if(!compile_info.isEmpty()) System.out.println(compile_info);

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
