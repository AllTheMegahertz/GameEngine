package blocks;

import collision.Bounding;
import collision.BoundingBox;
import collision.Collider;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.ModelData;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 8/27/2017.
 */

public class Block implements Collider {

	private static final Loader LOADER = new Loader();
	private static final ModelData DATA = OBJLoader.loadOBJ("cube");
	private static final RawModel RAW_MODEL = LOADER.loadToVAO(DATA.getVertices(), DATA.getIndices(), DATA.getTextureCoords(), DATA.getNormals());
	private static final ModelTexture MODEL_TEXTURE = new ModelTexture(LOADER.loadTexture("textureAtlas"));
	private static final TexturedModel TEXTURED_MODEL;

	private final ArrayList<Face> faces;

	private Bounding bounding;

	static {
		MODEL_TEXTURE.setNumberOfRows(16);
		TEXTURED_MODEL = new TexturedModel(RAW_MODEL, MODEL_TEXTURE);
	}

	private final int blockID;
	private final BlockPosition position;

	public Block(BlockType blockType, BlockPosition position) {

		this.blockID = blockType.blockID;

		faces = new ArrayList<Face>();

		faces.add(new Face(FaceType.TOP, BlockType.blockType(blockID), position));
		faces.add(new Face(FaceType.LEFT, BlockType.blockType(blockID), position));
		faces.add(new Face(FaceType.RIGHT, BlockType.blockType(blockID), position));
		faces.add(new Face(FaceType.FRONT, BlockType.blockType(blockID), position));
		faces.add(new Face(FaceType.BACK, BlockType.blockType(blockID), position));
		faces.add(new Face(FaceType.BOTTOM, BlockType.blockType(blockID), position));

		this.position = position;

		bounding = new Bounding(this, 0.5f);

	}

	public ArrayList<Face> getFaces() {
		return faces;
	}

	public BlockType getBlockType() {
		return BlockType.blockType(blockID);
	}

	public Vector3f getPosition() {
		return new Vector3f(position.position[0], position.position[1], position.position[2]);
	}

	public BlockPosition getBlockPosition() {
		return position;
	}

	public Bounding getBounding() {
		return bounding;
	}
}