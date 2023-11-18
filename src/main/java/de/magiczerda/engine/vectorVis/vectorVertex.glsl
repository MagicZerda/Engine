#version 330 core

layout (location = 0) in vec3 vertex;

uniform vec3 vectorOrigin;
uniform vec3 vectorDirection;

//out vec3 vectorDest;
out vec3 vecDir;


void main() {

    vecDir = vectorDirection;

    //vectorDest = vectorDestination - (vertex + vectorOrigin);
    gl_Position = vec4(vertex + vectorOrigin, 1);

}