package entities;

import collision.BoundingBox;
import models.TexturedModel;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public abstract class PhysicsObject extends Entity {

	private static final float GRAVITY = -0.5f;

	protected BoundingBox boundingBox;
	protected Location location;

	protected float xSpeed = 0;
	protected float ySpeed = 0;
	protected float zSpeed = 0;
	protected float currentTurnSpeed = 0;
	protected boolean onGround;

	public PhysicsObject(TexturedModel model, BoundingBox boundingBox, Vector3f position, World world, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.boundingBox = boundingBox;
		location = new Location(world, position, rotX, rotY);
	}

	public void move() {

		setRotY(getRotY() - Mouse.getDX() * 0.1f);

		float xDistance = xSpeed * DisplayManager.getFrameTime();
		float zDistance = zSpeed * DisplayManager.getFrameTime();

		float dx = xDistance * (float) Math.cos(Math.toRadians(getRotY())) + zDistance * (float) Math.sin(Math.toRadians(getRotY()));
		float dz = zDistance * (float) Math.cos(Math.toRadians(getRotY())) - xDistance * (float) Math.sin(Math.toRadians(getRotY()));

		if (!onGround) {
			ySpeed += GRAVITY * DisplayManager.getFrameTime();
		}

		BoundingBox testBox = new BoundingBox(Vector3f.add(boundingBox.getCenter(), new Vector3f(dx, ySpeed, dz), new Vector3f()), boundingBox.getHalfExtent());
		ArrayList<BoundingBox> collisions = location.getWorld().getColliderEngine().handleCollisions(testBox);

		onGround = false;
		for (BoundingBox box : collisions) {

			boolean xCheck = (int) Math.floor(box.getCenter().x) != Math.round(testBox.getCenter().x);
			boolean yCheck = (int) Math.floor(box.getCenter().y) != Math.round(testBox.getCenter().y);
			boolean zCheck = (int) Math.floor(box.getCenter().z) != Math.round(testBox.getCenter().z);

			if (xCheck && !yCheck && !zCheck) {
				dx = 0;
			}

			if (!xCheck && yCheck && !zCheck) {
				setPosition(new Vector3f(getPosition().x, getPosition().y - boundingBox.getCollision(box).distance.y, getPosition().z));
				ySpeed = 0;
				onGround = true;
			}

			if (!xCheck && !yCheck && zCheck) {
				dz = 0;
			}

		}

		super.increasePosition(dx, ySpeed, dz);
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTime(), 0);

		boundingBox.setCenter(new Vector3f(getPosition().x, getPosition().y, getPosition().z));

	}

}
