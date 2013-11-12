import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class GLRenderer {
    Camera camera;

    public GLRenderer(Camera camera) {
        this.camera = camera;
    }

    public void initialize() {
        GL11.glClearColor(0.176470588f, 0.176470588f, 0.176470588f, 1f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void render(ArrayList<ModelInstance> instancesToRender) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // render individual model instances
        for (ModelInstance instance : instancesToRender) {

            GL20.glUseProgram(instance.getShaderID());

            int attribLocation = GL20.glGetAttribLocation(instance.getShaderID(), "position");
            GL20.glEnableVertexAttribArray(attribLocation);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, instance.getModel().getVertexBufferID());
            GL20.glVertexAttribPointer(attribLocation, 3, GL11.GL_FLOAT, false, 0, 0);

            attribLocation = GL20.glGetAttribLocation(instance.getShaderID(), "normal");
            GL20.glEnableVertexAttribArray(attribLocation);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, instance.getModel().getNormalBufferID());
            GL20.glVertexAttribPointer(attribLocation, 3, GL11.GL_FLOAT, false, 0, 0);

//	      attribLocation = GL20.glGetAttribLocation(basicShaderProgram.getProgramId(), "color");
//	      GL20.glEnableVertexAttribArray(attribLocation);
//	      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colourBufferID);
//	      GL20.glVertexAttribPointer(attribLocation, 4, GL11.GL_FLOAT, false, 0, 0);
//
            attribLocation = GL20.glGetAttribLocation(instance.getShaderID(), "texCoords");
            GL20.glEnableVertexAttribArray(attribLocation);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, instance.getModel().getUvCoordsBufferID());
            GL20.glVertexAttribPointer(attribLocation, 2, GL11.GL_FLOAT, false, 0, 0);

            Matrix4f tmpMatrix = new Matrix4f();
            Matrix4f.mul(instance.getModelMatrix(), camera.getVPMatrix(), tmpMatrix);
            FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
            tmpMatrix.store(matrixBuffer);
            matrixBuffer.flip();

            int uniformId = 0;
            GL20.glGetUniformLocation(uniformId, "u_MVPMatrix");
            GL20.glUniformMatrix4(uniformId, false, matrixBuffer);

            instance.getColorTexture().bind();

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, instance.getModel().getNumOfModelVerts() * 3);
        }

    }

}
