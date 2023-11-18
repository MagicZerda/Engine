package de.magiczerda.engine.core.util;

import java.util.ArrayList;
import java.util.List;


/**
 * VBOs get assigned random values when they are created.
 * If we plan on changing the contents of said VBO,
 * we'd do best to have a way of recalling the VBO's id.
 * This is why we can -per VAO- give names to those VBOs
 * which might be in need of changing at a later point in time.
 * (Normals for example, when the mesh changes)
 */

public class VboIDs {

    private List<String> vboNames = new ArrayList<>();
    private int[] vboIDs;

    public VboIDs(int... vboIDs) {
        this.vboIDs = vboIDs;
    }

    /**
     * Call this method after the constructor
     * and name each VBO in the same order as
     * they were initialized in the constructor
     *
     * @param names
     */

    public void setNames(String... names) {
        for(String name : names)
            vboNames.add(name);
    }


    /**
     * Use this method later to recall the VBO's
     * ID from its name
     *
     * @param name
     * @return
     */

    public int getID(String name) {
        return vboIDs[vboNames.indexOf(name)];
    }

}
