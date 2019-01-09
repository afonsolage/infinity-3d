//load SP_SOURCE_BASIC_LIGHT_VERTEX_SHADER
#version 400

attribute vec3 aPosition;
attribute vec3 aNormal;

uniform vec4 sunDir;

uniform mat4 viewProjMatrix;

out vec3 lightingIntensity;

vec3 ambient = vec3(0.05, 0.05, 0.05); //Ambient color;;

void main()
{
	vec3 L = normalize(-sunDir.xyz);
	vec3 N = normalize(aNormal.xyz);

	lightingIntensity = max(dot(N,L), 0.0) + ambient;

	gl_Position = viewProjMatrix * vec4(aPosition, 1.0);
}