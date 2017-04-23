in float gl_FragDepth;

void main(){
    gl_FragColor = vec4(gl_FragDepth, gl_FragDepth, gl_FragDepth, 1.0);
}