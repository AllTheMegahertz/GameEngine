package entities;

import blocks.Block;
import blocks.BlockPosition;
import blocks.BlockType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by AllTheMegahertz on 8/27/2017.
 */

public class World {

	private static final int MAX_HEIGHT = 256;

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private HashMap<BlockPosition, Block> blocks = new HashMap<BlockPosition, Block>();
	private HashMap<BlockPosition, Block> openBlocks = new HashMap<BlockPosition, Block>();

	public World() {

	}

	public void addBlock(Block block) {
		blocks.put(block.getPosition(), block);
		processBlocks();
	}

	public void addBlocks(ArrayList<Block> blocks) {

		for (Block block : blocks) {
			this.blocks.put(block.getPosition(), block);
		}

		processBlocks();

	}

	public Block getBlock(BlockPosition position) {

		if (blocks.get(position) == null) {
			return new Block(BlockType.Air, position);
		}

		return blocks.get(position);

	}


	public HashMap<BlockPosition, Block> getHashMap() {
		return blocks;
	}

	public ArrayList<Block> getBlocks() {

		ArrayList<Block> blocks = new ArrayList<Block>();
		for (Block block : this.blocks.values()) {
			blocks.add(block);
		}

		return blocks;

	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void addEntities(ArrayList<Entity> entities) {
		for (Entity entity : entities) {
			this.entities.add(entity);
		}
	}

	public ArrayList<Block> getOpenBlocks() {

		ArrayList<Block> blocks = new ArrayList<Block>();
		for (Block block : this.openBlocks.values()) {
			blocks.add(block);
		}

		this.openBlocks.values();

		return blocks;

	}

	private void processBlocks() {

		for (Block block : getBlocks()) {

			BlockPosition position = block.getPosition();

			ArrayList<Block> surroundingBlocks = new ArrayList<Block>();

			surroundingBlocks.add(getBlock(new BlockPosition(position.getX() - 1, position.getY(), position.getZ())));
			surroundingBlocks.add(getBlock(new BlockPosition(position.getX(), position.getY() - 1, position.getZ())));
			surroundingBlocks.add(getBlock(new BlockPosition(position.getX(), position.getY(), position.getZ() - 1)));
			surroundingBlocks.add(getBlock(new BlockPosition(position.getX() + 1 , position.getY(), position.getZ())));
			surroundingBlocks.add(getBlock(new BlockPosition(position.getX(), position.getY() + 1, position.getZ())));
			surroundingBlocks.add(getBlock(new BlockPosition(position.getX(), position.getY(), position.getZ() + 1)));

			for (Block sBlock : surroundingBlocks) {
				if (sBlock.getBlockType() == BlockType.Air) {
					openBlocks.put(block.getPosition(), block);
					break;
				}
			}

		}

	}

	public static int getMaxHeight() {
		return MAX_HEIGHT;
	}
}