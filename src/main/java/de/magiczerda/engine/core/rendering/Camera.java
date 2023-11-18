package de.magiczerda.engine.core.rendering;

import de.magiczerda.engine.core.math.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Transformation {

    protected Matrix4f currentViewMatrix = new Matrix4f().identity();

    public void calcViewMatrix() {
        //currentViewMatrix = super.getNewTrafoMat();
        //currentViewMatrix.translate(-2f * translation.x, -2f * translation.y, -2f * translation.z);

        currentViewMatrix.identity();
        currentViewMatrix.rotate(rotation);
        currentViewMatrix.translate(translation.mul(-1, new Vector3f()));
    }

    public Matrix4f getViewMatrix() { return currentViewMatrix; }

}
