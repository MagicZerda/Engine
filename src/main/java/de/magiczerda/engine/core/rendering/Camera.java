package de.magiczerda.engine.core.rendering;

import de.magiczerda.engine.core.io.MousePicking;
import de.magiczerda.engine.core.math.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Transformation {

    public Camera() {
        calcViewMatrix();

        MousePicking.camera = this;
    }

    protected Matrix4f currentViewMatrix = new Matrix4f().identity();
    protected Matrix4f invertedViewMatrix = new Matrix4f();

    public void calcViewMatrix() {
        //currentViewMatrix = super.getNewTrafoMat();
        //currentViewMatrix.translate(-2f * translation.x, -2f * translation.y, -2f * translation.z);

        currentViewMatrix.identity();
        currentViewMatrix.rotate(rotation);
        currentViewMatrix.translate(translation.mul(-1, new Vector3f()));

        currentViewMatrix.invert(invertedViewMatrix);
    }

    public Matrix4f getViewMatrix() { return currentViewMatrix; }
    public Matrix4f getInvertedViewMatrix() { return invertedViewMatrix; }

    public Vector3f getPosition() {
        return super.translation.mul(1, new Vector3f());
    }

}
