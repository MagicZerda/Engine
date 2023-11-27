#version 330 core

layout (location = 0) in vec3 vertex;
layout (location = 2) in vec3 normal;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

out vec3 norm;

void main() {
    norm = normalize(normal);

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(vertex, 1);
}