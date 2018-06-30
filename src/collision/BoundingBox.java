package collision;

import org.lwjgl.util.vector.Vector2f;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public class BoundingBox {

	public Vector2f v1;
	public Vector2f v2;

	public BoundingBox(Vector2f v1, Vector2f v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	public static boolean intersect(BoundingBox a, Vector2f posA, BoundingBox b, Vector2f posB) {

		Vector2f aV1 = Vector2f.add(a.v1, posA, new Vector2f());
		Vector2f aV2 = Vector2f.add(a.v2, posA, new Vector2f());
		Vector2f bV1 = Vector2f.add(b.v1, posB, new Vector2f());
		Vector2f bV2 = Vector2f.add(b.v2, posB, new Vector2f());

		// The following section checks for intersections across two axes, but they are not necessarily x and y,
		// depending on what type of box is being checked.

		if (((aV1.x <= bV1.x && aV2.x > bV1.x) || (bV1.x <= aV1.x && bV2.x > aV1.x)) && // Check along x-axis
			((aV1.y <= bV1.y && aV2.y > bV1.y) || (bV1.y <= aV1.y && bV2.y > aV1.y))) { // Check along y-axis
			return true;
		}


		return false;

	}

}
