package de.magiczerda.engine.core.gameObjects;

import de.magiczerda.engine.core.math.Transformation;
import org.joml.Vector3f;

public class GameObject extends Transformation {

    //protected Transformation transformation;
    protected Model model;

    protected Vector3f speed = new Vector3f();

    /*
    public GameObject(int vaoID, int vertexCount, int... vboIDs) {
        super();
        model = new Model(vaoID, vertexCount, vboIDs);
        //transformation = new Transformation();
    }*/

    public GameObject(Model model) {
        this.model = model;
        //this.transformation = transformation;
    }

    public Model getModel() { return model; }


}
