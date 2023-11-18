#version 400 core

layout (location = 0) in vec4 pointVertex;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

out float value;

void main() {
    value = pointVertex.w;

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(pointVertex.xyz, 1);
}