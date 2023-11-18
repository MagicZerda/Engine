package de.magiczerda.engine.testing;

import de.magiczerda.engine.core.loading.Loader;
import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.gameObjects.Model;

public class Triangle extends GameObject {

    protected static float[] vertices = {
            /*
            -0.5f,  -0.5f,  0,
             0.5f,  -0.5f,  0,
             0,      0.5f,  0
             */

            -0.5f,  -0.5f,  0,
            0.5f,   -0.5f,  0,
            0.5f,   0.5f,   0,
            -0.5f,  0.5f,   0
    };

    protected static int[] indices = {
            0, 1, 2,
            2, 0, 3
    };

    public Triangle() {
        super(Loader.loadModel(vertices, indices, true));
    }

    public Model getModel() { return model; }

}
