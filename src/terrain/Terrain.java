package terrain;

import blocks.Block;
import blocks.BlockPosition;
import blocks.BlockType;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 9/2/2017.
 */

public class Terrain {

	private static final int SIZE = 50;

	private int x;
	private int z;
	private int[][] heights;
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private HeightsGenerator heightsGenerator;

	public Terrain(int x, int z, HeightsGenerator heightsGenerator) {

		this.x = x;
		this.z = z;

		this.heightsGenerator = heightsGenerator;
		generateTerrain();

	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public ArrayList<Block> getBlocks() {

		ArrayList<Block> blocks = new ArrayList<Block>();

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				blocks.add(new Block(BlockType.Grass, new BlockPosition(i, getHeight(i, j), j)));
				blocks.add(new Block(BlockType.Dirt, new BlockPosition(i, getHeight(i, j) - 1, j)));
				blocks.add(new Block(BlockType.Dirt, new BlockPosition(i, getHeight(i, j) - 2, j)));
				blocks.add(new Block(BlockType.Dirt, new BlockPosition(i, getHeight(i, j) - 3, j)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(i, getHeight(i, j) - 4, j)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(i, getHeight(i, j) - 5, j)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(i, getHeight(i, j) - 6, j)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(i, getHeight(i, j) - 7, j)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(i, getHeight(i, j) - 8, j)));
			}
		}

		return blocks;
	}

	private int getHeight(int x, int z) {

		try {
			return heights[x][z];
		}
		catch (IndexOutOfBoundsException e) {
			return 0;
		}


	}

	private void generateTerrain() {

		heights = new int[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				heights[i][j] = heightsGenerator.generateHeight(i, j);
			}
		}

	}

}
