#version 400

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexUV;
attribute vec2 aTileUV;

uniform vec4 uSunDir;
uniform float uTileSize;
uniform mat4 uViewProjectionMatrix;

out vec3 lightingIntensity;
out vec2 tiledTexUV;
out vec2 tiledBaseTexUV;

vec3 ambient = vec3(0.5, 0.5, 0.5); //Ambient color;;

void main()
{
    tiledTexUV = aTileUV * uTileSize;
    tiledBaseTexUV = aTexUV * uTileSize;

	lightingIntensity = max(dot(aNormal.xyz,-uSunDir.xyz), 0.0) + ambient;

	gl_Position = uViewProjectionMatrix * vec4(aPosition, 1.0);
}