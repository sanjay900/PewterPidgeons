
in vec2 fragCoord;
in float fragDepth;
out vec4 gl_FragColor;

void mainImage(){
    gl_FragColor = vec4(fragCoord, fragDepth, 1.0);
}