package de.magiczerda.engine.core.loading;

import de.magiczerda.engine.core.gameObjects.Model;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    /* All VAOs, VBOs and texture IDs will be saved to
     * clean them up in the end as required by OpenGL*/
    protected static List<Integer> vaos = new ArrayList<>();
    protected static List<Integer> vbos = new ArrayList<Integer>();
    protected static List<Integer> textures = new ArrayList<Integer>();



    public static Model loadModel(float[] vertices, boolean static_draw) {
        int vao = createVAO();

        int vertexVBO = loadToVAO(0, 3, vertices, static_draw);

        unbindVAO();


        return new Model(vao, vertices.length / 3, vertexVBO);
    }


    public static void updateModel(Model model, int verticesDimension, float[] newVertices) {
        GL30.glBindVertexArray(model.getVaoID());

        int attributeNumber = 0;
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getVBOID(attributeNumber));

        /* Prepare data */
        FloatBuffer buffer = floatArrToBuffer(newVertices);

        /* Sends our data to the VBO */
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);

        /* Specify how OpenGL should interpret the given data */
        GL20.glVertexAttribPointer(attributeNumber, verticesDimension, GL11.GL_FLOAT, false, 0, 0);

        /* Enable new VBO ID in the VAO
         * This number will later be used
         * inside the shader (layout (location = x) in ... [NAME]) */
        //GL20.glEnableVertexAttribArray(attributeNumber);	//(number for layout in shader)
        GL30.glBindVertexArray(0);

        model.setVertexCount(newVertices.length/verticesDimension);
    }



    public static Model loadModel(float[] vertices, int[] indices, boolean static_draw) {
        int vao = createVAO();

        int vertexVBO = loadToVAO(0, 3, vertices, static_draw);
        bindEBO(indices, static_draw);

        unbindVAO();


        return new Model(vao, indices.length, vertexVBO);
    }


    public static Model loadModel(float[] vertices, float[] normals, int[] indices, boolean static_draw) {
        int vao = createVAO();

        int vertexVBO = loadToVAO(0, 3, vertices, static_draw);
        int normalsVBO = loadToVAO(2, 3, normals, static_draw);
        bindEBO(indices, static_draw);

        unbindVAO();


        return new Model(vao, indices.length, vertexVBO, normalsVBO);
    }

    public static Model loadModel(float[] vertices, int[] indices, float[] textureCoords, boolean static_draw) {
        int vao = createVAO();

        int vertexVBO = loadToVAO(0, 3, vertices, static_draw);
        int normalsVBO = loadToVAO(1, 2, textureCoords, static_draw);
        bindEBO(indices, static_draw);

        unbindVAO();


        return new Model(vao, indices.length, vertexVBO, normalsVBO);
    }




    /** Creates and binds a new VAO, returns its ID */
    protected static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /** Unbinds the currently bound VAO */
    protected static void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Creates a new VBO, loads a float[] of data into it
     * and inserts the new VBO into the currently bound VAO
     * at the specified attribute number.
     *
     * This data usually consists of vectors of
     * differing dimensions, thus we need to specify
     * the dimension of the vector that is given
     * (so that OpenGL will interpret the data
     * correctly and we get the right data in
     * our shader code)
     *
     * We will need 3D vectors to represent the
     * vertex positions, but e.g. (u,v) texture
     * coordinates only use two coordinates
     * per vector entry.
     *
     * @param attributeNumber specifies at what attribattribute number of the vao this data should be locatedute number of the vao this data should be located
     * @param dimension the dimension of each vector
     * @param data - usually data for vec2/vec3 -> (v1.x, v1.y, v1.z, v2.x, ... / t1.u, t1.v, t2.u, ...)
     * @param static_draw - true if the data will never change, false if you intend to change the contained data at some point.
     * @return the ID of the VBO that was created and inserted into the currently bound VAO
     */
    protected static int loadToVAO(int attributeNumber, int dimension, float[] data, boolean static_draw) {
        /* Create and bind new VBO */
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        /* Prepare data */
        FloatBuffer buffer = floatArrToBuffer(data);

        /* Sends our data to the VBO */
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, static_draw ? GL15.GL_STATIC_DRAW : GL15.GL_DYNAMIC_DRAW);

        /* Specify how OpenGL should interpret the given data */
        GL20.glVertexAttribPointer(attributeNumber, dimension, GL11.GL_FLOAT, false, 0, 0);

        /* Enable new VBO ID in the VAO
         * This number will later be used
         * inside the shader (layout (location = x) in ... [NAME]) */
        GL20.glEnableVertexAttribArray(attributeNumber);	//(number for layout in shader)
        return vboID;
    }




    /** Creates and binds a new element array buffer */
    protected static void bindEBO(int[] indices, boolean static_draw) {
        /* Create and bind new EBOs */
        int ebo = GL15.glGenBuffers();
        vbos.add(ebo);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);

        /* Send the index data to our new EBO */
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, static_draw ? GL15.GL_STATIC_DRAW : GL15.GL_DYNAMIC_DRAW);
    }


    public static FloatBuffer floatArrToBuffer(float... data) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
        fb.put(data);
        fb.flip();
        return fb;
    }




    /**
     * Delete everything once the program is done running.
     * This has to be done because OpenGL leaves
     * memory management to the programmer
     */

    public static void cleanUp() {
        for(int v : vbos) GL15.glDeleteBuffers(v);
        for(int v : vaos) GL33.glDeleteVertexArrays(v);
        for(int t : textures) GL11.glDeleteTextures(t);
    }

}
