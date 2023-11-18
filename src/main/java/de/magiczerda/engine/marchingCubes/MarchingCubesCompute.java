package de.magiczerda.engine.marchingCubes;

import de.magiczerda.engine.core.shader.ComputeShader;
import de.magiczerda.engine.field.ScalarField;

public class MarchingCubesCompute extends ComputeShader {

    float r = 0, g = 0, b = 0;

    public MarchingCubesCompute(int cubesX, int cubesY, int cubesZ) {
        super(cubesX, cubesY, cubesZ, "marchingCubes/marchingCubesCompute.glsl");

        //sendData(new float[] {1,1,1});
        start();

        stop();
    }

    public void loadUniforms(int sizeX, int sizeY, int sizeZ, float iso_value, boolean shaderRunning) {
        if(!shaderRunning) start();
        loadInt("sizeX", sizeX);
        loadInt("sizeY", sizeY);
        loadInt("sizeZ", sizeZ);
        loadFloat("iso_value", iso_value);

        loadInt("t", 0);
        if(!shaderRunning) stop();
    }

    protected int phase = 0;
    public void update(ScalarField field) {
        /*final float dc = 0.01f;

        if(r <= 0 && g <= 0 && b <= 0) phase = 0;
        else {
            if(r >= 1 && g == 0 && b == 0) phase = 1;
            if(r >= 1 && g >= 1 && b == 0) phase = 2;
            if(r >= 1 && g >= 1 && b >= 1) phase = 3;
            if(r <= 0 && g >= 1 && b >= 1) phase = 4;
            if(r <= 0 && g <= 0 && b >= 1) phase = 5;
        }

        if(phase == 0) r += dc;
        if(phase == 1) g += dc;
        if(phase == 2) b += dc;
        if(phase == 3) r -= dc;
        if(phase == 4) g -= dc;
        if(phase == 5) b -= dc;

        sendData(new float[] {r, g, b});

        ScalarField field;*/

        int index = 0;
        float[] data = new float[field.getSizeX() * field.getSizeY() * field.getSizeZ()];
        for(int yy = 0; yy < field.getSizeY(); yy++) {
            for (int zz = 0; zz < field.getSizeZ(); zz++) {
                for (int xx = 0; xx < field.getSizeX(); xx++) {
                    data[index] = field.getValueAt(xx, yy, zz);
                    index++;
                }
            }
        }

        sendData(data);
    }

}
