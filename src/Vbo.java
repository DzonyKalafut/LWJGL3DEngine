import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class Vbo {
	
	public static int vertexBufferID ;
	public static int normalBufferID;
	public static int colourBufferID;
	public static int uvCoordsBufferID;
	
	public static int numOfModelVerts;
	
	public static float lastFPS;
	public static int fps;
	
	public static int createVBOID() {
	    return GL15.glGenBuffers(); //Which can only supply you with a single id.
	}
	
	public static void vertexBufferData(int id, FloatBuffer buffer) { //Not restricted to FloatBuffer
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id); //Bind buffer (also specifies type of buffer)
	    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Send up the data and specify usage hint.
	}
	
	public static void render() {
//	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	    GL20.glEnableVertexAttribArray(0);
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferID);
//	    GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
	    
//	    
//	    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
//	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colourBufferID);
//	    GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
	    
	    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
	}
	
	public static FloatBuffer generateVertexBuffer(Model model) {
		float[] vertices = new float[model.faces.size() * 9];
		int i = 0;
		for (Face face : model.faces) {
			Vector3f vertex = model.vertices.get((int) (face.vertices.x - 1));
			vertices[i] = vertex.x;
			vertices[i+1] = vertex.y;
			vertices[i+2] = vertex.z;
			vertex = model.vertices.get((int) (face.vertices.y - 1));
			vertices[i+3] = vertex.x;
			vertices[i+4] = vertex.y;
			vertices[i+5] = vertex.z;
			vertex = model.vertices.get((int) (face.vertices.z - 1));
			vertices[i+6] = vertex.x;
			vertices[i+7] = vertex.y;
			vertices[i+8] = vertex.z;
			i = i + 9;
		}
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		
		return verticesBuffer;		 
	}
	
	public static FloatBuffer generateNormalsBuffer(Model model) {
		float[] normals = new float[model.faces.size() * 9];
		int i = 0;
		for (Face face : model.faces) {
			Vector3f vertex = model.normals.get((int) (face.normals.x - 1));
			normals[i] = vertex.x;
			normals[i+1] = vertex.y;
			normals[i+2] = vertex.z;
			vertex = model.normals.get((int) (face.normals.y - 1));
			normals[i+3] = vertex.x;
			normals[i+4] = vertex.y;
			normals[i+5] = vertex.z;
			vertex = model.normals.get((int) (face.normals.z - 1));
			normals[i+6] = vertex.x;
			normals[i+7] = vertex.y;
			normals[i+8] = vertex.z;
			i = i + 9;
		}
		
		FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
		normalsBuffer.put(normals);
		normalsBuffer.flip();
		
		return normalsBuffer;		 
	}
	
	public static FloatBuffer generateUVCoordsBuffer(Model model) {
		float[] texCoords = new float[model.faces.size() * 6];
		
		int i = 0;
		if (model.texCoords.size() > 0) {
			for (Face face : model.faces) {
				Vector2f vertex = model.texCoords.get((int) (face.texCoords.x - 1));
				texCoords[i] = vertex.x;
				texCoords[i+1] = vertex.y;
				vertex = model.texCoords.get((int) (face.texCoords.y - 1));
				texCoords[i+2] = vertex.x;
				texCoords[i+3] = vertex.y;
				vertex = model.texCoords.get((int) (face.texCoords.z - 1));
				texCoords[i+4] = vertex.x;
				texCoords[i+5] = vertex.y;
				i = i + 6;
			} 
		}
		
		FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
		texCoordsBuffer.put(texCoords);
		texCoordsBuffer.flip();
		
		return texCoordsBuffer;
		 
	}
	
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static void updateFPS() {
	    if (getTime() - lastFPS > 1000) {
	        Display.setTitle("FPS: " + fps); 
	        fps = 0; //reset the FPS counter
	        lastFPS += 1000; //add one second
	    }
	    fps++;
	}
	
	public static void main(String[] args) {
		try {
			Display.setTitle("LWJGL Test");
		    Display.setDisplayMode(new DisplayMode(800, 600));
		    Display.create();
		} 
		catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}
		
		ShaderProgram basicShaderProgram = new ShaderProgram("/home/jaha/workspace/lwjgl test/src/shaders/basic.vert", "/home/jaha/workspace/lwjgl test/src/shaders/basic.frag");
				
		GL11.glClearColor(0.176470588f, 0.176470588f, 0.176470588f, 1f);

	    GL11.glEnable(GL11.GL_DEPTH_TEST);
	    
	    GL11.glEnable(GL11.GL_CULL_FACE);
	    
	    GL11.glCullFace(GL11.GL_BACK);
	    
	    Random rnd = new Random();
	    
	    try {
			Model model = ObjLoader.loadModel(new File("/home/jaha/workspace/lwjgl test/data/gravity_ship.obj"));
			numOfModelVerts = model.faces.size() * 3;
			System.out.println(numOfModelVerts);
			System.out.println(model.vertices.size());
			System.out.println(model.faces.size());
			vertexBufferID = createVBOID();
		    vertexBufferData(vertexBufferID, generateVertexBuffer(model));
		    uvCoordsBufferID = createVBOID();
		    vertexBufferData(uvCoordsBufferID, generateUVCoordsBuffer(model));
			normalBufferID = createVBOID();
		    vertexBufferData(normalBufferID, generateNormalsBuffer(model));
		} catch (NumberFormatException | IOError | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
//	    colourBufferID = createVBOID();
//	    vertexBufferData(colourBufferID, generateTriangleColorBuffer());
	    
	    Texture tex = new Texture("/home/jaha/workspace/lwjgl test/data/spaceship_color.png");
	    
	    Camera cam = new Camera();
	    
	    while (!Display.isCloseRequested()) {

	      // clear the display
	      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	      
	      GL20.glUseProgram(basicShaderProgram.getProgramId());
	      
//	      render();
	      
	      int attribLocation = GL20.glGetAttribLocation(basicShaderProgram.getProgramId(), "position");
	      GL20.glEnableVertexAttribArray(attribLocation);
	      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferID);
	      GL20.glVertexAttribPointer(attribLocation, 3, GL11.GL_FLOAT, false, 0, 0);
	      
	      attribLocation = GL20.glGetAttribLocation(basicShaderProgram.getProgramId(), "normal");
	      GL20.glEnableVertexAttribArray(attribLocation);
	      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferID);
	      GL20.glVertexAttribPointer(attribLocation, 3, GL11.GL_FLOAT, false, 0, 0);
	      
//	      attribLocation = GL20.glGetAttribLocation(basicShaderProgram.getProgramId(), "color");
//	      GL20.glEnableVertexAttribArray(attribLocation);
//	      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colourBufferID);
//	      GL20.glVertexAttribPointer(attribLocation, 4, GL11.GL_FLOAT, false, 0, 0);
//	      
	      attribLocation = GL20.glGetAttribLocation(basicShaderProgram.getProgramId(), "texCoords");
	      GL20.glEnableVertexAttribArray(attribLocation);
	      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvCoordsBufferID);
	      GL20.glVertexAttribPointer(attribLocation, 2, GL11.GL_FLOAT, false, 0, 0);
	      
	      int uniformId = 0;
	      GL20.glGetUniformLocation(uniformId, "u_MVPMatrix");
	      FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	      cam.getVPMatrix().store(matrixBuffer);
	      matrixBuffer.flip();
	      
	      GL20.glUniformMatrix4(uniformId, false, matrixBuffer);	  
	      
	      tex.bind();
	    	      
	      GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, numOfModelVerts * 3);
	      
	      // update the display
	      Display.update();
	      
	      updateFPS();
	    }

	    // clean things up
	    Display.destroy();
	 
	}
}
