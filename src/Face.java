import org.lwjgl.util.vector.Vector3f;


public class Face {
    public Vector3f vertices;
    public Vector3f normals;
    public Vector3f texCoords;

    public Face(Vector3f vertices, Vector3f texCoords, Vector3f normals) {
        this.vertices = vertices;
        this.normals = normals;
        this.texCoords = texCoords;
    }
}
