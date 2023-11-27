package de.magiczerda.engine.core.shader;

import org.joml.Vector3f;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class ComputeShader extends ShaderProgram {

    protected int workGroupX;
    protected int workGroupY;
    protected int workGroupZ;

    protected static int texture_width = 1024;
    protected static int texture_height = 1024;

    //protected SSBO[] ssbos;

    protected Map<Integer, Integer> ssbo = new HashMap<>();


    protected final int SSBO_IN = 0;
    protected final int SSBO_VERTEX_OUT = 1;
    protected final int SSBO_NORMAL_OUT = 2;
    protected final int SSBO_DEBUG = 3;

    protected int shaderBufferIn = -1;
    protected int shaderBufferOut = -1;
    protected int shaderBufferNorm = -1;
    protected int shaderBufferDebug = -1;

    protected int ssboOutSize;


    protected int textureID = -1;


    public ComputeShader(int workGroupX, int workGroupY, int workGroupZ, int ssboOutSize, String shaderSource) {
        super(new Shader(ShaderType.COMPUTE, shaderSource));
        this.workGroupX = workGroupX;
        this.workGroupY = workGroupY;
        this.workGroupZ = workGroupZ;

        texture_width = workGroupX;
        texture_height = workGroupY;

        ssbo.put(SSBO_IN, -1);
        ssbo.put(SSBO_VERTEX_OUT, -1);
        ssbo.put(SSBO_NORMAL_OUT, -1);
        ssbo.put(SSBO_DEBUG, -1);

        this.ssboOutSize =  ssboOutSize;

        init();
        createTexture();
    }


    public ComputeShader(Vector3f segSize, int ssboOutSize, String shaderSource) {
        super(new Shader(ShaderType.COMPUTE, shaderSource));
        this.workGroupX = (int) (segSize.x);
        this.workGroupY = (int) (segSize.y);
        this.workGroupZ = (int) (segSize.z);

        texture_width = workGroupX;
        texture_height = workGroupY;

        ssbo.put(SSBO_IN, -1);
        ssbo.put(SSBO_VERTEX_OUT, -1);
        ssbo.put(SSBO_NORMAL_OUT, -1);
        ssbo.put(SSBO_DEBUG, -1);

        this.ssboOutSize =  ssboOutSize;

        init();
        createTexture();
    }


    /*public ComputeShader(int workGroupX, int workGroupY, int workGroupZ, String shaderSource, SSBO... ssbos) {
        super(new Shader(ShaderType.COMPUTE, shaderSource));
        this.workGroupX = workGroupX;
        this.workGroupY = workGroupY;
        this.workGroupZ = workGroupZ;

        texture_width = workGroupX;
        texture_height = workGroupY;
        createTexture();

        this.ssbos = ssbos;
    }*/


    /*public ComputeShader(int workGroupX, int workGroupY, int workGroupZ, RCShader shader, int ssboOutSize) {
        super(shader);
        this.workGroupX = workGroupX;
        this.workGroupY = workGroupY;
        this.workGroupZ = workGroupZ;

        texture_width = workGroupX;
        texture_height = workGroupY;

        this.ssboOutSize = ssboOutSize;

        init();
        createTexture();
    }*/


    private void init() {
        ssbo.put(SSBO_IN, GL15.glGenBuffers());
        ssbo.put(SSBO_VERTEX_OUT, GL15.glGenBuffers());
        ssbo.put(SSBO_NORMAL_OUT, GL15.glGenBuffers());
        ssbo.put(SSBO_DEBUG, GL15.glGenBuffers());

        shaderBufferIn = ssbo.get(0);
        shaderBufferOut = ssbo.get(1);
        shaderBufferNorm = ssbo.get(2);
        shaderBufferDebug = ssbo.get(3);
        /*
        shaderBufferIn = GL15.glGenBuffers();
        shaderBufferOut = GL15.glGenBuffers();
        shaderBufferNorm = GL15.glGenBuffers();
        shaderBufferDebug = GL15.glGenBuffers();*/
    }


    protected void sendData(float[] data) {
        /*if(ssbos == null) return;

        for(SSBO ssbo : ssbos) {
            if(ssbo.isWriteOnly())
                ssbo.send(data);
            else ssbo.prepare();
        }*/

        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, shaderBufferIn);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, shaderBufferIn);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, 0);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_VERTEX_OUT, shaderBufferOut);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, (long) 4 * ssboOutSize, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_VERTEX_OUT, 0);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_NORMAL_OUT, shaderBufferNorm);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, (long) 4 * ssboOutSize, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_NORMAL_OUT, 0);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_DEBUG, shaderBufferDebug);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, (long) 4 * ssboOutSize, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_DEBUG, 0);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
    }



    private void createTexture() {
        textureID = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGBA32F,
                texture_width, texture_height, 0, GL11.GL_RGBA,
                GL11.GL_FLOAT, 0);

        GL42.glBindImageTexture(0, textureID, 0, false, 0, GL15.GL_READ_WRITE, GL30.GL_RGBA32F);
    }


    public void deploySSBO(boolean shaderOn) {
        if(!shaderOn) start();

        //int blockIndex = GL43.glGetProgramResourceIndex(programID, GL43.GL_SHADER_STORAGE_BLOCK, "bufferData");
        //GL43.glShaderStorageBlockBinding(programID, blockIndex, SSBO_BINDING);
        /*GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbos[0].ssboObject);
        if(ssbos != null)
            for(SSBO ssbo : ssbos) ssbo.bind();*/

        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, shaderBufferIn);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, shaderBufferIn);

        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, shaderBufferOut);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_VERTEX_OUT, shaderBufferOut);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_NORMAL_OUT, shaderBufferNorm);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_DEBUG, shaderBufferDebug);

        GL43.glDispatchCompute(workGroupX, workGroupY, workGroupZ);
        GL43.glMemoryBarrier(GL43.GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);

        /*if(ssbos != null)
            for(SSBO ssbo : ssbos) ssbo.unbind();*/


        //GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, 0);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_VERTEX_OUT, 0);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_NORMAL_OUT, 0);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_DEBUG, 0);


        GL30.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);


        //GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_OUT, 0);
        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);

        //GL43.glBindImageTexture(3, 0, 0, false, 0, GL15.GL_READ_WRITE, GL30.GL_RGBA32F);

        if(!shaderOn) stop();
    }

    public float[] getData(int binding) {
        //if(binding == 1) return v.getData();

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, ssbo.get(binding));

        float[] data = new float[ssboOutSize];
        GL30.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, data);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, 0);
        return data;
    }

    public void deployTexture(int binding, boolean shaderOn) {
        if(!shaderOn) start();
        GL42.glBindImageTexture(binding, textureID, 0, false, 0, GL15.GL_READ_WRITE, GL30.GL_RGBA32F);

        GL43.glDispatchCompute(workGroupX, workGroupY, workGroupZ);
        GL43.glMemoryBarrier(GL43.GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);

        GL43.glBindImageTexture(binding, 0, 0, false, 0, GL15.GL_READ_WRITE, GL30.GL_RGBA32F);

        if(!shaderOn) stop();
    }


    public int getTextureID() { return textureID; }


}
