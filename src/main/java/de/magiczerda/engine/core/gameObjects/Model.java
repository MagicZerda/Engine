package de.magiczerda.engine.core.gameObjects;

import de.magiczerda.engine.core.util.Texture;
import de.magiczerda.engine.core.util.VboIDs;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private int vaoID = -1;
    private int vertexCount = -1;
    private int textureID = -1;

    private List<Integer> vboIDs = new ArrayList<>();
    private boolean cullBack = true;

    public Model(int vaoID, int vertexCount, int... vboIDs) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;

        for(int vboID : vboIDs)
            this.vboIDs.add(vboID);
    }

    public Model(int vaoID, int vertexCount, Texture texture, int... vboIDs) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.textureID = texture.getTextureID();

        for(int vboID : vboIDs)
            this.vboIDs.add(vboID);
    }


    //public void setVBONames(String[] names) {
    //    if(vboIDs != null) this.vboIDs.setNames(names);
    //}

    public void setCullBack(boolean cullBack) { this.cullBack = cullBack; }
    public boolean isCullBack() { return cullBack; }

    public boolean hasTexture() { return textureID != -1; }

    public int getVaoID() { return vaoID; }
    public int getVertexCount() { return vertexCount; }
    public int getTextureID() { return textureID; }

    public void setTextureID(int textureID) { this.textureID = textureID; }

    public int getVBOID(int attributeNumber) { return vboIDs.get(attributeNumber); }

    public void setVertexCount(int vertexCount) { this.vertexCount = vertexCount; }

}
