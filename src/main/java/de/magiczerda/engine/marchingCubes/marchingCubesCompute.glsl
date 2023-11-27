#version 450 core

layout (local_size_x = 9, local_size_y = 9, local_size_z = 9) in; /* 9*9*9 cubes */

//layout (rgba32f, binding = 3) uniform image2D outTexture;

layout (std430, binding = 0) readonly buffer bufferInData {
    float fieldData[];
} bufferIn;

layout (std430, binding = 1) writeonly buffer bufferOutData {
    float outData[];
} bufferOut;


layout (std430, binding = 2) writeonly buffer bufferOutNormal {
    float normData[];
} normalOut;

layout (std430, binding = 3) writeonly buffer bufferOutDebug {
    float data[];
} testOut;


uniform vec3 segSize;


uniform vec3 worldRay;

uniform float isolevel;

const float ds = 0.00000001;
const float dp = 1;


vec3 interpolate(vec4 v1, vec4 v2) {
    if(abs(v1.w - isolevel) < ds) return v1.xyz;
    if(abs(v2.w - isolevel) < ds) return v2.xyz;
    if(abs(v2.w - v1.w) < ds) return v1.xyz;

    float factor = (isolevel - v1.w) / (v2.w - v1.w);
    //float factor = 0.5;
    return v1.xyz + factor * (v2.xyz - v1.xyz);
}


vec3[6] polygonize(vec4[8] cubeGrid, int v0, int v1, int v2, int v3) {
    int tri = 0;
    uint triindex = 0;

    if(cubeGrid[v0].w < isolevel) triindex |= 1;
    if(cubeGrid[v1].w < isolevel) triindex |= 2;
    if(cubeGrid[v2].w < isolevel) triindex |= 4;
    if(cubeGrid[v3].w < isolevel) triindex |= 8;


    vec3[] triangles = {
        vec3(0,0,0),   vec3(0,0,0),   vec3(0,0,0),
        vec3(0,0,0),   vec3(0,0,0),   vec3(0,0,0)
    };

    //if(triindex != 0x08) return triangles;

    switch(triindex) {
        case 0x00:
        case 0x0F:
            break;
        case 0x0E:
        case 0x01:
            triangles[0] = interpolate(cubeGrid[v0], cubeGrid[v1]);
            triangles[1] = interpolate(cubeGrid[v0], cubeGrid[v2]);
            triangles[2] = interpolate(cubeGrid[v0], cubeGrid[v3]);
            tri++;
            break;
        case 0x0D:
        case 0x02:
            triangles[0] = interpolate(cubeGrid[v1], cubeGrid[v0]);
            triangles[1] = interpolate(cubeGrid[v1], cubeGrid[v3]);
            triangles[2] = interpolate(cubeGrid[v1], cubeGrid[v2]);
            tri++;
            break;
        case 0x0C:
        case 0x03:
            triangles[0] = interpolate(cubeGrid[v0], cubeGrid[v3]);
            triangles[1] = interpolate(cubeGrid[v0], cubeGrid[v2]);
            triangles[2] = interpolate(cubeGrid[v1], cubeGrid[v3]);
            tri++;
            triangles[3] = triangles[2];
            triangles[4] = interpolate(cubeGrid[v1], cubeGrid[v2]);
            triangles[5] = triangles[1];
            tri++;
            break;
        case 0x0B:
        case 0x04:
            triangles[0] = interpolate(cubeGrid[v2], cubeGrid[v0]);
            triangles[1] = interpolate(cubeGrid[v2], cubeGrid[v1]);
            triangles[2] = interpolate(cubeGrid[v2], cubeGrid[v3]);
            tri++;
            break;
        case 0x0A:
        case 0x05:
            triangles[0] = interpolate(cubeGrid[v0], cubeGrid[v1]);
            triangles[1] = interpolate(cubeGrid[v2], cubeGrid[v3]);
            triangles[2] = interpolate(cubeGrid[v0], cubeGrid[v3]);
            tri++;
            triangles[3] = triangles[0];
            triangles[4] = interpolate(cubeGrid[v1], cubeGrid[v2]);
            triangles[5] = triangles[1];
            tri++;
            break;
        case 0x09:
        case 0x06:
            triangles[0] = interpolate(cubeGrid[v0], cubeGrid[v1]);
            triangles[1] = interpolate(cubeGrid[v1], cubeGrid[v3]);
            triangles[2] = interpolate(cubeGrid[v2], cubeGrid[v3]);
            tri++;
            triangles[3] = triangles[0];
            triangles[4] = interpolate(cubeGrid[v0], cubeGrid[v2]);
            triangles[5] = triangles[2];
            tri++;
            break;
        case 0x07:
        case 0x08:
            triangles[0] = interpolate(cubeGrid[v3], cubeGrid[v0]);
            triangles[1] = interpolate(cubeGrid[v3], cubeGrid[v2]);
            triangles[2] = interpolate(cubeGrid[v3], cubeGrid[v1]);
            tri++;
            break;
    }


    return triangles;
}


void sendVertex(vec3 vertex, int id) {
    bufferOut.outData[3*id]   = vertex.x;
    bufferOut.outData[3*id+1] = vertex.y;
    bufferOut.outData[3*id+2] = vertex.z;
}

void sendNormal(vec3 normal, int id) {
    normalOut.normData[3*id]   = normal.x;
    normalOut.normData[3*id+1] = normal.y;
    normalOut.normData[3*id+2] = normal.z;
}

void calcNormal(vec3[6] tt, int subj, int n1, int n2, int nID) {
    vec3 toN1 = tt[n1] - tt[subj];
    vec3 toN2 = tt[n2] - tt[subj];
    vec3 normal = normalize(cross(toN1, toN2));
    sendNormal(normal, nID);
}


void sendTT(vec3[6] tt, int id, int offset) {
    int mul = 36;

    for(int ii = 5; ii >= 0; ii--)  {
        sendVertex(dp * tt[ii], mul*id + offset + ii);
    }

    calcNormal(tt, 0,   1, 2, mul*id + offset);
    calcNormal(tt, 1,   2, 0, mul*id + offset + 1);
    calcNormal(tt, 2,   0, 1, mul*id + offset + 2);

    calcNormal(tt, 3,   4, 5, mul*id + offset + 3);
    calcNormal(tt, 4,   5, 3, mul*id + offset + 4);
    calcNormal(tt, 5,   3, 4, mul*id + offset + 5);

}


/** Unfortunately, some of the triangles are specified clockwise by
    marching tetrahedra in a really unpredictable way.
    So this is my shabby attempt at finding those triangles
    and flipping the winding order around.                         */

vec3[6] sortVertices(vec3[6] tt) {
    vec3[] ret = {
            tt[0],   tt[1],   tt[2],
            tt[3],   tt[4],   tt[5]
    };

    for(int ii = 0; ii < 2; ii++) {
        vec3 center = vec3(             //find the average of the three triangle vertices
            (tt[3*ii + 0].x + tt[3*ii + 1].x + tt[3*ii + 2].x) / 3,
            (tt[3*ii + 0].y + tt[3*ii + 1].y + tt[3*ii + 2].y) / 3,
            (tt[3*ii + 0].z + tt[3*ii + 1].z + tt[3*ii + 2].z) / 3
        );

        //Now, get the vector from the center of the triangle
        //To vertex #0 and #1 of the triangle
        vec3 toV0 = normalize(tt[3*ii] - center);
        vec3 toV1 = normalize(tt[3*ii+1] - center);

        //Calculate the cross product of the two normalized vectors
        //pointig to vertices #0 and #1 of the triangle, to get
        //the current triangle's normal vector (which might be negative)
        vec3 crss = normalize(cross(toV0, toV1));

        //Now, take the cross product between the triangle's normal vector
        //and the vector that points toward the player/ center of the screen
        float scl = dot(crss, -worldRay);


        //If the normal of the triangle points in a different direction
        //than the screen, we need to flip the order of the triangle

        if(scl < 0) {
            vec3 save = tt[3*ii + 2];
            ret[3*ii + 2] = tt[3*ii];
            ret[3*ii] = save;
        }
    }

    return ret;

}


void main() {
    uint lcx = gl_LocalInvocationID.x;
    uint lcy = gl_LocalInvocationID.y;
    uint lcz = gl_LocalInvocationID.z;

    uint gcx = 10 * gl_WorkGroupID.x;
    uint gcy = 10 * gl_WorkGroupID.y;
    uint gcz = 10 * gl_WorkGroupID.z;

    uint wgx = gl_WorkGroupID.x;
    uint wgy = gl_WorkGroupID.y;
    uint wgz = gl_WorkGroupID.z;

    uint ffID = lcy * 100 + lcz * 10 + lcx + 1000*wgx +
        1000 * uint(segSize.x * segSize.z) * wgy +
        1000 * uint(segSize.x) * wgz;


    //Read all 8 vertices of a cube into our cubeGrid.
    //the fourth direction specifies the value of our
    //field at that vertex.

    vec4[] cubeGrid = {
            vec4(gcx + lcx,     gcy + lcy,     gcz + lcz,     bufferIn.fieldData[ffID]),         //0
            vec4(gcx + lcx + 1, gcy + lcy,     gcz + lcz,     bufferIn.fieldData[ffID + 1]),     //1
            vec4(gcx + lcx + 1, gcy + lcy,     gcz + lcz + 1, bufferIn.fieldData[ffID + 11]),    //2
            vec4(gcx + lcx,     gcy + lcy,     gcz + lcz + 1, bufferIn.fieldData[ffID + 10]),    //3
            vec4(gcx + lcx,     gcy + lcy + 1, gcz + lcz,     bufferIn.fieldData[ffID + 100]),   //4
            vec4(gcx + lcx + 1, gcy + lcy + 1, gcz + lcz,     bufferIn.fieldData[ffID + 101]),   //5
            vec4(gcx + lcx + 1, gcy + lcy + 1, gcz + lcz + 1, bufferIn.fieldData[ffID + 111]),   //6
            vec4(gcx + lcx,     gcy + lcy + 1, gcz + lcz + 1, bufferIn.fieldData[ffID + 110])    //7
    };

    int id = int(gl_LocalInvocationID.y * 81 + gl_LocalInvocationID.z * 9 + gl_LocalInvocationID.x);

    id += int(gl_WorkGroupID.x) * 729;
    id += int(gl_WorkGroupID.y) * int(segSize.x * segSize.z) * 729;
    id += int(gl_WorkGroupID.z) * int(segSize.x) * 729;

    vec3[] tt0 = polygonize(cubeGrid, 0, 2, 3, 7);
    vec3[] tt1 = polygonize(cubeGrid, 0, 2, 6, 7);
    vec3[] tt2 = polygonize(cubeGrid, 0, 4, 6, 7);
    vec3[] tt3 = polygonize(cubeGrid, 0, 6, 1, 2);
    vec3[] tt4 = polygonize(cubeGrid, 0, 6, 1, 4);
    vec3[] tt5 = polygonize(cubeGrid, 5, 6, 1, 4);

    sendTT(sortVertices(tt0), id, 0);
    sendTT(sortVertices(tt1), id, 6);
    sendTT(sortVertices(tt2), id, 12);
    sendTT(sortVertices(tt3), id, 18);
    sendTT(sortVertices(tt4), id, 24);
    sendTT(sortVertices(tt5), id, 30);

}