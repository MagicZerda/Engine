#version 400 core

layout (location = 0) in vec3 pointVertex;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

//out float value;

void main() {
    /*value = pointVertex.w;
    if(value > 4) gl_PointSize = 20;
    else gl_PointSize = 10;*/

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(pointVertex, 1);
}