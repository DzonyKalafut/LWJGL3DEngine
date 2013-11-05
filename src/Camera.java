import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class Camera {
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix  = new Matrix4f();
	
	public Camera() {
		// Setup projection matrix
		projectionMatrix = new Matrix4f();
		float fieldOfView = 90f;
		float aspectRatio = (float) 800 / (float) 600;
		float near_plane = 0.1f;
		float far_plane = 100f;

		float y_scale = 1/(float) Math.tan(Math.toRadians(fieldOfView / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
		projectionMatrix.m33 = 0;	
		
		viewMatrix.translate(new Vector3f(0, 0, -3.1f));
		viewMatrix.rotate((float) Math.toRadians(30), new Vector3f(1, 0, 0));

	}
	
	public Matrix4f getVPMatrix() {
		viewMatrix.rotate(0.01f, new Vector3f(0, 1, 0));
		Matrix4f tmpMatrix = new Matrix4f();
		Matrix4f.mul(projectionMatrix, viewMatrix, tmpMatrix);
		return tmpMatrix;
	}
	
	
}
