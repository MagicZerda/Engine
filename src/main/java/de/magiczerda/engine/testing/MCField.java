package de.magiczerda.engine.testing;

import de.magiczerda.engine.marchingCubes.MarchingCubes;
import de.magiczerda.engine.marchingCubes.MarchingCubesField;
import de.magiczerda.engine.marchingCubes.MarchingCubesFieldSeg;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MCField extends MarchingCubesField {

    protected Vector3f pos = new Vector3f();

    protected Vector4f[] charges = {    //The last argument is the charge
            //new Vector4f(0, 0, 0, 10),
            //new Vector4f(3, 7, 5, 2),
            //new Vector4f(10, 10, 10, 5),
            //new Vector4f(15, 5, 3, 10),
            new Vector4f(5, 5, 5, 7),
            new Vector4f(15, 5, 5, 7),
            new Vector4f(15, 5, 15, 7),
            new Vector4f(5, 5, 15, 7),
            new Vector4f(5, 15, 5, 7),
            new Vector4f(15, 15, 5, 7),
            new Vector4f(15, 15, 15, 7),
            new Vector4f(5, 15, 15, 7)
    };

    public MCField() {
        super(2, 2, 2);

        for(int ssy = 0; ssy < segSize.y; ssy++) {
            for(int ssz = 0; ssz < segSize.z; ssz++) {
                for(int ssx = 0; ssx < segSize.x; ssx++) {
                    MarchingCubesFieldSeg currentSegment = getSegment(ssx, ssy, ssz);

                    for(int yy = 0; yy < MarchingCubesFieldSeg.SIZE_Y; yy++) {
                        for(int zz = 0; zz < MarchingCubesFieldSeg.SIZE_Z; zz++) {
                            for(int xx = 0; xx < MarchingCubesFieldSeg.SIZE_X; xx++) {
                                currentSegment.setValAt(xx, yy, zz,
                                        getValAt(ssx * MarchingCubesFieldSeg.SIZE_X + xx,
                                                 ssy * MarchingCubesFieldSeg.SIZE_Y + yy,
                                                 ssz * MarchingCubesFieldSeg.SIZE_Z + zz));
                            }
                        }
                    }
                }
            }
        }
    }

    protected float getValAt(int xx, int yy, int zz) {
        pos.set(xx, yy, zz);
        float value = 0;

        for(Vector4f charge : charges) {
            value += charge.w / pos.distanceSquared(charge.x, charge.y, charge.z);
        }

        return value;
    }

}
