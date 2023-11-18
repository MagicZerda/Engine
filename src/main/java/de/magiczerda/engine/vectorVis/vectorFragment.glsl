#version 330 core

in float distAlongVector;

out vec4 out_Color;

void main() {

    out_Color = vec4(1 - distAlongVector, 0, distAlongVector, 1);

}