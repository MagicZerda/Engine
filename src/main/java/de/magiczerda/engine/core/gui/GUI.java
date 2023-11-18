package de.magiczerda.engine.core.gui;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.gameObjects.Model;
import de.magiczerda.engine.core.loading.Loader;

public class GUI extends GameObject {

    protected static final float[] quad = {
            -1, -1, 0,
             1, -1, 0,
             1,  1, 0,
            -1,  1, 0
    };

    protected static final int[] indices = {
            0, 1, 2, 2, 3, 0
    };

    protected static final float[] textureCoords = {
             0,  1,
             1,  1,
             1,  0,
             0,  0
    };

    protected static final Model quadModel = Loader.loadModel(quad, indices, textureCoords, true);

    public GUI() {
        super(quadModel);
    }

}
