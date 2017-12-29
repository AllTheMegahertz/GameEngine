package entities;

import collision.BoundingBox;
import collision.Collider;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by AllTheMegahertz on 8/18/2017.
 */

public class Player extends PhysicsObject implements Collider {

	private static final float MOVEMENT_SPEED = 7;
	private static final float JUMP_POWER = 0.15f;

	//TODO: Implement a location class so that "world" does not need to be separately passed to the constructor
	public Player(TexturedModel model, Location location, float rotX, float rotY, float rotZ, float scale) {
		super(model, new BoundingBox(location.getPosition(), new Vector3f(0.5f, 0.5f, 0.5f)), location.getPosition(), location.getWorld(), rotX, rotY, rotZ, scale);
	}

	public void move() {
		checkInputs();
		super.move();
	}

	private void checkInputs() {

		float movementSpeed = MOVEMENT_SPEED;

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			movementSpeed *= 1.5;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			super.zSpeed = movementSpeed;
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
