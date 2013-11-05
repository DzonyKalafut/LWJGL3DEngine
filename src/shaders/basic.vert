uniform mat4 u_MVPMatrix;

attribute vec3 position;
attribute vec3 normal;
//attribute vec4 color;
attribute vec2 texCoords;

varying vec3 vNormal;
//varying vec4 vColor;
varying vec2 vTexCoords;

void main() {
	vNormal = normal;
	//vColor = color;
	vTexCoords = texCoords;
	gl_Position = u_MVPMatrix * vec4(position, 1);
}