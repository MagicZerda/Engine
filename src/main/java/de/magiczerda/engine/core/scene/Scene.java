package de.magiczerda.engine.core.scene;

import de.magiczerda.engine.core.math.Maths;
import de.magiczerda.engine.core.util.Time;

public abstract class Scene {

    /*protected Camera camera;
    protected List<GameObject> gameObjects = new ArrayList<>();


    public Scene(Camera camera, GameObject... gameObjects) {
        this.camera = camera;

        for(GameObject gameObject : gameObjects)
            this.gameObjects.add(gameObject);

    }*/

    public Scene() {
        Maths.recalculate();
    }

    public abstract void init();


    public abstract void updateMatrices();

    public abstract void render();
    public abstract void update(float dt);

    public abstract void terminate();


    public void loop() {
        update(Time.dt);
        updateMatrices();

        render();
    }

}
