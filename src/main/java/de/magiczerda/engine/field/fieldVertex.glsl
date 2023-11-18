#version 400 core

layout (location = 0) in vec4 fieldVertex;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

out float value;

void main() {
    value = fieldVertex.w;
    if(value < 0.4 || value > 0.6)
            gl_PointSize = 5;
    else gl_PointSize = 15;

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(fieldVertex.xyz, 1);
}