package de.magiczerda.engine.testing;

import de.magiczerda.engine.boxRenderer.BoxRenderer;
import de.magiczerda.engine.core.controls.SpecialKeys;
import de.magiczerda.engine.field.ScalarField;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EField extends ScalarField {

    protected static final int sizeX = 10,
            sizeY = 10,
            sizeZ = 10;


    protected List<Vector3f> charges;


    public EField() {
        super(sizeX, sizeY, sizeZ);
    }


    @Override
    protected void init() {
        charges = new ArrayList<>();
        charges.add(new Vector3f(0, 0, 0));
        charges.add(new Vector3f(6, 6, 6));
        //charges.add(new Vector3f(7, 6, 7));
    }

    protected float val = 0;
    public void update(float val) {
        this.val += val;
        charges.get(0).add(0.01f * (float) Math.sin(this.val), 0, 0.01f * (float) Math.cos(this.val));
        updateValues();
    }

    @Override
    protected float getHeightAt(int xx, int yy, int zz) {
        float value = 0;


        int chargeCount = charges.size();
        for(int oo = 0; oo < chargeCount; oo++) {

            value += 10f / charges.get(oo).distanceSquared(xx, yy, zz);

            /*for(int dd = oo + 1; dd < chargeCount; dd++) {
                value += charges.get(oo).distance(charges.get(dd));
                System.out.println(oo + "   " + dd);
            }*/
        }

        return value;
    }


    public static void move() {
        int xx = SpecialKeys.M_COUNT % (sizeX - 1);
        int zz = (SpecialKeys.M_COUNT / (sizeX - 1)) % (sizeZ - 1);
        int yy = (SpecialKeys.M_COUNT / ((sizeX - 1) * (sizeZ - 1))) % (sizeY - 1);
        BoxRenderer.box.setTranslation(xx, yy, zz);
    }

}
