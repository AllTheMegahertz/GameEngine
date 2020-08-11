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

		Vector3f pos1 = testPosition;
		Vector3f pos2 = bounding.collider.getPosition();

		Vector2f xBoxOverlap = BoundingBox.intersect(this.boxX, new Vector2f(pos1.y, pos1.z), bounding.boxX, new Vector2f(pos2.y, pos2.z));
//		Vector2f yBoxOverlap = BoundingBox.intersect(this.boxY, new Vector2f(pos1.x, pos1.z), bounding.boxY, new Vector2f(pos2.x, pos2.z));
		Vector2f zBoxOverlap = BoundingBox.intersect(this.boxZ, new Vector2f(pos1.x, pos1.y), bounding.boxZ, new Vector2f(pos2.x, pos2.y));

		Vector3f result = new Vector3f(zBoxOverlap.x, xBoxOverlap.x, xBoxOverlap.y);

		System.out.println(result);

		if (result.y != 0) {
			System.out.println("debug");
		}

//		if ((xBoxOverlap.x == 0 && xBoxOverlap.y == 0) || (zBoxOverlap.x == 0 && zBoxOverlap.y == 0)) {
//			return new Vector3f();
//		}

		return new Vector3f(zBoxOverlap.x, xBoxOverlap.x, xBoxOverlap.y);

	}

}
