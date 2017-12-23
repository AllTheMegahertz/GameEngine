package terrain;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import sun.dc.pr.PRError;
import textures.ModelTexture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by AllTheMegahertz on 8/17/2017.
 */

@Deprecated
public class OldTerrain {

	private static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_VALUE = 256 * 256 * 256;

	private float x;
	private float z;
	private float[][] heights;

	private RawModel model;
	private ModelTexture texture;

	public OldTerrain(int x, int z, Loader loader, ModelTexture texture) {

		this.x = x * SIZE;
		this.z = z * SIZE;

		this.texture = texture;

		this.model = generateTerrain(loader, "heightmap2");

	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public ModelTexture getTexture() {
		return texture;
	}

	public RawModel getModel() {
		return model;
	}

	private RawModel generateTerrain(Loader loader, String heightMap) {

		BufferedImage image = null;

		try {
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		}
		catch (IOException e) {
			System.err.println("Could not load height map");
			e.printStackTrace();
		}

		int vertexCount = image.getHeight();
		this.heights = new float[vertexCount][vertexCount];

		int count = vertexCount * vertexCount;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(vertexCount-1)*(vertexCount-1)];
		int vertexPointer = 0;
		for(int i=0;i<vertexCount;i++){
			for(int j=0;j<vertexCount;j++){
				vertices[vertexPointer*3] = (float)j/((float)vertexCount - 1) * SIZE;

				float height = getHeight(i, j, image);
				heights[j][i] = height;

				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)vertexCount - 1) * SIZE;

				Vector3f normal = calculateNormal(i, j, image);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)vertexCount - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)vertexCount - 1);
				vertexPointer++;
			}
		}

		int pointer = 0;
		for(int gz=0;gz<vertexCount-1;gz++){
			for(int gx=0;gx<vertexCount-1;gx++){
				int topLeft = (gz*vertexCount)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*vertexCount)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}

		return loader.loadToVAO(vertices, indices, textureCoords, normals);

	}

	private float getHeight(int x, int z, BufferedImage image) {

		x = Math.max(Math.min(x, image.getHeight() - 1), 0);
		z = Math.max(Math.min(z, image.getHeight() - 1), 0);

		return (float) (image.getRGB(x, z) / MAX_PIXEL_VALUE + 0.5) * 2 * MAX_HEIGHT;
	}

	public float getHeight(float worldX, float worldZ) {

		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);

		int gridX = (int) Math.floor((terrainX / gridSquareSize));
		int gridZ = (int) Math.floor((terrainZ / gridSquareSize));

		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}

		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;

		float height;
		if (xCoord <= (1-zCoord)) {
			height = toolbox.Math
					.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ], 0), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			height = toolbox.Math
					.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}

		return height;

	}

	private Vector3f calculateNormal(int x, int z, BufferedImage image) {

		float heightL = getHeight(x - 1, z, image);
		float heightR = getHeight(x + 1, z, image);
		float heightF = getHeight(x, z + 1, image);
		float heightB = getHeight(x, z - 1, image);

		Vector3f normal = new Vector3f(heightL - heightR, 2, heightB - heightF);
		normal.normalise();

		return normal;

	}

}
