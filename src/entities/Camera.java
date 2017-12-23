package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import java.security.Key;

/**
 * Created by AllTheMegahertz on 8/16/2017.
 */

public class Camera {

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 10;
	private float yaw = 0;
	private float roll = 0;

	private float distanceFromPlayer = 0;
	private float angleAroundPlayer = 0;
	private float heightOffset = 1.85f;

	private Player player;

	public Camera(Player player) {
		this.player = player;
		Mouse.setGrabbed(true);
	}

	public void move() {

		distanceFromPlayer -= Mouse.getDWheel() * 0.025f;
		distanceFromPlayer = Math.max(0, distanceFromPlayer);

		pitch -= Mouse.getDY() * 0.08f;
		pitch = Math.min(90, Math.max(-90 ,pitch));


		float verticalDistance = distanceFromPlayer * (float) Math.sin(Math.toRadians(pitch));
		float horizontalDistance = distanceFromPlayer * (float) Math.cos(Math.toRadians(pitch));

		calculateCameraPosition(verticalDistance, horizontalDistance);

		yaw = 180 - angleAroundPlayer - player.getRotY();

	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calculateCameraPosition(float verticalDistance, float horizontalDistance) {

		position.x = player.getPosition().x - horizontalDistance * (float) Math.sin(Math.toRadians(angleAroundPlayer + player.getRotY()));
		position.y = player.getPosition().y + verticalDistance + heightOffset;
		position.z = player.getPosition().z - horizontalDistance * (float) Math.cos(Math.toRadians(angleAroundPlayer + player.getRotY()));

	}

}
