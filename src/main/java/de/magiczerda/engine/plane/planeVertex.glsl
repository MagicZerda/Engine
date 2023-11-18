#version 330 core

layout (location = 0) in vec3 vertexPos;
layout (location = 2) in vec3 vertexNormal;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

out vec3 normal;

void main() {

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(vertexPos, 1);
    normal = normalize(vertexNormal);
}