#version 330 core

in vec3 normal;

out vec4 out_Color;

void main() {
    out_Color = vec4(normal, 1);
}