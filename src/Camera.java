import org.lwjgl.util.vector.Matrix4f;


public class Camera {
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public Camera(int viewportWidth, int viewportHeight) {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        float fieldOfView = 90f;
        float aspectRatio = (float) viewportWidth / (float) viewportHeight;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = 1 / (float) Math.tan(Math.toRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getVPMatrix() {
        Matrix4f tmpMatrix = new Matrix4f();
        Matrix4f.mul(projectionMatrix, viewMatrix, tmpMatrix);
        return tmpMatrix;
    }


}
