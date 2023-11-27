package de.magiczerda.engine.core.math;

import de.magiczerda.engine.core.util.Settings;
import org.joml.Matrix4f;

public class Maths {

    private static Matrix4f projectionMatrix = null;
    private static Matrix4f inverseProjection = new Matrix4f();

    public static float currentZoomFactor = 1;

    private static void calcProjectionMatrix() {
        if(projectionMatrix == null) {
            float aspectRatio = (float)(Settings.CURRENT_WIDTH / Settings.CURRENT_HEIGHT);
            float y_scale = (float) ((1f / Math.tan(Math.toRadians(Settings.FOV/2))) * aspectRatio);
            float x_scale = y_scale / aspectRatio;
            float frustum_length = Settings.FAR_PLANE - Settings.NEAR_PLANE;

            if(projectionMatrix == null) projectionMatrix = new Matrix4f();
            else projectionMatrix.identity();

            projectionMatrix.m00(x_scale);// = x_scale;
            projectionMatrix.m11(y_scale);// = y_scale;
            projectionMatrix.m22(-((Settings.FAR_PLANE + Settings.NEAR_PLANE) / frustum_length));
            projectionMatrix.m23(-1);// = -1;
            projectionMatrix.m32(-((2 * Settings.NEAR_PLANE * Settings.FAR_PLANE) / frustum_length));
            projectionMatrix.m33(0);// = 0;
        }

        inverseProjection = projectionMatrix.invert(inverseProjection);

    }

    public static void changeZoom(double zoomMultiplier) {
        float aspectRatio = (float)(Settings.CURRENT_WIDTH / Settings.CURRENT_HEIGHT);
        float y_scale = (float) ((1f / Math.tan(Math.toRadians((Settings.FOV*zoomMultiplier)/2))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;

        projectionMatrix.m00(x_scale);// = x_scale;
        projectionMatrix.m11(y_scale);// = y_scale;

        currentZoomFactor = (float) (Settings.FOV*zoomMultiplier);
    }

    public static void recalculate() {
        calcProjectionMatrix();
    }

    public static Matrix4f getProjectionMatrix() { return projectionMatrix; }

    public static Matrix4f getInverseProjection() { return inverseProjection; }

}
