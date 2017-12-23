package blocks;

import entities.Entity;
import entities.TextureIndex;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.ModelData;
import renderEngine.OBJLoader;
import textures.ModelTexture;

/**
 * Created by AllTheMegahertz on 9/2/2017.
 */

public class Face {

	private static final Loader LOADER = new Loader();
	private static final ModelData DATA = OBJLoader.loadOBJ("face");
	private static final RawModel RAW_MODEL = LOADER.loadToVAO(DATA.getVertices(), DATA.getIndices(), DATA.getTextureCoords(), DATA.getNormals());
	private static final ModelTexture MODEL_TEXTURE = new ModelTexture(LOADER.loadTexture("textureAtlas"));
	private static final TexturedModel TEXTURED_MODEL;

	private final int textureIndex;
	private final FaceType faceType;
	private final BlockPosition blockPosition;

	static {
		MODEL_TEXTURE.setNumberOfRows(16);
		TEXTURED_MODEL = new TexturedModel(RAW_MODEL, MODEL_TEXTURE);
	}

	public Face(FaceType faceType, BlockType blockType, BlockPosition blockPosition) {
		this.faceType = faceType;
		this.textureIndex = TextureIndex.getTextureIndex(blockType, faceType);
		this.blockPosition = blockPosition;
	}

	public FaceType getFaceType() {
		return faceType;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public Entity getEntity() {

		if (faceType == FaceType.TOP) {
			Vector3f position = new Vector3f(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
			return new Entity(TEXTURED_MODEL, getTextureIndex(), position, 0, 0, 0, 1);
		}
		else if (faceType == FaceType.LEFT) {
			Vector3f position = new Vector3f(blockPosition.getX(), blockPosition.getY() - 0.5f, blockPosition.getZ() + 0.5f);
			return new Entity(TEXTURED_MODEL, getTextureIndex(), position, 90, 0, 0, 1);
		}
		else if (faceType == FaceType.RIGHT) {
			Vector3f position = new Vector3f(blockPosition.getX(), blockPosition.getY() - 0.5f, blockPosition.getZ() - 0.5f);
			return new Entity(TEXTURED_MODEL, getTextureIndex(), position, 90, 0, 180, 1);
		}
		else if (faceType == FaceType.FRONT) {
			Vector3f position = new Vector3f(blockPosition.getX() - 0.5f, blockPosition.getY() - 0.5f, blockPosition.getZ());
			return new Entity(TEXTURED_MODEL, getTextureIndex(), position, 90, 0, 90, 1);
		}
		else if (faceType == FaceType.BACK) {
			Vector3f position = new Vector3f(blockPosition.getX() + 0.5f, blockPosition.getY() - 0.5f, blockPosition.getZ());
			return new Entity(TEXTURED_MODEL, getTextureIndex(), position, 90, 0, -90, 1);
		}
		else if (faceType == FaceType.BOTTOM) {
			Vector3f position = new Vector3f(blockPosition.getX(), blockPosition.getY() - 1, blockPosition.getZ());
			return new Entity(TEXTURED_MODEL, getTextureIndex(), position, 180, 0, 0, 1);
		}
		else {
			return null;
		}

	}

}
