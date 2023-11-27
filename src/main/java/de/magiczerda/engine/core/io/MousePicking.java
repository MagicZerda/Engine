package de.magiczerda.engine.core.io;

import de.magiczerda.engine.core.math.Maths;
import de.magiczerda.engine.core.rendering.Camera;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MousePicking {

    protected static Vector4f helper = new Vector4f();
    public static Camera camera;

    public static Vector3f worldRay = new Vector3f();
    public static Vector3f cursorWorldPos = new Vector3f();


    public static Vector3f screenCenter = new Vector3f();

    public static void calc() {
        helper.set(CursorCallback.ndcCursorPos, -1, 1);

        helper = Maths.getInverseProjection().transform(helper, helper);    //eye ray
        helper.z = -1;
        helper.w =  0;

        helper = camera.getInvertedViewMatrix().transform(helper, helper);

        worldRay.set(helper.x, helper.y, helper.z);
        worldRay.normalize(worldRay);


        cursorWorldPos = camera.getPosition().add(worldRay.mul(5, new Vector3f()), cursorWorldPos);
    }


    public static Vector3f getScreenCenter() {
        helper.set(0, 0, -1, 1);

        helper = Maths.getInverseProjection().transform(helper, helper);    //eye ray
        helper.z = -1;
        helper.w =  0;

        helper = camera.getInvertedViewMatrix().transform(helper, helper);

        screenCenter.set(helper.x, helper.y, helper.z);
        return screenCenter.normalize(screenCenter);
    }

}
