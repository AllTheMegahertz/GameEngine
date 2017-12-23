package blocks;

import org.lwjgl.util.vector.Vector3f;

import java.util.Arrays;

/**
 * Created by AllTheMegahertz on 8/28/2017.
 */

public class BlockPosition {

	public final int[] position;

	public BlockPosition(int x ,int y, int z) {
		this.position = new int[] {x, y, z};
	}

	public int[] getPosition() {
		return position;
	}

	public Vector3f getVector() {
		return new Vector3f(position[0], position[1], position[2]);
	}

	public int getX() {
		return  position[0];
	}

	public int getY() {
		return position[1];
	}

	public int getZ() {
		return position[2];
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BlockPosition that = (BlockPosition) o;

		return Arrays.equals(position, that.position);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(position);
	}

}
