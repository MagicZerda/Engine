package de.magiczerda.engine.core.shader;

import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class ComputeShader extends ShaderProgram {

    protected int workGroupX;
    protected int workGroupY;
    protected int workGroupZ;

    protected static int texture_width = 1024;
    protected static int texture_height = 1024;


    protected final int SSBO_IN = 0;
    protected final int SSBO_OUT = 1;
    protected int shaderBufferIn = -1;
    protected int shaderBufferOut = -1;


    protected int textureID = -1;


    public ComputeShader(int workGroupX, int workGroupY, int workGroupZ, String shaderSource) {
        super(new Shader(ShaderType.COMPUTE, shaderSource));
        this.workGroupX = workGroupX;
        this.workGroupY = workGroupY;
        this.workGroupZ = workGroupZ;

        texture_width = workGroupX;
        texture_height = workGroupY;

        init();
        createTexture();
    }


    private void init() {
        shaderBufferIn = GL15.glGenBuffers();
        shaderBufferOut = GL15.glGenBuffers();
        //sendData(data);



/*
        int bufferOutIndex = GL43.glGetProgramResourceIndex(programID, GL43.GL_SHADER_STORAGE_BLOCK, "bufferOutData");
        GL43.glShaderStorageBlockBinding(programID, bufferOutIndex, SSBO_OUT);

        int bufferInIndex = GL43.glGetProgramResourceIndex(programID, GL43.GL_SHADER_STORAGE_BLOCK, "bufferInData");
        GL43.glShaderStorageBlockBinding(programID, bufferInIndex, SSBO_IN);
        System.err.println(bufferInIndex + " " + bufferOutIndex);*/
    }

    protected void sendData(float[] data) {
        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, shaderBufferIn);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, shaderBufferIn);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, 0);
        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
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

        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, shaderBufferIn);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, shaderBufferIn);
        //GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_OUT, shaderBufferOut);

        GL43.glDispatchCompute(workGroupX, workGroupY, workGroupZ);
        GL43.glMemoryBarrier(GL43.GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_IN, 0);
        GL30.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
        //GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_OUT, 0);
        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);

        //GL43.glBindImageTexture(3, 0, 0, false, 0, GL15.GL_READ_WRITE, GL30.GL_RGBA32F);

        if(!shaderOn) stop();
    }


    public void deployTexture(boolean shaderOn) {
        if(!shaderOn) start();
        GL42.glBindImageTexture(0, textureID, 0, false, 0, GL15.GL_READ_WRITE, GL30.GL_RGBA32F);

        GL43.glDispatchCompute(workGroupX, workGroupY, workGroupZ);
        GL43.glMemoryBarrier(GL43.GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);

        GL43.glBindImageTexture(0, 0, 0, false, 0, GL15.GL_READ_WRITE, GL30.GL_RGBA32F);

        if(!shaderOn) stop();
    }


    /*public static final int sizeX = 5;
    public static final int sizeY = 1;
    public static final int sizeZ = 5;
    //float[] data = new float[3 * (sizeX - 1) * (sizeY) * (sizeZ - 1)];*/
    FloatBuffer fb;
    public float[] getData(int numOfFloatsInTotal) {
        //fb = BufferUtils.createFloatBuffer(numOfFloatsInTotal);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_OUT, shaderBufferIn);   //bind ssbo
        //GL30.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, fb);

        float[] data = new float[numOfFloatsInTotal];
        GL30.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, data);

        //GL30.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, fb);

        //float[] data = new float[fb.capacity()];
        //for(int ii = 0; ii < fb.capacity(); ii++)
        //    data[ii] = fb.get(ii);
        //fb.clear();

        //data = new float[3*(16*2 + 2*4+1)];
        //GL30.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, data);
        /*int size = 3 * (sizeX - 1) * (sizeY) * (sizeZ - 1);
        this.data = new float[size];
        System.err.println(size);
        for(int ii = 0; ii < size; ii++)
            this.data[ii] = fb.get(ii);*/

        //return this.data;

        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, shaderBufferIn);

        //ssbo_retrieval_buffer = null;

       // ssbo_retrieval_buffer
        //ByteBuffer bb = GL30.glMapBuffer(GL43.GL_SHADER_STORAGE_BUFFER, GL30.GL_WRITE_ONLY); //retrieve

        //GL30.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, this.data);

        //System.err.println(bb != null);
        //if(bb == null) return new float[] {};

        //FloatBuffer fb = bb.asFloatBuffer();
        //float[] data = new float[fb.limit()];

        //fb.get(data);
        return data;
        //float[] data = new float[ssbo_retrieval_buffer.limit() / 4];
        //ssbo_retrieval_buffer.asFloatBuffer().get(data);
        //GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, SSBO_OUT, 0);      //unbind ssbo
        //GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
    }

    public int getTextureID() { return textureID; }


}
