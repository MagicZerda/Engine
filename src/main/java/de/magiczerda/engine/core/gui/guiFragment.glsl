#version 330 core

in vec2 pass_textureCoords;

uniform sampler2D textureSampler;

out vec4 out_Color;


void main() {
    out_Color = texture(textureSampler, pass_textureCoords);//vec4(1, 1, 0, 1);
}