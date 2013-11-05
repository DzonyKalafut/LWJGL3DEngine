uniform sampler2D texture0;

varying vec3 vNormal;
//varying vec4 vColor;
varying vec2 vTexCoords;

void main() {
	gl_FragColor = texture2D(texture0, vTexCoords);
	//gl_FragColor = vec4(vNormal, 1);
}