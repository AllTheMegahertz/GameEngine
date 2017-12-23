package toolbox;

import blocks.Block;
import blocks.BlockType;
import entities.Camera;
import entities.Player;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by AllTheMegahertz on 8/16/2017.
 */

public class Math {

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		matrix.translate(translation, matrix, matrix);

		matrix.rotate((float) java.lang.Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		matrix.rotate((float) java.lang.Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		matrix.rotate((float) java.lang.Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);

		matrix.scale(new Vector3f(scale, scale, scale), matrix, matrix);

		return matrix;

	}

	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Camera camera) {

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		matrix.rotate((float) java.lang.Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), matrix, matrix);
		matrix.rotate((float) java.lang.Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), matrix, matrix);
		matrix.rotate((float) java.lang.Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), matrix, matrix);

		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);

		matrix.translate(negativeCameraPos, matrix, matrix);

		return matrix;

	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {

		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;

	}

	public static boolean checkCollision(Block block, Player player) {

		if (block.getBlockType() == BlockType.Air) {
			return false;
		}

		//check the X axis
		if(java.lang.Math.abs(block.getPosition().getX() - player.getPosition().getX()) < 1 + 1)
		{
			//check the Y axis
			if(java.lang.Math.abs(block.getPosition().getY() - player.getPosition().getY()) < 1 + 2)
			{
				//check the Z axis
				if(java.lang.Math.abs(block.getPosition().getZ() - player.getPosition().getZ()) < 1 + 1)
				{
					return true;
				}
			}
		}

		return false;

//		return  java.lang.Math.abs(block.getPosition().getX() - player.getPosition().x) < 1 + 0.75f &&
//				java.lang.Math.abs(block.getPosition().getY() - player.getPosition().y) < 1 + 2 &&
//				java.lang.Math.abs(block.getPosition().getZ() - player.getPosition().z) < 1 + 0.75f;

	}

}
