//load SP_SOURCE_BASIC_LIGHT_VERTEX_SHADER
#version 400

attribute vec3 aPosition;
attribute vec3 aNormal;

uniform vec4 sunPosition;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

out vec3 lightingIntensity;

vec3 ambient = vec3(0.05, 0.05, 0.05); //Ambient color;;

void main()
{
	vec4 eyeCoords = viewMatrix * vec4(aPosition, 1.0);
	vec3 dir = normalize(vec3(sunPosition - eyeCoords));

	lightingIntensity = (max(dot(dir, aNormal), 0.0)) + ambient;

	gl_Position = projectionMatrix * eyeCoords;
}