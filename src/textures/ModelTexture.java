package textures;

/**
 * Created by AllTheMegahertz on 8/15/2017.
 */

public class ModelTexture {

	private int textureID;
	private boolean isTransparent = false;
	private boolean useFakeLighting = false;

	private int numberOfRows = 1;

	public ModelTexture(int id) {
		this.textureID = id;
	}

	public int getTextureID() {
		return textureID;
	}

	public boolean isTransparent() {
		return isTransparent;
	}

	public void setTransparent(boolean transparent) {
		isTransparent = transparent;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

}
