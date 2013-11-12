import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;


public class GLVbo {

    public static int createVBOID() {
        return GL15.glGenBuffers(); //Which can only supply you with a single id.
    }

    public static void vertexBufferData(int id, FloatBuffer buffer) { //Not restricted to FloatBuffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id); //Bind buffer (also specifies type of buffer)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Send up the data and specify usage hint.
    }

    public static FloatBuffer generateVertexBuffer(Model model) {
        float[] vertices = new float[model.getFaces().size() * 9];
        int i = 0;
        for (Face face : model.getFaces()) {
            Vector3f vertex = model.getVertices().get((int) (face.vertices.x - 1));
            vertices[i] = vertex.x;
            vertices[i + 1] = vertex.y;
            vertices[i + 2] = vertex.z;
            vertex = model.getVertices().get((int) (face.vertices.y - 1));
            vertices[i + 3] = vertex.x;
            vertices[i + 4] = vertex.y;
            vertices[i + 5] = vertex.z;
            vertex = model.getVertices().get((int) (face.vertices.z - 1));
            vertices[i + 6] = vertex.x;
            vertices[i + 7] = vertex.y;
            vertices[i + 8] = vertex.z;
            i = i + 9;
        }

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        return verticesBuffer;
    }

    public static FloatBuffer generateNormalsBuffer(Model model) {
        float[] normals = new float[model.getFaces().size() * 9];
        int i = 0;
        for (Face face : model.getFaces()) {
            Vector3f vertex = model.getNormals().get((int) (face.normals.x - 1));
            normals[i] = vertex.x;
            normals[i + 1] = vertex.y;
            normals[i + 2] = vertex.z;
            vertex = model.getNormals().get((int) (face.normals.y - 1));
            normals[i + 3] = vertex.x;
            normals[i + 4] = vertex.y;
            normals[i + 5] = vertex.z;
            vertex = model.getNormals().get((int) (face.normals.z - 1));
            normals[i + 6] = vertex.x;
            normals[i + 7] = vertex.y;
            normals[i + 8] = vertex.z;
            i = i + 9;
        }

        FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
        normalsBuffer.put(normals);
        normalsBuffer.flip();

        return normalsBuffer;
    }

    public static FloatBuffer generateUVCoordsBuffer(Model model) {
        float[] texCoords = new float[model.getFaces().size() * 6];

        int i = 0;
        if (model.getTexCoords().size() > 0) {
            for (Face face : model.getFaces()) {
                Vector2f vertex = model.getTexCoords().get((int) (face.texCoords.x - 1));
                texCoords[i] = vertex.x;
                texCoords[i + 1] = vertex.y;
                vertex = model.getTexCoords().get((int) (face.texCoords.y - 1));
                texCoords[i + 2] = vertex.x;
                texCoords[i + 3] = vertex.y;
                vertex = model.getTexCoords().get((int) (face.texCoords.z - 1));
                texCoords[i + 4] = vertex.x;
                texCoords[i + 5] = vertex.y;
                i = i + 6;
            }
        }

        FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
        texCoordsBuffer.put(texCoords);
        texCoordsBuffer.flip();

        return texCoordsBuffer;

    }

}
