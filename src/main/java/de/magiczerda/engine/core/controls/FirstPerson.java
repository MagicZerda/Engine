package de.magiczerda.engine.core.controls;

import de.magiczerda.engine.core.io.CursorCallback;
import de.magiczerda.engine.core.io.KeyCallback;
import de.magiczerda.engine.core.math.Transformation;
import de.magiczerda.engine.core.util.Settings;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.sql.SQLOutput;

public class FirstPerson {


    public static float SPEED_MULTIPLIER = 1;

    protected Transformation toControl;

    protected Vector3f relativeVelocity = new Vector3f();
    protected Vector3f absoluteVelocity = new Vector3f();

    protected Vector2f pitchYaw = new Vector2f();

    public FirstPerson(Transformation toControl) {
        this.toControl = toControl;
    }

    public void setPitchYaw(Vector2f pitchYaw) {
        this.pitchYaw.set(pitchYaw);
    }
    public void setPitchYaw(float pitch, float yaw) { this.pitchYaw.set(pitch, yaw); }


    public void update() {
        this.pitchYaw.set(pitchYaw);
        relativeVelocity.set(0, 0, 0);

        if(KeyCallback.isW()) relativeVelocity.z = -1;
        if(KeyCallback.isA()) relativeVelocity.x = -1;
        if(KeyCallback.isS()) relativeVelocity.z =  1;
        if(KeyCallback.isD()) relativeVelocity.x =  1;

        if(KeyCallback.isShift()) relativeVelocity.y = -1;
        if(KeyCallback.isSpace()) relativeVelocity.y =  1;


        pitchYaw.set(CursorCallback.cursorPosition.y * 0.001f,
                     CursorCallback.cursorPosition.x * 0.001f);


        float cosY = (float) Math.cos(pitchYaw.y);
        float sinY = (float) Math.sin(pitchYaw.y);

        float velX = 0, velZ = 0;
        if(relativeVelocity.z != 0) { //moving forward/ backward
            velX += relativeVelocity.z * (-sinY);
            velZ += relativeVelocity.z * cosY;
        } if(relativeVelocity.x != 0) { //moving left/ right
            velX += relativeVelocity.x * cosY;
            velZ += relativeVelocity.x * (sinY);
        }

        toControl.getRotation().setPitchYaw(pitchYaw);
        toControl.getRotation().applyPitchYaw();


        absoluteVelocity.set(velX, relativeVelocity.y, velZ);


        //absoluteVelocity.set(relativeVelocity);
        absoluteVelocity.mul(SPEED_MULTIPLIER * 0.01f);    //Time.dt
        toControl.addTranslation(absoluteVelocity);

    }

}
