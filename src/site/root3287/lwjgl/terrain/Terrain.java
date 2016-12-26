package site.root3287.lwjgl.terrain;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import site.root3287.lwjgl.engine.Loader;
import site.root3287.lwjgl.model.RawModel;
import site.root3287.lwjgl.texture.ModelTexture;
import site.root3287.lwjgl.toolbox.LWJGLMaths;

public class Terrain {
    private static final float SIZE = 800;
    private static final float MAX_HEIGHT = 100;
    private static final float MAX_PIXEL_COLOUR = 256*256*256;
    
    private int vertexCount;
    private float x;
    private float z;
    private RawModel model;
    private ModelTexture texture;
    private float[][] heights;
    private int seed;
    
    public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture, int vertexCount){
    	this.vertexCount = vertexCount;
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        HeightGenerator generator = new HeightGenerator(gridX, gridZ, this.vertexCount);
        this.model = generateTerrain(loader, generator);
        this.texture.setFakeLight(true);
    }
    
    public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture, int vertexCount, int seed){
    	this.vertexCount = vertexCount;
    	this.seed = seed;
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        HeightGenerator generator = new HeightGenerator(gridX, gridZ, this.vertexCount, this.seed);
        this.model = generateTerrain(loader, generator);
        this.texture.setFakeLight(true);
    }
      
    public float getX() {
        return x;
    }
 
    public float getZ() {
        return z;
    }
 
    public RawModel getModel() {
        return model;
    }
 
    public ModelTexture getTexture() {
        return texture;
    }
    
    private Vector3f calculateNormal(int x, int z, HeightGenerator generator){
    	float heightL = getHeight(x-1, z, generator);
    	float heightR = getHeight(x+1, z, generator);
    	float heightD = getHeight(x, z-1, generator);
    	float heightU = getHeight(x, z+1, generator);
    	Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD-heightU);
    	normal.normalise();
    	return normal;
    }
    
	private RawModel generateTerrain(Loader loader){ // Flat Terrain
    	int VERTEX_COUNT = 128;
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }

	private RawModel generateTerrain(Loader loader, HeightGenerator generator){
        int VERTEX_COUNT = this.vertexCount;
        int count = VERTEX_COUNT * VERTEX_COUNT;
        heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT * 1)];
        int vertexPointer = 0;
        
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
            	
            	float height = getHeight(j, i, generator);
				heights[j][i] = height;
            	
            	vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				
				Vector3f normal = calculateNormal(j, i, generator);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				
				vertexPointer++;
            }
        }
        
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
	
    private float getHeight(int x, int z, HeightGenerator generator){
    	return generator.generateHeight2(x, z);
    }
    public float getTerrainHeightByCoords(float worldX, float worldZ){
    	float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}

		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;

		if (xCoord <= (1 - zCoord)) {
			answer = LWJGLMaths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			answer = LWJGLMaths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}

		return answer;
    }
}