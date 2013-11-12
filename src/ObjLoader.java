import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;


public class ObjLoader {
    public static Model loadModel(File f) throws IOError, NumberFormatException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model model = new Model();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                float z = Float.valueOf(line.split(" ")[3]);
                model.getVertices().add(new Vector3f(x, y, z));
            } else if (line.startsWith("vt ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = 1 - Float.valueOf(line.split(" ")[2]);
                model.getTexCoords().add(new Vector2f(x, y));
            } else if (line.startsWith("vn ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                float z = Float.valueOf(line.split(" ")[3]);
                model.getNormals().add(new Vector3f(x, y, z));
            } else if (line.startsWith("f ")) {
                Vector3f vertexIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[0]),
                        Float.valueOf(line.split(" ")[2].split("/")[0]),
                        Float.valueOf(line.split(" ")[3].split("/")[0]));
                Vector3f texCoordIndices = new Vector3f(1, 1, 1);
                if (!line.split(" ")[1].split("/")[1].equals("")) {
//						System.out.println(line.split(" ")[1].split("/")[1]);
                    texCoordIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[1]),
                            Float.valueOf(line.split(" ")[2].split("/")[1]),
                            Float.valueOf(line.split(" ")[3].split("/")[1]));
                }
                Vector3f normalIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[2]),
                        Float.valueOf(line.split(" ")[2].split("/")[2]),
                        Float.valueOf(line.split(" ")[3].split("/")[2]));
                model.getFaces().add(new Face(vertexIndices, texCoordIndices, normalIndices));
            }
        }
        model.setNumOfModelVerts(model.getFaces().size() * 3);
        return model;
    }
}
