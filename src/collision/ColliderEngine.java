package collision;

import entities.World;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public class ColliderEngine {

	private ArrayList<BoundingBox> boundingBoxes;

	public ColliderEngine() {

		this.boundingBoxes = new ArrayList<>();

	}

	public void handleCollisions() {

		for (int i = 0; i < boundingBoxes.size(); i++) {
			for (int j = i + 1; j < boundingBoxes.size(); j++) {

				BoundingBox box1 = boundingBoxes.get(i);
				BoundingBox box2 = boundingBoxes.get(j);

				if (box1.getCenter().lengthSquared() > box2.getCenter().lengthSquared()) {
					Collision data = box1.getCollision(box2);
					if (data.isIntersecting) {
						System.out.println("Collision at " + box1.getCenter().x + " " + box1.getCenter().getY() + " " + box1.getCenter().getZ());
					}
				}

			}
		}

	}

	public void setBoundingBoxArray(ArrayList<BoundingBox> boundingBoxes) {
		this.boundingBoxes = boundingBoxes;
	}

	public void addBoundingBox(BoundingBox boundingBox) {
		boundingBoxes.add(boundingBox);
	}

}
