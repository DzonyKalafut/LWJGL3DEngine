import org.lwjgl.util.vector.Vector3f;


public class Light {
    Vector3f position;
    Vector3f color;
    float intensity;

    public Light(Vector3f position, Vector3f color, float intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }
}
