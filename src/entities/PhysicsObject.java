package entities;

import collision.Bounding;
import collision.BoundingBox;
import collision.Collider;
import models.TexturedModel;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public abstract class PhysicsObject extends Entity implements Collider {

	private static final float GRAVITY = -15;

	protected Bounding bounding;
	protected Location location;

	protected float xSpeed = 0;
	protected float ySpeed = 0;
	protected float zSpeed = 0;
	protected float currentTurnSpeed = 0;
	protected boolean onGround;

	public PhysicsObject(TexturedModel model, Vector3f position, World world, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.bounding = new Bounding(this, 0.5f); // TODO: Don't
		location = new Location(world, position, rotX, rotY);
	}

	public void move() {

		setRotY(getRotY() - Mouse.getDX() * 0.1f);

		if (!onGround) {
			ySpeed += GRAVITY * DisplayManager.getFrameTime();
		}

		float xDistance = xSpeed * DisplayManager.getFrameTime();
		float zDistance = zSpeed * DisplayManager.getFrameTime();

		float dx = xDistance * (float) Math.cos(Math.toRadians(getRotY())) + zDistance * (float) Math.sin(Math.toRadians(getRotY()));
		float dy = ySpeed * DisplayManager.getFrameTime();
		float dz = zDistance * (float) Math.cos(Math.toRadians(getRotY())) - xDistance * (float) Math.sin(Math.toRadians(getRotY()));

//		BoundingBox testBox = new BoundingBox(Vector3f.add(bounding.getCenter(), new Vector3f(dx, dy, dz), new Vector3f()), bounding.getHalfExtent());
//		ArrayList<BoundingBox> collisions = location.getWorld().getCollisionEngine().handleCollisions(testBox);
//
//		onGround = false;
//		for (BoundingBox box : collisions) {
//
//			boolean xCheck = (int) Math.floor(box.getCenter().x) != Math.round(testBox.getCenter().x);
//			boolean yCheck = (int) Math.floor(box.getCenter().y) != Math.round(testBox.getCenter().y);
//			boolean zCheck = (int) Math.floor(box.getCenter().z) != Math.round(testBox.getCenter().z);
//
//			if (xCheck && !yCheck && !zCheck) {
//				dx = 0;
//			}
//
//			if (!xCheck && yCheck && !zCheck) {
//				setPosition(new Vector3f(getPosition().x, getPosition().y - bounding.getCollision(box).distance.y, getPosition().z));
//				dy = 0;
//				ySpeed = 0;
//				onGround = true;
//			}
//
//			if (!xCheck && !yCheck && zCheck) {
//				dz = 0;
//			}
//
//		}

		Vector3f movement = location.getWorld().getCollisionEngine().handleCollisions(getBounding(), Vector3f.add(getPosition(), new Vector3f(dx, dy, dz), new Vector3f()), new Vector3f(dx, dy, dz));
		super.increasePosition(movement.x, movement.y, movement.z);

		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTime(), 0);

//		bounding.setCenter(new Vector3f(getPosition().x, getPosition().y, getPosition().z));

	}

}
