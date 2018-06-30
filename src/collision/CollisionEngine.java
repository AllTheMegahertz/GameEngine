package collision;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public class CollisionEngine {

	private ArrayList<Bounding> boundings;

	public CollisionEngine() {

		this.boundings = new ArrayList<>();

	}

	//Handles collisions for all boxes with respect to all other boxes
//	public void handleCollisions() {
//		for (int i = 0; i < boundings.size(); i++) {
//			for (int j = i + 1; j < boundings.size(); j++) {
//				handleCollision(boundings.get(i), boundings.get(j));
//			}
//		}
//	}

	//Handles collisions for one box with respect to all other boxes
	public Vector3f handleCollisions(Bounding bounding, Vector3f test, Vector3f movement) {

		ArrayList<Bounding> collisions = new ArrayList<>();
		Vector3f correction = movement;

		for (Bounding bounding2 : boundings) {
			if (bounding != bounding2) {
				Vector3f.add(handleCollision(bounding, bounding2, test), correction, correction);
			}
		}

		return correction;

	}

	//Handles collisions for one box with respect to one other box
	public Vector3f handleCollision(Bounding bounding1, Bounding bounding2, Vector3f test) {
		return bounding1.intersect(test, bounding2);
	}

	public void setBoundingArray(ArrayList<Bounding> boundings) {
		this.boundings = boundings;
	}

	public void addBounding(Bounding bounding) {
		boundings.add(bounding);
	}

}
