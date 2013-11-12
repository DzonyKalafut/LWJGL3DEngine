import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Model {
    private List<Vector3f> vertices = new ArrayList<>();
    private List<Vector3f> normals = new ArrayList<>();
    private List<Vector2f> texCoords = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();
    private int vertexBufferID;
    private int normalBufferID;
    private int colourBufferID;
    private int uvCoordsBufferID;
    private int numOfModelVerts;

    public void generateGPUBuffers() {
        vertexBufferID = GLVbo.createVBOID();
        GLVbo.vertexBufferData(vertexBufferID, GLVbo.generateVertexBuffer(this));
        uvCoordsBufferID = GLVbo.createVBOID();
        GLVbo.vertexBufferData(uvCoordsBufferID, GLVbo.generateUVCoordsBuffer(this));
        normalBufferID = GLVbo.createVBOID();
        GLVbo.vertexBufferData(normalBufferID, GLVbo.generateNormalsBuffer(this));
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Vector2f> getTexCoords() {
        return texCoords;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public int getVertexBufferID() {
        return vertexBufferID;
    }

    public void setVertexBufferID(int vertexBufferID) {
        this.vertexBufferID = vertexBufferID;
    }

    public int getNormalBufferID() {
        return normalBufferID;
    }

    public void setNormalBufferID(int normalBufferID) {
        this.normalBufferID = normalBufferID;
    }

    public int getColourBufferID() {
        return colourBufferID;
    }

    public void setColourBufferID(int colourBufferID) {
        this.colourBufferID = colourBufferID;
    }

    public int getUvCoordsBufferID() {
        return uvCoordsBufferID;
    }

    public void setUvCoordsBufferID(int uvCoordsBufferID) {
        this.uvCoordsBufferID = uvCoordsBufferID;
    }

    public int getNumOfModelVerts() {
        return numOfModelVerts;
    }

    public void setNumOfModelVerts(int numOfModelVerts) {
        this.numOfModelVerts = numOfModelVerts;
    }

}