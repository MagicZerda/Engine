package de.magiczerda.engine.field;

import de.magiczerda.engine.core.gameObjects.Model;
import de.magiczerda.engine.core.loading.Loader;

public class FieldLoader extends Loader {

    public static Model loadField(float[] vertices, boolean static_draw) {
        int vao = createVAO();

        int vertexVBO = loadToVAO(0, 4, vertices, static_draw);
        unbindVAO();

        return new Model(vao, vertices.length / 4, vertexVBO);
    }

}
