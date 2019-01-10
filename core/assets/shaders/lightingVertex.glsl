//load SP_SOURCE_BASIC_LIGHT_VERTEX_SHADER
#version 400

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexUV;
attribute vec2 aTileUV;

uniform vec4 uSunDir;
uniform float uTileSize;
uniform mat4 uViewProjMatrix;

out vec3 lightingIntensity;
out vec2 tiledTexUV;
out vec2 tiledBaseTexUV;

vec3 ambient = vec3(0.5, 0.5, 0.5); //Ambient color;;

void main()
{
    tiledTexUV = tileUV * tileSize;
    tiledBaseTexUV = texUV * tileSize;

	lightingIntensity = max(dot(normal.xyz,-sunDir.xyz), 0.0) + ambient;

	gl_Position = viewProjMatrix * vec4(position, 1.0);
}