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

	public static Vector2f intersect(BoundingBox a, Vector2f posA, BoundingBox b, Vector2f posB) {

		Vector2f aV1 = Vector2f.add(a.v1, posA, new Vector2f());
		Vector2f aV2 = Vector2f.add(a.v2, posA, new Vector2f());
		Vector2f bV1 = Vector2f.add(b.v1, posB, new Vector2f());
		Vector2f bV2 = Vector2f.add(b.v2, posB, new Vector2f());

		// The following section checks for intersections across two axes, but they are not necessarily x and y,
		// depending on what type of box is being checked.

		// Check x-axis
		float xError = 0;
		if (aV1.x < bV2.x && aV2.x > bV1.x) {
			xError = aV1.x - bV2.x;
		}
		else if (aV2.x > bV1.x && aV1.x < bV2.x) {
			xError = bV1.x - aV2.x;
		}

		// Check Y-axis
		float yError = 0;
		if (aV1.x < bV2.x && aV2.x > bV1.x) {
			yError = aV1.x - bV2.x;
		}
		else if (aV2.x > bV1.x && aV1.x < bV2.x) {
			yError = bV1.x - aV2.x;
		}

		return new Vector2f(xError * -1, yError * -1);

	}

}
