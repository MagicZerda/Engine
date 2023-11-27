package de.magiczerda.engine.marchingCubes;

import de.magiczerda.engine.core.io.MousePicking;
import de.magiczerda.engine.core.shader.*;
import de.magiczerda.engine.field.ScalarField;

public class MarchingCubesCompute extends ComputeShader {
    protected float isolevel = 0.5f;


    protected float[] outData;
    protected float[] outNormals;

    protected MarchingCubesField marchingCubesField;

    /** 1000 pts per local group */
    protected static final int SSBO_OUT_SIZE = 9*9*9 * 1000 * 10;//9*9*9;

    public MarchingCubesCompute(MarchingCubesField marchingCubesField) {
        super(marchingCubesField.segSize, SSBO_OUT_SIZE, "marchingCubes/marchingCubesCompute.glsl");

        this.marchingCubesField = marchingCubesField;
    }


    public float[] march() {
        super.start();
        super.loadFloat("isolevel", isolevel);
        super.loadVector3f("worldRay", MousePicking.getScreenCenter());
        super.loadVector3f("segSize", marchingCubesField.getSegSize());

        super.sendData(marchingCubesField.getFieldValues());

        super.deploySSBO(true);
        //outData    = ssbos[1].getData();
        //outNormals = ssbos[2].getData();

        //float[] dd = ssbos[3].getData();

        outData = getData(1);
        outNormals = getData(2);
        float[] dd = getData(3);

        super.stop();

        return dd;
    }

    public float[] getVertices() {
        return outData;
    }

    public float[] getNormals() {
        return outNormals;
    }


    public void setIsolevel(float isolevel) { this.isolevel = isolevel; }
    public float getIsolevel() { return isolevel; }

}
