package de.magiczerda.engine.vectorVis;

import org.joml.Vector3f;

public class Vector {

    protected Vector3f origin = new Vector3f(),
                       direction = new Vector3f();

    public Vector(Vector3f origin, Vector3f direction) {
        this.origin.set(origin);
        this.direction.set(direction);
    }

    public Vector(float ox, float oy, float oz, float dx, float dy, float dz) {
        this.origin.set(ox, oy, oz);
        this.direction.set(dx, dy, dz);
    }


    public Vector3f getOrigin() { return origin; }
    public Vector3f getDirection() { return direction; }

}
