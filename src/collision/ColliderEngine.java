package collision;

import entities.World;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public class ColliderEngine {

	private ArrayList<BoundingBox> boundingBoxes;
	private World world;

	public ColliderEngine(World world) {

		this.boundingBoxes = new ArrayList<>();
		this.world = world;

	}

	public void handleCollisions() {

	}

	public void setBoundingBoxArray(ArrayList<BoundingBox> boundingBoxes) {
		this.boundingBoxes = boundingBoxes;
	}

	public void addBoundingBox(BoundingBox boundingBox) {
		boundingBoxes.add(boundingBox);
	}

}
