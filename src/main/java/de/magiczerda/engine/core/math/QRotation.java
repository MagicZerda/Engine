package de.magiczerda.engine.core.math;

import org.joml.*;

import java.lang.Math;

public class QRotation extends Quaternionf {

    private static final float PI = (float) Math.PI;

    private Matrix4f currentRotationMatrix = new Matrix4f().identity();
    protected Vector2f pitchYaw = new Vector2f();

    public QRotation() {
        super(new AxisAngle4f());
    }

    public QRotation(Vector3f axis, float angle) {
        super(new AxisAngle4f(angle, axis));
    }


    /** This field determines whether changing
     *  the pitch past 90° up- /downwards
     *  is allowed or not.
     *
     *  If this field is set to true, every
     *  pitch can be assumed,
     *  but when looking too far up/ down,
     *  yaw controls are reversed.				*/

    public static boolean allowFullPitch = true;



    /**
     * Used for controlling the entity
     * with the cursor in 1st person
     */

    /*public void calculatePitchYawVectorFromCursorPosition() {
        Vector2f dcp = CursorCallback.getCursorPosDifference();
        dcp.mul(10*Settings.mouse_sensitivity);

        pitchYaw.x -= dcp.y * TimeKeeper.DT;
        pitchYaw.y += dcp.x * TimeKeeper.DT;

        float absPit = Math.abs(pitchYaw.x);
        if(allowFullPitch) {
            if(absPit >= 2.0*PI)
                pitchYaw.x %= 2.0*PI;
        } else {
            if(absPit >= 0.5*PI) pitchYaw.x = (float)
                    (pitchYaw.x > 0 ? 0.5*PI : -0.5*PI);
        }

        if(Math.abs(pitchYaw.y) >= 2.0*PI) pitchYaw.y %= 2.0 * PI;
    }*/


    public void setPitchYaw(Vector2f pitchYaw) {
        this.pitchYaw.set(pitchYaw);
        applyPitchYaw();
    }

    public void setPitchYaw(float pitch, float yaw) {
        this.pitchYaw.set(pitch, yaw);
        applyPitchYaw();
    }

    public void changePitchYaw(float dPitch, float dYaw) {
        pitchYaw.add(dPitch, dYaw);
        applyPitchYaw();
    }

    public void changePitchYaw(Vector2f dPitchYaw) {
        pitchYaw.add(dPitchYaw);
        applyPitchYaw();
    }

    public void applyPitchYaw() {
        rotationXYZ(pitchYaw.x, pitchYaw.y, 0);
    }


    public Vector2f getPitchYaw() { return pitchYaw; }

    public float getPitch() { return pitchYaw.x; }
    public float getYaw() { return pitchYaw.y; }

    public Matrix4f getRotationMatrix() {
        return (currentRotationMatrix.identity()).rotate(this);
    }

}
