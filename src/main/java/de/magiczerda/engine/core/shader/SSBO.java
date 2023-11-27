package de.magiczerda.engine.core.shader;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;

public class SSBO {
    protected int binding;
    protected int ssboObject = -1;

    protected int bufferSize;

    protected boolean writeOnly = false;

    public SSBO(int binding, int bufferSize) {
        this.binding = binding;
        this.ssboObject = GL15.glGenBuffers();

        this.bufferSize = bufferSize;
    }

    public SSBO(int binding, int bufferSize, boolean writeOnly) {
        this.binding = binding;
        this.ssboObject = GL15.glGenBuffers();

        this.writeOnly = writeOnly;

        this.bufferSize = bufferSize;
    }


    public void send(float[] data) {
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssboObject);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, ssboObject);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, 0);
    }

    public void prepare() {
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, ssboObject);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, (long) bufferSize, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, 0);
        //L15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
    }


    public float[] getData() {
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, ssboObject);

        float[] data = new float[bufferSize];
        GL30.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, data);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, 0);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
        return data;
    }


    public void bind() {
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, ssboObject);
    }

    public void unbind() {
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, binding, 0);
    }


    public boolean isWriteOnly() { return writeOnly; }

}
