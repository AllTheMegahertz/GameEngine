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

	public Terrain(int x, int z) {

		this.x = x;
		this.z = z;

		this.heightsGenerator = new HeightsGenerator();
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

				int blockX = i + x * SIZE;
				int blockZ = j + z * SIZE;

				blocks.add(new Block(BlockType.Grass, new BlockPosition(blockX, getHeight(blockX, blockZ), blockZ)));
				blocks.add(new Block(BlockType.Dirt, new BlockPosition(blockX, getHeight(blockX, blockZ) - 1, blockZ)));
				blocks.add(new Block(BlockType.Dirt, new BlockPosition(blockX, getHeight(blockX, blockZ) - 2, blockZ)));
				blocks.add(new Block(BlockType.Dirt, new BlockPosition(blockX, getHeight(blockX, blockZ) - 3, blockZ)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(blockX, getHeight(blockX, blockZ) - 4, blockZ)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(blockX, getHeight(blockX, blockZ) - 5, blockZ)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(blockX, getHeight(blockX, blockZ) - 6, blockZ)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(blockX, getHeight(blockX, blockZ) - 7, blockZ)));
				blocks.add(new Block(BlockType.Stone, new BlockPosition(blockX, getHeight(blockX, blockZ) - 8, blockZ)));

			}
		}

		return blocks;
	}

	private int getHeight(int x, int z) {

		try {
			return heights[x - this.x * SIZE][z - this.z * SIZE];
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
