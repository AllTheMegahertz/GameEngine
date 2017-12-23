package entities;

import blocks.BlockType;
import blocks.FaceType;

import java.util.HashMap;

/**
 * Created by AllTheMegahertz on 8/28/2017.
 */

public enum TextureIndex {

	GRASS_TOP (0),
	STONE (1),
	DIRT (2),
	GRASS_SIDE (3);

	private final int textureIndex;

	private static final HashMap<BlockType, Integer> textureIndices = new HashMap<BlockType, Integer>();

	static {

		textureIndices.put(BlockType.Air, -1);
		textureIndices.put(BlockType.Grass, 0);
		textureIndices.put(BlockType.Stone, 1);
		textureIndices.put(BlockType.Dirt, 2);

	}

	TextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public int textureIndex() {
		return this.textureIndex;
	}

	public static int getTextureIndex(BlockType blockType, FaceType faceType) {

		if (blockType == BlockType.Grass) {

			if (faceType == FaceType.TOP) {
				return TextureIndex.GRASS_TOP.textureIndex();
			}
			else if (faceType == FaceType.FRONT || faceType == FaceType.BACK || faceType == FaceType.LEFT || faceType == FaceType.RIGHT) {
				return TextureIndex.GRASS_SIDE.textureIndex();
			}
			else if (faceType == FaceType.BOTTOM) {
				return TextureIndex.DIRT.textureIndex();
			}

		}

		return textureIndices.get(blockType);

	}

}
