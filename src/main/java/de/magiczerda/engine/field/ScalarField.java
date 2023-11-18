package de.magiczerda.engine.field;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.gameObjects.Model;

public abstract class ScalarField {

    protected int sizeX, sizeY, sizeZ;


    protected float[] vertices;


    protected GameObject gameObject;

    public ScalarField(int sizeX, int sizeY, int sizeZ) {
       this.sizeX = sizeX;
       this.sizeY = sizeY;
       this.sizeZ = sizeZ;

       this.vertices = new float[4 * (sizeX * sizeY * sizeZ)];
       init();

       for(int yy = 0; yy < sizeY; yy++) {
           for(int zz = 0; zz < sizeZ; zz++) {
               for(int xx = 0; xx < sizeX; xx++) {
                   vertices[4 * ((sizeX * sizeZ * yy) + (sizeX * zz) + xx)    ] = xx;
                   vertices[4 * ((sizeX * sizeZ * yy) + (sizeX * zz) + xx) + 1] = yy;
                   vertices[4 * ((sizeX * sizeZ * yy) + (sizeX * zz) + xx) + 2] = zz;
                   vertices[4 * ((sizeX * sizeZ * yy) + (sizeX * zz) + xx) + 3] =
                           getHeightAt(xx, yy, zz);
               }
           }
       }


       Model model = FieldLoader.loadField(vertices, true);
       this.gameObject = new GameObject(model);
    }

    protected void updateValues() {
        for(int yy = 0; yy < sizeY; yy++) {
            for(int zz = 0; zz < sizeZ; zz++) {
                for(int xx = 0; xx < sizeX; xx++) {
                    vertices[4 * ((sizeX * sizeZ * yy) + (sizeX * zz) + xx) + 3] =
                            getHeightAt(xx, yy, zz);
                }
            }
        }
    }

    protected abstract void init();
    protected abstract float getHeightAt(int xx, int yy, int zz);

    public GameObject getGameObject() { return gameObject; }

    public float getValueAt(int xx, int yy, int zz) {
        return vertices[4 * ((sizeX * sizeZ * yy) + (sizeX * zz) + xx) + 3];
    }

    public int getSizeX() { return sizeX; }
    public int getSizeY() { return sizeY; }
    public int getSizeZ() { return sizeZ; }

}
