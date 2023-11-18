#version 430 core

layout (local_size_x = 1, local_size_y = 1, local_size_z = 1) in;

layout (rgba32f, binding = 0) uniform image2D outTexture;


uniform float randomSeed;

uint hash(uint x) {
    x += (x << 10u);
    x ^= (x >> 6u);
    x += (x << 3u);
    x ^= (x >> 11u);
    x += (x << 15u);
    return x;
}

uint hash(uvec3 v) {
    return hash(v.x ^ hash(v.y) ^ hash(v.z));
}

float constrFloat(uint m) {
    const uint im = 0x007FFFFFu;
    const uint io = 0x3F800000u;
    m &= im;
    m |= io;
    return uintBitsToFloat(m) - 1.0;
}

float random() {
    vec3 inp = vec3(gl_GlobalInvocationID.x, gl_GlobalInvocationID.y, randomSeed);
    return constrFloat(hash(floatBitsToUint(inp)));
}


void main () {
    ivec2 texelCoords = ivec2(gl_GlobalInvocationID.xy);

    vec4 color = vec4(0, 0, 0, 1);
    color.r = random();
    color.g = random();
    color.b = random();



    imageStore(outTexture, texelCoords, color);

}