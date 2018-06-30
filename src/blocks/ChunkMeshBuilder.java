package blocks;

import entities.World;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 9/2/2017.
 */

public class ChunkMeshBuilder {

	private static final int SIZE = 16;
	private ArrayList<Block> blocks;
	private World world;

	public ChunkMeshBuilder(World world) {
		this.blocks = world.getBlocks();
		this.world = world;
	}

//	public ArrayList<ChunkMesh> generateChunkMeshes() {
//
//		for (int chunkX = 0; chunkX < 208; chunkX++) {
//			for (int chunkZ = 0; chunkZ < 208; chunkZ++) {
//
//				for (int boxX = 0; boxX < SIZE; boxX++) {
//					for (int boxZ = 0; boxZ < SIZE; boxZ++) {
//						for (int boxY = 0; boxY < World.getMaxHeight(); boxY++) {
//
//							Block block = world.getBlock(new BlockPosition(chunkX * 16 + boxX, boxY, chunkZ * 16 + boxZ));
//
//							if (world.getBlock(new BlockPosition(chunkX * 16 + boxX - 1, boxY, chunkZ * 16 + boxZ)).getBlockType() == BlockType.Air) {
//								block.getEntity().getModel().getRawModel()
//							}
//
//						}
//					}
//				}
//
//			}
//		}
//
//	}

}
