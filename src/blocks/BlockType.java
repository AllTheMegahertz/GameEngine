package blocks;

/**
 * Created by AllTheMegahertz on 8/27/2017.
 */

public enum BlockType {

	Air (0),
	Stone (1),
	Grass (2),
	Dirt (3);

	public final int blockID;

	BlockType(int blockID) {
		this.blockID = blockID;
	}

	public static BlockType blockType(int ID) {
		return BlockType.values()[ID];
	}

	public int toInt() {
		return blockID;
	}

}
