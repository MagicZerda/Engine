package de.magiczerda.engine.boxRenderer;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.gameObjects.Model;
import de.magiczerda.engine.core.loading.Loader;
import de.magiczerda.engine.core.math.Transformation;
import org.joml.Vector3f;

public class Box extends GameObject {

    /*
    protected static final float[] vertices = {
            -0.5f,  -0.5f,  -0.5f,
             0.5f,  -0.5f,  -0.5f,
             0.5f,  -0.5f,   0.5f
    };*/

    protected static final float[] vertices = {
            0,  0,  0,
            1,  0,  0,
            1,  0,  1,
            0,  0,  1,
            0,  1,  1,
            0,  1,  0,
            1,  1,  0,
            1,  1,  1
    };

    /*protected static final int[] indices = {
            0, 1, 2,    0, 2, 3,
            0, 3, 4,    0, 4, 5,
            0, 6, 1,    0, 5, 6,
            7, 4, 3,    7, 3, 2,
            7, 1, 6,    7, 2, 1,
            4, 6, 5,    7, 6, 4
    };*/

    protected static final int[] indices = {
            0, 1,   1, 2,   2, 3,   3, 0,
            4, 5,   5, 6,   6, 7,   7, 4,
            0, 5,   1, 6,   2, 7,   3, 4
    };

    protected static final Model boxModel = Loader.loadModel(vertices, indices, true);

    public Box() {
        super(boxModel);
    }

    protected static Model getBoxModel() { return boxModel; }


}
