package de.magiczerda.engine.testing;

import de.magiczerda.engine.boxRenderer.BoxRenderer;
import de.magiczerda.engine.core.controls.FirstPerson;
import de.magiczerda.engine.core.gui.GUIRenderer;
import de.magiczerda.engine.core.io.KeyCallback;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.math.Maths;
import de.magiczerda.engine.core.scene.Scene;
import de.magiczerda.engine.core.shader.ComputeShader;
import de.magiczerda.engine.field.FieldRenderer;
import de.magiczerda.engine.field.PointRenderer;
import de.magiczerda.engine.marchingCubes.MarchingCubes;
import de.magiczerda.engine.noise.NoiseCompute;
import de.magiczerda.engine.plane.PlaneRenderer;
import de.magiczerda.engine.vectorVis.VectorRenderer;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import javax.swing.*;

public class TestScene extends Scene {

    protected Camera camera;
    protected Triangle triangle;
    protected TRenderer renderer;

    protected FirstPerson fp;


    protected PlaneRenderer pRend;

    protected VectorRenderer vRend;

    protected GUIRenderer guiRenderer;
    protected ComputeShader compShader;

    protected FieldRenderer fieldRenderer;

    protected BoxRenderer br;


    protected MarchingCubes marchingCubes;

    protected PointRenderer pr;

    protected NoiseCompute noise;
/*
    public TestScene() {
        super(new Camera(), new Triangle());
    }*/

    @Override
    public void init() {
        this.camera = new Camera();
        this.triangle = new Triangle();
        this.renderer = new TRenderer(camera, triangle);

        fp = new FirstPerson(camera);


        triangle.setTranslation(0, 3, -10);

        pRend = new PlaneRenderer(camera);
        vRend = new VectorRenderer(camera);

        guiRenderer = new GUIRenderer();

        br = new BoxRenderer(camera);

        //marchingCubes = new MarchingCubes();
        //marchingCubes.march();

        //fieldRenderer = new FieldRenderer(camera, marchingCubes.getField());
        //guiRenderer.getGui().getModel().setTextureID(marchingCubes.getTextureID());

        pr = new PointRenderer(camera);

        noise = new NoiseCompute();
        guiRenderer.getGui().getModel().setTextureID(noise.getFBMTextureID());
    }

    @Override
    public void updateMatrices() {
        triangle.calcTrafoMat();
        camera.calcViewMatrix();
    }

    @Override
    public void render() {
        renderer.render(triangle, true);
        pRend.render();

        if(KeyCallback.isV()) vRend.render();
        //marchingCubes.march();

        //fieldRenderer.render();
        //guiRenderer.render();
        //compShader.deploy(false);
        br.render();

        pr.render();
    }

    int ii = 0;
    @Override
    public void update(float dt) {
        fp.update();

        //noise.calcFBM(new Vector2f(System.nanoTime()/500f, System.nanoTime()/1000f));

    }

    @Override
    public void terminate() {

    }
}
