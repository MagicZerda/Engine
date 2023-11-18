package de.magiczerda.engine.core.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    protected Vector3f translation = new Vector3f();
    protected Vector3f scale = new Vector3f(1, 1, 1);
    protected QRotation rotation;

    protected Matrix4f currentTrafoMat = new Matrix4f().identity();

    public Transformation() {
        this.rotation = new QRotation();
    }

    public Transformation(Vector3f translation, QRotation rotation, Vector3f scale) {
        this.translation.set(translation);
        this.rotation = rotation;
        this.scale.set(scale);
    }


    public void calcTrafoMat() {
        currentTrafoMat.identity();
        currentTrafoMat.translationRotateScale(translation, rotation, scale);
    }


    public Matrix4f getTrafoMat() { return currentTrafoMat; }
    /*protected Matrix4f getNewTrafoMat() {
        calcTrafoMat();
        return currentTrafoMat;
    }*/


    public void setTranslation(Vector3f translation) { this.translation.set(translation); }
    public void setTranslation(float x, float y, float z) { this.translation.set(x, y, z); }
    public void addTranslation(Vector3f dTranslation) { this.translation.add(dTranslation); }
    public void addTranslation(float dx, float dy, float dz) { this.translation.add(dx, dy, dz); }


    public void setScale(Vector3f scale) { this.scale.set(scale); }
    public void setScale(float sx, float sy, float sz) { this.scale.set(sx, sy, sz); }
    public void addScale(Vector3f dScale) { this.scale.add(dScale); }
    public void addScale(float dsx, float dsy, float dsz) { this.scale.add(dsx, dsy, dsz); }

    public QRotation getRotation() { return this.rotation; }

}
