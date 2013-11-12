import org.lwjgl.util.vector.Matrix4f;

/**
 * User: jaha
 * Date: 11/12/13
 * Time: 5:36 PM
 */
public class ModelInstance {

    private Model model;
    private Texture colorTexture;
    private ShaderProgram shader;
    private Matrix4f modelMatrix;

    public ModelInstance(Model model, Texture colorTexture, ShaderProgram shader) {
        this.model = model;
        this.colorTexture = colorTexture;
        this.shader = shader;
        this.modelMatrix = new Matrix4f();
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public Model getModel() {
        return model;
    }

    public int getShaderID() {
        return shader.getProgramId();
    }

    public void setShader(ShaderProgram shader) {
        this.shader = shader;
    }

    public Texture getColorTexture() {
        return colorTexture;
    }

    public void setColorTexture(Texture colorTexture) {
        this.colorTexture = colorTexture;
    }
}
