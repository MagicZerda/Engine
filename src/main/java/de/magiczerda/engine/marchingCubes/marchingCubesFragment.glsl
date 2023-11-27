#version 330 core

in vec3 norm;

out vec4 out_Color;

void main() {
    out_Color = vec4(norm, 1);
}