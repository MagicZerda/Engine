package de.magiczerda.engine.plane;

import de.magiczerda.engine.noise.NoiseCompute;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBPerlin;

public class Terrain extends HeightGenerator {


    Plane plane;

    public Terrain() {
        plane = new Plane(this);
    }

    @Override
    protected float getHeightAt(int xx, int zz) {
        return 10f * STBPerlin.stb_perlin_fbm_noise3(1f*xx, 0, 1f*zz, 0.2f, 0.2f, 3);
    }
}
