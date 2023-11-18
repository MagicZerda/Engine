package de.magiczerda.engine.core.util;

import org.lwjgl.system.Callback;

import java.util.ArrayList;
import java.util.List;

public class CleanUp {

    public static List<Callback> callbacks = new ArrayList<>();

    public static void freeCallbacks() {
        for(Callback cb : callbacks)
            if(cb != null) cb.free();
    }

}
