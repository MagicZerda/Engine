package de.magiczerda.engine.marchingCubes;

public class MarchingCubesFieldSeg {

    public static final int SIZE_X = 10, SIZE_Y = 10, SIZE_Z = 10;

    protected float[] fieldValues = new float[SIZE_X * SIZE_Y * SIZE_Z];

    public MarchingCubesFieldSeg() {

    }

    public void setValAt(int xx, int yy, int zz, float value) {
        this.fieldValues[SIZE_Z * SIZE_X * yy + SIZE_X * zz + xx] = value;
    }

    public float[] getFieldValues() { return fieldValues; }

}
