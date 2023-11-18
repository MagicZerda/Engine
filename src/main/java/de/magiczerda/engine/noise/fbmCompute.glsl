#version 430 core

layout (local_size_x = 1, local_size_y = 1, local_size_z = 1) in;

layout (rgba32f, binding = 0) uniform image2D outTexture;

uniform vec2 coordinates;

vec2 fade(vec2 t) {return t*t*t*(t*(t*6.0-15.0)+10.0);}
vec4 permute(vec4 x){return mod(((x*34.0)+1.0)*x, 289.0);}
float cnoise(vec2 P, float hd){
    vec2 pt = P*hd;
    pt += coordinates/1000000.0;

    vec4 Pi = floor(pt.xyxy) + vec4(0.0, 0.0, 1.0, 1.0);
    vec4 Pf = fract(pt.xyxy) - vec4(0.0, 0.0, 1.0, 1.0);
    Pi = mod(Pi, 289.0); // To avoid truncation effects in permutation
    vec4 ix = Pi.xzxz;
    vec4 iy = Pi.yyww;
    vec4 fx = Pf.xzxz;
    vec4 fy = Pf.yyww;
    vec4 i = permute(permute(ix) + iy);
    vec4 gx = 2.0 * fract(i * 0.0243902439) - 1.0; // 1/41 = 0.024...
    vec4 gy = abs(gx) - 0.5;
    vec4 tx = floor(gx + 0.5);
    gx = gx - tx;
    vec2 g00 = vec2(gx.x,gy.x);
    vec2 g10 = vec2(gx.y,gy.y);
    vec2 g01 = vec2(gx.z,gy.z);
    vec2 g11 = vec2(gx.w,gy.w);
    vec4 norm = 1.79284291400159 - 0.85373472095314 *
    vec4(dot(g00, g00), dot(g01, g01), dot(g10, g10), dot(g11, g11));
    g00 *= norm.x;
    g01 *= norm.y;
    g10 *= norm.z;
    g11 *= norm.w;
    float n00 = dot(g00, vec2(fx.x, fy.x));
    float n10 = dot(g10, vec2(fx.y, fy.y));
    float n01 = dot(g01, vec2(fx.z, fy.z));
    float n11 = dot(g11, vec2(fx.w, fy.w));
    vec2 fade_xy = fade(Pf.xy);
    vec2 n_x = mix(vec2(n00, n01), vec2(n10, n11), fade_xy.x);
    float n_xy = mix(n_x.x, n_x.y, fade_xy.y);
    return 2.3 * n_xy;
}

float fbm(vec2 p, float hd) {
    float cn = cnoise(p, hd*0.02)*15;
    cn += cnoise(p, hd*0.05)*10;
    cn += cnoise(p, hd*0.08)*3;
    cn += cnoise(p, hd*0.125)*2;
    cn += cnoise(p, hd*0.25);
    cn += cnoise(p, hd*0.5) * 0.025;
    cn += cnoise(p, hd) * 0.005;
    cn += cnoise(p, hd*5) * 0.001;
    return cn;
}


void main(void) {
    ivec2 coord = ivec2(gl_GlobalInvocationID.xy);
    vec4 col = vec4(0.0, 0.0, 0.0, 1);

    vec2 coords = vec2(coord.x, coord.y);

    col.r += fbm(coords, 1);
    col.r += fbm(coords + vec2(-0.2, 0.5), 1);
    col.r += fbm(coords + vec2(0.1, 0.21), 2);

    col.g += fbm(coords, 1);
    col.g += fbm(coords + vec2(0.3, -0.035), 0.3);
    col.g += fbm(coords + vec2(0.1254, 0.05), 0.1);

    col.b += fbm(coords, 0.5);
    col.b += fbm(coords + vec2(0.174, -0.152), 0.03);
    col.b += fbm(coords + vec2(-0.463, 0.15), 0.001);
    //col.x += fbm(coords + vec2(0.05, 0.1), 20);
    //col.y = fbm(coords + vec2(bd.data[0].x, bd.data[0].y), 1);
    //col.z = fbm(coords + vec2(bd.data[1].x, bd.data[1].y), 1);
    //col = vec4(data[px.x]);

    //col = bd.data[0];

    imageStore(outTexture, coord, col);
}