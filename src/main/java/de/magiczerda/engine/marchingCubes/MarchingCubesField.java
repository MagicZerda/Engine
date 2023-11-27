package de.magiczerda.engine.marchingCubes;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.loading.Loader;
import org.joml.Vector3f;

public class MarchingCubesField {

    protected Vector3f segSize = new Vector3f(1, 1, 1);
    /*protected int xSegCount = 1,
                  ySegCount = 1,
                  zSegCount = 1;*/

    protected MarchingCubesFieldSeg segments[];

    protected GameObject marchingCubesField;

    public MarchingCubesField() {
        this.segments = new MarchingCubesFieldSeg[] { new MarchingCubesFieldSeg() };
        marchingCubesField = new GameObject(Loader.loadModel(new float[0], new float[0], false));
    }

    public MarchingCubesField(int xSegCount, int ySegCount, int zSegCount) {
        this.segSize.set(xSegCount, ySegCount, zSegCount);
        /*this.xSegCount = xSegCount;
        this.ySegCount = ySegCount;
        this.zSegCount = zSegCount;*/

        this.segments = new MarchingCubesFieldSeg[xSegCount * ySegCount * zSegCount];
        for(int ii = 0; ii < segments.length; ii++)
            this.segments[ii] = new MarchingCubesFieldSeg();

        marchingCubesField = new GameObject(Loader.loadModel(new float[0], new float[0], false));
    }

    public void updateGameObject(float[] vertices, float[] normals) {
        if(vertices == null || normals == null) {
            System.err.println("Can't update model as the vertices or normals are null!");
            return;
        }
        Loader.updateModel(marchingCubesField.getModel(), vertices, normals);
    }

    public void setSegments(MarchingCubesFieldSeg... segments) {
        this.segments = segments;
    }


    public float[] getFieldValues() {

        /**
         * 1000 values per segment
         */

        float[] fieldVal = new float[1000 * (int) (segSize.x * segSize.y * segSize.z)];
        int fieldValIndex = 0;

        for(int yy = 0; yy < segSize.y; yy++) {
            for(int zz = 0; zz < segSize.z; zz++) {
                for(int xx = 0; xx < segSize.x; xx++) {
                    float[] segVal = getSegment(xx, yy, zz).getFieldValues();

                    for(float ssv : segVal) {
                        fieldVal[fieldValIndex] = ssv;
                        fieldValIndex++;
                    }
                }
            }
        }

        return fieldVal;
    }


    public MarchingCubesFieldSeg getSegment(int xID, int yID, int zID) {
        return segments[(int) (segSize.z * segSize.x * yID + segSize.x * zID + xID)];
    }

    public GameObject getMarchingCubesGameObject() { return marchingCubesField; }

    public Vector3f getSegSize() { return segSize; }
    /*public int getXSegCount() { return segSize.x; }
    public int getYSegCount() { return ; }
    public int getZSegCount() { return zSegCount; }*/

}
