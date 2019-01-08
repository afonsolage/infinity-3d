#ifdef GL_ES
#precision mediump float;
#endif

in vec3 lightingIntensity;

void main()
{
    gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0) * vec4(lightingIntensity, 1.0);
}