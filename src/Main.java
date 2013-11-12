import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * User: jaha
 * Date: 11/12/13
 * Time: 5:04 PM
 */
public class Main {

    public static final int VIEWPORT_WIDTH = 1280;
    public static final int VIEWPORT_HEIGHT = 720;

    public static void handleInput(Camera cam) {

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            cam.getViewMatrix().translate(new Vector3f(0.1f, 0f, 0f));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            cam.getViewMatrix().translate(new Vector3f(-0.1f, 0f, 0f));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            cam.getViewMatrix().translate(new Vector3f(0f, -0.1f, 0f));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            cam.getViewMatrix().translate(new Vector3f(0f, 0.1f, 0f));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            cam.getViewMatrix().translate(new Vector3f(0f, 0f, 0.2f));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            cam.getViewMatrix().translate(new Vector3f(0f, 0f, -0.2f));
        }

        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            cam.getViewMatrix().translate(new Vector3f(0f, 0f, -0.2f));
        } else if (dWheel > 0){
            cam.getViewMatrix().translate(new Vector3f(0f, 0f, 0.2f));
        }

        if (Mouse.isButtonDown(0)) {
                cam.getViewMatrix().translate(new Vector3f(Mouse.getDX()*0.01f, Mouse.getDY()*0.01f, 0f));
        }
//        else if (Mouse.isButtonDown(1)) {
//        }
    }

    public static void main(String[] args) {
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(2, 0);
            Display.setTitle("LWJGL Test");
            Display.setDisplayMode(new DisplayMode(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
            Display.create(pixelFormat, contextAtrributes);
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(1);
        }

        Camera cam = new Camera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        GLRenderer glRenderer = new GLRenderer(cam);
        Texture tex = new Texture("/home/jaha/workspace/lwjgl test/data/spaceship_color.png");
        ShaderProgram basicShaderProgram = new ShaderProgram("/home/jaha/workspace/lwjgl test/src/shaders/basic.vert", "/home/jaha/workspace/lwjgl test/src/shaders/basic.frag");
        ArrayList<ModelInstance> modelInstances = new ArrayList<>();

        try {
            Model ship = ObjLoader.loadModel(new File("/home/jaha/workspace/lwjgl test/data/gravity_ship.obj"));
            ship.generateGPUBuffers();
            modelInstances.add(new ModelInstance(ship, tex, basicShaderProgram));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        glRenderer.initialize();

        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

//            modelInstances.get(0).getModelMatrix().translate(new Vector3f(0.01f, 0f, 0f));

            glRenderer.render(modelInstances);

            Display.update();
            Display.sync(60);

            handleInput(cam);
        }

        Display.destroy();
    }
}
