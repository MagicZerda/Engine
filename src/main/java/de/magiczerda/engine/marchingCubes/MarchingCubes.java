package de.magiczerda.engine.marchingCubes;

import de.magiczerda.engine.core.gameObjects.GameObject;

public class MarchingCubes {


    protected MarchingCubesField marchingCubesField;
    protected MarchingCubesCompute marchingCubesCompute;

    protected float[] vertices = new float[0];


    protected float tt = 0;

    public MarchingCubes(MarchingCubesField marchingCubesField) {
        this.marchingCubesCompute = new MarchingCubesCompute(marchingCubesField);
        this.marchingCubesField = marchingCubesField;
    }

    int ii = 0;
    public void update() {
        ii++;
        if(ii < 10) return;
        ii = 0;

        tt += System.currentTimeMillis() / 10000000000f;

        marchingCubesCompute.setIsolevel(0.3f * (float) (Math.sin(tt / 10000f)+1)/2f + 0.6f);
        marchingCubesCompute.march();

        /*for(int ii = 0; ii < d.length/100; ii++)
            System.out.print(d[ii] + " ");
        System.out.println();*/
    }

    public float[] getVertices() {
        float[] vert = marchingCubesCompute.getVertices();
        /*float[] vert3D = new float[3 * vert4D.length/3];

        for(int ii = 0; ii < vert4D.length/4; ii++) {
            vert3D[3*ii  ] = vert4D[4*ii  ];
            vert3D[3*ii+1] = vert4D[4*ii+1];
            vert3D[3*ii+2] = vert4D[4*ii+2];
        }

        return vert3D;*/

        /*List<Float> redVertices = new ArrayList<>();
        for(int ii = 0; ii < vert.length/3; ii++) {
            if(vert[3*ii] == 0 && vert[3*ii+1] == 0 && vert[3*ii+2] == 0) continue;
            else {
                redVertices.add(vert[3*ii]);
                redVertices.add(vert[3*ii + 1]);
                redVertices.add(vert[3*ii + 2]);
            }
        }

        float[] ret = new float[redVertices.size()];
        for(int ii = 0; ii < redVertices.size(); ii++)
            ret[ii] = redVertices.get(ii);

        for(int ii = 0; ii < ret.length; ii++)
            System.out.print(ret[ii] + " ");
        System.out.println();

        return ret;*/
        return vert;

        //return mcc.getVertices();
    }

    public float[] getNormals() {
        return marchingCubesCompute.getNormals();
    }

    //public EField getField() { return field; }

    public int getTextureID() { return marchingCubesCompute.getTextureID(); }


    public void updateGameObject(float[] vertices, float[] normals) {
        marchingCubesCompute.marchingCubesField.updateGameObject(vertices, normals);
    }
    public GameObject getGameObject() { return marchingCubesCompute.marchingCubesField.getMarchingCubesGameObject(); }

}
