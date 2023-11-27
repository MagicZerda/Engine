package de.magiczerda.engine.plane;

import de.magiczerda.engine.core.gameObjects.GameObject;
import de.magiczerda.engine.core.gameObjects.Model;
import de.magiczerda.engine.core.loading.Loader;
import de.magiczerda.engine.vectorVis.Vector;
import de.magiczerda.engine.vectorVis.VectorRenderer;
import org.joml.Vector3f;

public class Plane {

    protected float dp = 1f;
    protected int ppz = 10;
    protected int ppx = 10;


    protected float[] vertices;
    protected int[] indices;
    protected float[] normals;


    protected GameObject planeGameObject;

    public Plane(HeightGenerator heightGen) {
        createVertices();
        createIndices();

        applyHeight(heightGen);
        createNormals();

        planeGameObject = new GameObject(Loader.loadModel(vertices, normals, indices, true));


        for(int ii = 0; ii < vertices.length/3; ii++) {
            if(ii % ppx == 0) continue;

            VectorRenderer.vectors.add(new Vector(
                    vertices[3*ii  ],
                    vertices[3*ii+1],
                    vertices[3*ii+2],

                    normals[3*ii  ],
                    normals[3*ii+1],
                    normals[3*ii+2]));
        }

    }


    private void createVertices() {
        int vertexCount = ppx * ppz;
        vertices = new float[3 * vertexCount];

        for(int vv = 0; vv < vertexCount; vv++) {
            int xx = vv % ppx;
            int zz = vv / ppx;

            vertices[3*vv    ] = dp * xx;
            vertices[3*vv + 1] = 0;
            vertices[3*vv + 2] = dp * (-0.5f*xx + zz);
        }
    }

    private void createIndices() {
        indices = new int[6 * (ppx - 1) * (ppz-1)];

        for(int zz = 0; zz < ppz - 1; zz++) {
            for(int xx = 0; xx < ppx - 1; xx++) {
                //System.out.println(((ppx-1)*zz+xx) + " " + (ppx*zz + xx));

                int rowStartIndex = 6 * ((ppx-1)*zz+xx);
                indices[rowStartIndex    ] = ppx * zz + xx;   //column 0
                indices[rowStartIndex + 1] = indices[rowStartIndex] + ppx;
                indices[rowStartIndex + 2] = indices[rowStartIndex + 1] + 1;
                indices[rowStartIndex + 3] = indices[rowStartIndex + 2];
                indices[rowStartIndex + 4] = indices[rowStartIndex] + 1;
                indices[rowStartIndex + 5] = indices[rowStartIndex];
            }
        }
    }

    public void applyHeight(HeightGenerator heightGenerator) {
        for(int vv = 0; vv < ppx * ppz; vv++) {
            int xx = vv % ppx;
            int zz = vv / ppx;
            vertices[3*vv + 1] = heightGenerator.getHeightAt(xx, zz);
        }
    }

    private Vector3f subj = new Vector3f();
    private Vector3f nei1 = new Vector3f();
    private Vector3f nei2 = new Vector3f();
    private Vector3f normal = new Vector3f();
    public void createNormals() {
        normals = new float[vertices.length];

        //int subjIndex = 0;


        for(int subjIndex = 0; subjIndex < ppx * (ppz - 1); subjIndex++) {
            //if(subjIndex == ppx - 1) continue;

            //this.normal.set(0, 0, 0);

            //ORDAAAA
            createNormal(subjIndex, subjIndex - ppx    , subjIndex - ppx - 1);
            createNormal(subjIndex, subjIndex - ppx    , subjIndex       - 1);

            createNormal(subjIndex, subjIndex - ppx - 1, subjIndex       - 1);
            createNormal(subjIndex, subjIndex - ppx - 1, subjIndex + ppx    );

            createNormal(subjIndex, subjIndex       - 1, subjIndex + ppx    );
            createNormal(subjIndex, subjIndex       - 1, subjIndex + ppx + 1);

            createNormal(subjIndex, subjIndex + ppx    , subjIndex + ppx + 1);
            createNormal(subjIndex, subjIndex + ppx    , subjIndex       + 1);

            createNormal(subjIndex, subjIndex + ppx    , subjIndex + ppx + 1);
            createNormal(subjIndex, subjIndex + ppx    , subjIndex       + 1);

            createNormal(subjIndex, subjIndex + ppx + 1,subjIndex        + 1);
            createNormal(subjIndex, subjIndex + ppx + 1,subjIndex - ppx     );

            createNormal(subjIndex, subjIndex       + 1,subjIndex - ppx     );
            createNormal(subjIndex, subjIndex       + 1,subjIndex - ppx  - 1);

            this.normal.normalize();

            normals[3*subjIndex    ] += this.normal.x;
            normals[3*subjIndex + 1] += this.normal.y;
            normals[3*subjIndex + 2] += this.normal.z;

        }

        //smoothNormals();

    }

    protected void createNormal(int subj, int n1, int n2) {
        if(subj < 0 || n1 < 0 || n2 < 0) return;
        if(3*n1 >= vertices.length || 3*n2 >= vertices.length) return;

        this.subj.set(vertices[3*subj    ],
                      vertices[3*subj + 1],
                      vertices[3*subj + 2]);

        this.nei1.set(vertices[3*n1    ],
                      vertices[3*n1 + 1],
                      vertices[3*n1 + 2]);

        this.nei2.set(vertices[3*n2    ],
                      vertices[3*n2 + 1],
                      vertices[3*n2 + 2]);

        this.nei1.sub(this.subj, this.nei1);
        this.nei2.sub(this.subj, this.nei2);

        this.nei1.normalize();
        this.nei2.normalize();

        this.nei1.cross(this.nei2, this.nei1);
        this.nei1.normalize();
        this.normal.add(this.nei1);
    }

    /*protected void smoothNormals() {
        //int index = 10;



        for(int index = 0; index < ppx*(ppz - 1); index++) {
            for(int ii = 0; ii <= 1; ii+=3) {
                int index2 = index + ii;
                if(index2 < 0) continue;

                normals[3*index    ] += normals[3*index2    ];
                normals[3*index + 1] += normals[3*index2 + 1];
                normals[3*index + 2] += normals[3*index2 + 2];
            }
        }
    }*/

    public GameObject createGameObject() { return planeGameObject; }

}
