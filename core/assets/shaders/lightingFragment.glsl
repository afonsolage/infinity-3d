#ifdef GL_ES
#precision mediump float;
#endif

uniform sampler2D uTexAtlas;
uniform float uTileSize;

in vec2 tiledTexUV;
in vec2 tiledBaseTexUV;
in vec3 lightingIntensity;

void main()
{
    vec2 uv = mod(tiledBaseTexUV, uTileSize);
    gl_FragColor = texture2D(uTexAtlas, tiledTexUV + uv) * vec4(lightingIntensity, 1.0);
}