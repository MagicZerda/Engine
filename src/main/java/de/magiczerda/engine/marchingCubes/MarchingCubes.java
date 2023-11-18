package de.magiczerda.engine.marchingCubes;

import de.magiczerda.engine.core.controls.SpecialKeys;
import de.magiczerda.engine.core.io.KeyCallback;
import de.magiczerda.engine.field.PointRenderer;
import de.magiczerda.engine.field.ScalarField;
import de.magiczerda.engine.testing.EField;

public class MarchingCubes {

    protected MarchingCubesCompute compute;
    protected EField field;


    protected float iso_value = 0.5f;

    public MarchingCubes() {
        field = new EField();
        compute = new MarchingCubesCompute(
                1,
                1,
                1);

        compute.loadUniforms(field.getSizeX(), field.getSizeY(), field.getSizeZ(), iso_value, false);


        /*SpecialKeys.toRunOnJ = new Runnable() {
            @Override
            public void run() {
                int xx = cubeInd % (field.getSizeX() - 1);
                int yy = cubeInd / ((field.getSizeX() - 1) * (field.getSizeZ() - 1));
                int zz = (cubeInd / (field.getSizeX() - 1)) % (field.getSizeZ() - 1);

                yy %= field.getSizeY() - 1;
                cubeInd++;

                addPoint(xx, yy, zz);
            }
        };*/

    }


    int cubeInd = 0;

    public void march() {

        //float[] dt = compute.getData(4 * (field.getSizeX() - 1) * (field.getSizeZ() - 1) * 9);

        float[] dt = compute.getData(700*3);
        for(int ii = 0; ii < dt.length; ii++)
            System.out.print(dt[ii] + " ");
        System.out.println();

        //BoxRenderer.box.setTranslation();
        if(KeyCallback.isY()) {
            //field.update(0.01f);

            compute.start();
            compute.loadInt("t", SpecialKeys.M_COUNT);
            compute.update(field);
            compute.deploySSBO(true);
            compute.stop();

            PointRenderer.updateVertices(dt);//dt);
        }


        /*int xx = cubeInd % (field.getSizeX() - 1);
        int yy = cubeInd / ((field.getSizeX() - 1) * (field.getSizeZ() - 1));
        int zz = (cubeInd / (field.getSizeX() - 1)) % (field.getSizeZ() - 1);

        yy %= field.getSizeY() - 1;
        cubeInd++;

        addPoint(xx, yy, zz);*/

    }

    /*protected void addPoint(int xx, int yy, int zz) {
        //PointRenderer.addVertices(new float[] {xx, yy, zz, 1});

        BoxRenderer.box.setTranslation(xx, yy, zz);
        PointRenderer.updateVertices(new float[] {xx, yy, zz, 0,            //0
                                                xx+1, yy, zz, 0.1f,         //1
                                                xx+1, yy, zz+1, 0.2f,          //2
                                                xx, yy, zz+1, 0.3f,                //3
                                                xx, yy+1, zz, 0.4f,            //4
                                                xx+1, yy+1, zz, 0.5f,          //5
                                                xx+1, yy+1, zz+1, 0.6f,           //6
                                                xx, yy+1, zz+1, 1              //7
        });
    }*/

    public int getTextureID() {
        return compute.getTextureID();
    }

    public ScalarField getField() { return field; }

}
