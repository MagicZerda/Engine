package de.magiczerda.engine.noise;

import de.magiczerda.engine.core.shader.ComputeShader;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class NoiseCompute {
    protected static ComputeShader fbmCompute = new ComputeShader(
            1024, 1024, 1, "noise/fbmCompute.glsl");
    protected ComputeShader voronoiCompute = new ComputeShader(
            128, 128, 1, "noise/voronoiCompute.glsl");


    protected static int skip = 0;


    public static void calcFBM(Vector2f coordinates) {
        skip++;
        if(skip % 5 != 0) return;
        else skip = 0;
        fbmCompute.start();
        fbmCompute.loadVector2f("coordinates", coordinates);
        fbmCompute.deployTexture(true);
        fbmCompute.stop();
    }

    public void calcVoronoi() {
        voronoiCompute.start();
        voronoiCompute.loadFloat("randomSeed", System.nanoTime());
        voronoiCompute.deploySSBO(true);
        voronoiCompute.stop();
    }

    public static int getFBMTextureID() {
        return fbmCompute.getTextureID();
    }

    public int getVoronoiTextureID() {
        return voronoiCompute.getTextureID();
    }

}
