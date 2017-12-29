package entities;

import blocks.BlockPosition;
import blocks.BlockType;
import collision.BoundingBox;
import collision.Collider;
import collision.Collision;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AllTheMegahertz on 8/18/2017.
 */

public class Player extends Entity implements Collider {

	private static final float MOVEMENT_SPEED = 10;
	private static final float TURN_SPEED = 100;
	private static final float GRAVITY = -0.5f;
	private static final float JUMP_POWER = 0.2f;

	private float xSpeed = 0;
	private float ySpeed = 0;
	private float zSpeed = 0;
	private float currentTurnSpeed = 0;

	private World world;
	private BoundingBox boundingBox;
	private ArrayList<BoundingBox> collisions;
	private HashMap<BoundingBox, Collision> previousCollisions;
	private boolean onGround = false;

	//TODO: Implement a location class so that "world" does not need to be separately passed to the constructor
	public Player(TexturedModel model, Vector3f position, World world, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.world = world;
		boundingBox = new BoundingBox(new Vector3f(getPosition().x, getPosition().y, getPosition().z), new Vector3f(0.5f, 0.5f, 0.5f));
		collisions = new ArrayList<>();
		previousCollisions = new HashMap<>();
	}

	public void move() {

		checkInputs();

		setRotY(getRotY() - Mouse.getDX() * 0.1f);

		float xDistance = xSpeed * DisplayManager.getFrameTime();
		float zDistance = zSpeed * DisplayManager.getFrameTime();

		float dx = xDistance * (float) Math.cos(Math.toRadians(getRotY())) + zDistance * (float) Math.sin(Math.toRadians(getRotY()));
		float dz = zDistance * (float) Math.cos(Math.toRadians(getRotY())) - xDistance * (float) Math.sin(Math.toRadians(getRotY()));

		if (!onGround) {
			ySpeed += GRAVITY * DisplayManager.getFrameTime();
		}

		BoundingBox testBox = new BoundingBox(Vector3f.add(boundingBox.getCenter(), new Vector3f(dx, ySpeed, dz), new Vector3f()), boundingBox.getHalfExtent());
		collisions = world.getColliderEngine().handleCollisions(testBox);

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

		for (BoundingBox box : collisions) {
			previousCollisions.put(box, boundingBox.getCollision(box));
		}

	}

	private void checkInputs() {

		float movementSpeed = MOVEMENT_SPEED;

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			movementSpeed *= 2;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			zSpeed = movementSpeed;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			zSpeed = -movementSpeed;
		}
		else {
			zSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xSpeed = movementSpeed;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xSpeed = -movementSpeed;
		}
		else {
			xSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && onGround) {
			ySpeed = JUMP_POWER;
		}

	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

}
