package entities;

import blocks.BlockPosition;
import blocks.BlockType;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 * Created by AllTheMegahertz on 8/18/2017.
 */

public class Player extends Entity {

	private static final float MOVEMENT_SPEED = 10;
	private static final float TURN_SPEED = 100;
	private static final float GRAVITY = -0.5f;
	private static final float JUMP_POWER = 0.2f;

	private float xSpeed = 0;
	private float ySpeed = 0;
	private float zSpeed = 0;
	private float currentTurnSpeed = 0;

	private World world;

	//TODO: Implement a location class so that "world" does not need to be separately passed to the constructor
	public Player(TexturedModel model, Vector3f position, World world, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.world = world;
	}

	public void move() {

		Vector3f currentPosition = getPosition();

		checkInputs();

		setRotY(getRotY() - Mouse.getDX() * 0.1f);

		float xDistance = xSpeed * DisplayManager.getFrameTime();
		float zDistance = zSpeed * DisplayManager.getFrameTime();

		if (checkXCollisions()) {
			xSpeed = 0;
			xDistance = 0;
		}

		if (checkYCollisions()) {
			if (ySpeed < 0) {
				ySpeed = 0;
			}
		}

		if (checkZCollisions()) {
			zSpeed = 0;
			zDistance = 0;
		}

		super.increasePosition(
				xDistance * (float) Math.cos(Math.toRadians(getRotY())) + zDistance * (float) Math.sin(Math.toRadians(getRotY())),
				ySpeed,
				zDistance * (float) Math.cos(Math.toRadians(getRotY())) - xDistance * (float) Math.sin(Math.toRadians(getRotY()))
		);

		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTime(), 0);

		ySpeed += GRAVITY * DisplayManager.getFrameTime();

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

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir()) {
			ySpeed = JUMP_POWER;
		}


	}

	private boolean isInAir() {
		return world.getBlock(new BlockPosition((int) getPosition().x, (int) getPosition().y, (int) getPosition().z)).getBlockType() == BlockType.Air;
	}

	private boolean checkXCollisions() {
		return  toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x - 1, (int) getPosition().y + 1, (int) getPosition().z)), this) ||
				toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x + 1, (int) getPosition().y + 1, (int) getPosition().z)), this) ||
				toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x - 1, (int) getPosition().y + 2, (int) getPosition().z)), this) ||
				toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x + 1, (int) getPosition().y + 2, (int) getPosition().z)), this);
	}

	private boolean checkYCollisions() {

		float y = (float) Math.ceil(getPosition().y);

		return  toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x, (int) y, (int) getPosition().z)), this) ||
				toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x, (int) y + 2, (int) getPosition().z)), this);
	}

	private boolean checkZCollisions() {
		return  toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x, (int) getPosition().y + 1, (int) getPosition().z - 1)), this) ||
				toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x, (int) getPosition().y + 1, (int) getPosition().z + 1)), this) ||
				toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x, (int) getPosition().y + 2, (int) getPosition().z - 1)), this) ||
				toolbox.Math.checkCollision(world.getBlock(new BlockPosition((int) getPosition().x, (int) getPosition().y + 2, (int) getPosition().z + 1)), this);
	}

}
