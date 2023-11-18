#version 330 core

in float value;

out vec4 out_Color;


void main() {
    vec4 zeroColor = vec4(0, 1, 0, 1);
    vec4 oneColor = vec4(1, 0, 0, 1);

    out_Color = mix(zeroColor, oneColor, value);
}