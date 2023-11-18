#version 330 core

layout (points) in;
layout (line_strip, max_vertices = 5) out;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

//in vec3[] vectorDest;
in vec3[] vecDir;

out float distAlongVector;

void main() {
        mat4 vpMat = projectionMatrix * viewMatrix;
        distAlongVector = 0;
        gl_Position = vpMat *  gl_in[0].gl_Position;
        EmitVertex();

        distAlongVector = 1;
        gl_Position =  vpMat * (gl_in[0].gl_Position + vec4(vecDir[0], 0));
        EmitVertex();

        EndPrimitive();
}