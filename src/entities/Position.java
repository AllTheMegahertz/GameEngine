package entities;

/**
 * Created by AllTheMegahertz on 8/31/2017.
 */

public class Position {

	private float[] position;

	public Position(float[] position) {
		this.position = position;
	}

	public float getX() {
		return  position[0];
	}

	public float getY() {
		return position[1];
	}

	public float getZ() {
		return position[3];
	}

	public float getPitch() {
		return position[4];
	}

	public float getYaw() {
		return position[5];
	}

}
