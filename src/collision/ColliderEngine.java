package collision;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public class ColliderEngine {

	private ArrayList<BoundingBox> boundingBoxes;

	public ColliderEngine() {

		this.boundingBoxes = new ArrayList<>();

	}

	//Handles collisions for all boxes with respect to all other boxes
	public void handleCollisions() {
		for (int i = 0; i < boundingBoxes.size(); i++) {
			for (int j = i + 1; j < boundingBoxes.size(); j++) {
				handleCollision(boundingBoxes.get(i), boundingBoxes.get(j));
			}
		}
	}

	//Handles collisions for one box with respect to all other boxes
	public ArrayList<BoundingBox> handleCollisions(BoundingBox box) {

		ArrayList<BoundingBox> boxes = new ArrayList<>();

		for (BoundingBox box2 : boundingBoxes) {
			if (box != box2) {
				if (handleCollision(box, box2)) {
					boxes.add(box2);
				}
			}
		}

		return boxes;

	}

	//Handles collisions for one box with respect to one other box
	public boolean handleCollision(BoundingBox box1, BoundingBox box2) {

		boolean intersecting = false;

		if (Vector3f.sub(box1.getCenter(), box2.getCenter(), new Vector3f()).length() < box1.getHalfExtent().length() + box2.getHalfExtent().length()) {
			Collision data = box1.getCollision(box2);
			intersecting = data.isIntersecting;
		}

		return intersecting;

	}

	public void setBoundingBoxArray(ArrayList<BoundingBox> boundingBoxes) {
		this.boundingBoxes = boundingBoxes;
	}

	public void addBoundingBox(BoundingBox boundingBox) {
		boundingBoxes.add(boundingBox);
	}

}
