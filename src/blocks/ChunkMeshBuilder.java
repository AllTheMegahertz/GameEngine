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
//				for (int x = 0; x < SIZE; x++) {
//					for (int z = 0; z < SIZE; z++) {
//						for (int y = 0; y < World.getMaxHeight(); y++) {
//
//							Block block = world.getBlock(new BlockPosition(chunkX * 16 + x, y, chunkZ * 16 + z));
//
//							if (world.getBlock(new BlockPosition(chunkX * 16 + x - 1, y, chunkZ * 16 + z)).getBlockType() == BlockType.Air) {
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
