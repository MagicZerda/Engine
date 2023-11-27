package de.magiczerda.engine.testing;

import de.magiczerda.engine.boxRenderer.BoxRenderer;
import de.magiczerda.engine.core.controls.FirstPerson;
import de.magiczerda.engine.core.gui.GUIRenderer;
import de.magiczerda.engine.core.io.CursorCallback;
import de.magiczerda.engine.core.io.KeyCallback;
import de.magiczerda.engine.core.io.MousePicking;
import de.magiczerda.engine.core.rendering.Camera;
import de.magiczerda.engine.core.scene.Scene;
import de.magiczerda.engine.core.shader.ComputeShader;
import de.magiczerda.engine.field.FieldRenderer;
import de.magiczerda.engine.field.PointRenderer;
import de.magiczerda.engine.marchingCubes.MarchingCubesRenderer;
import de.magiczerda.engine.noise.NoiseCompute;
import de.magiczerda.engine.plane.PlaneRenderer;
import de.magiczerda.engine.vectorVis.VectorRenderer;
import org.joml.Vector3f;

public class TestScene extends Scene {

    protected Camera camera;
    protected Triangle triangle;
    protected TRenderer renderer;

    protected FirstPerson fp;


    protected PlaneRenderer planeRenderer;

    protected VectorRenderer vRend;

    protected GUIRenderer guiRenderer;
    protected ComputeShader compShader;

    protected FieldRenderer fieldRenderer;

    protected BoxRenderer boxRenderer;


    //protected MarchingCubes marchingCubes;
    //protected MC2 mc;

    protected MarchingCubesRenderer mcr;

    protected PointRenderer pointRenderer;

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


        //triangle.setTranslation(0, 3, -10);

        planeRenderer = new PlaneRenderer(camera);
        vRend = new VectorRenderer(camera);

        guiRenderer = new GUIRenderer();

        boxRenderer = new BoxRenderer(camera);

        //marchingCubes = new MarchingCubes();
        //marchingCubes.march();

        mcr = new MarchingCubesRenderer(camera);
        fieldRenderer = new FieldRenderer(camera, mcr.getField());
        //guiRenderer.getGui().getModel().setTextureID(marchingCubes.getTextureID());

        //mc = new MC2();
        //fieldRenderer = new FieldRenderer(camera, mcr.getField());
        //guiRenderer.getGui().getModel().setTextureID(mc.getTextureID());

        pointRenderer = new PointRenderer(camera);

        //noise = new NoiseCompute();
        //guiRenderer.getGui().getModel().setTextureID(noise.getFBMTextureID());
    }

    @Override
    public void updateMatrices() {
        triangle.calcTrafoMat();
        camera.calcViewMatrix();
    }

    @Override
    public void render() {
        renderer.render(triangle, true);

        //planeRenderer.render();

        if(KeyCallback.isV()) vRend.render();
        //marchingCubes.march();

        if(KeyCallback.isF())
            fieldRenderer.render();
        //compShader.deploy(false);
        //boxRenderer.render();

        pointRenderer.render();

        if(KeyCallback.isG())
            guiRenderer.render();

        //mc.update();
        mcr.render();
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
