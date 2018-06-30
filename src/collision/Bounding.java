package collision;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Bounding {

	private Collider collider;

	private BoundingBox boxX;
	private BoundingBox boxY;
	private BoundingBox boxZ;

	public Bounding(Collider collider, BoundingBox boxX, BoundingBox boxY, BoundingBox boxZ) {
		this.collider = collider;
		this.boxX = boxX;
		this.boxY = boxY;
		this.boxZ = boxZ;
	}

	public Bounding(Collider collider, float radius) {
		this.collider = collider;

		Vector2f box = new Vector2f(radius, radius);
		Vector2f negativeBox = new Vector2f(-radius, -radius);

		this.boxX = new BoundingBox(negativeBox, box);
		this.boxY = new BoundingBox(negativeBox, box);
		this.boxZ = new BoundingBox(negativeBox, box);
	}

//	public boolean intersect(Bounding bounding) {
//		return intersect(this.collider.getPosition(), bounding);
//	}

	public Vector3f intersect(Vector3f testPosition, Bounding bounding) {

		Vector3f correction = new Vector3f();

		Vector3f pos1 = testPosition;
		Vector3f pos2 = bounding.collider.getPosition();

		boolean x = false;
		boolean y = false;
		boolean z = false;
		int check = 0;

		if (BoundingBox.intersect(this.boxX, new Vector2f(pos1.y, pos1.z), bounding.boxX, new Vector2f(pos2.y, pos2.z))) {

			correction.y = -Math.min(this.boxY.v2.x - bounding.boxY.v1.x, this.boxY.v1.x - bounding.boxY.v2.x);
			correction.z = -Math.min(this.boxZ.v2.y - bounding.boxZ.v1.y, this.boxZ.v1.y - bounding.boxZ.v2.y);
			check++;
			x = true;

		}
		if (BoundingBox.intersect(this.boxY, new Vector2f(pos1.x, pos1.z), bounding.boxY, new Vector2f(pos2.x, pos2.z))) {

			correction.x = -Math.min(this.boxX.v2.x - bounding.boxX.v1.x, this.boxX.v1.x - bounding.boxX.v2.x);
			correction.z = -Math.min(this.boxZ.v2.y - bounding.boxZ.v1.y, this.boxZ.v1.y - bounding.boxZ.v2.y);
			check++;
			y = true;

		}
		if (BoundingBox.intersect(this.boxZ, new Vector2f(pos1.x, pos1.y), bounding.boxZ, new Vector2f(pos2.x, pos2.y))) {

			correction.x = -Math.min(this.boxX.v2.x - bounding.boxX.v1.x, this.boxX.v1.x - bounding.boxX.v2.x);
			correction.y = -Math.min(this.boxY.v2.y - bounding.boxY.v1.y, this.boxY.v1.y - bounding.boxY.v2.y);
			check++;
			z = true;

		}

		if (check < 2) {
			return new Vector3f();
		}

		float[] values = new float[] {Math.abs(correction.x), Math.abs(correction.y), Math.abs(correction.z)};
		float test = 0;
		for (int i = 0; i < 3; i++) {
			if (values[i] > test) {
				test = values[i];
			}
		}

		if (test == values[0]) {
			correction.y = 0;
			correction.z = 0;
		} else if (test == values[1]) {
			correction.y = 0;
			correction.z = 0;
		} else if (test == values[2]) {
			correction.x = 0;
			correction.y = 0;
		}

//		if (!x) {
//			correction.x = 0;
//		}
//		if (!y) {
//			correction.y = 0;
//		}
//		if (!z) {
//			correction.z = 0;
//		}

		return correction;

	}

}
