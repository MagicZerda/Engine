#version 330 core

in float value;

out vec4 out_Color;

void main() {
    vec4 color = vec4(1, 0, 1, 1);
    color *= value;

    out_Color = color;
}