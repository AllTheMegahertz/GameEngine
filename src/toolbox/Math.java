package toolbox;

import blocks.Block;
import blocks.BlockType;
import entities.Camera;
import entities.Player;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 8/16/2017.
 */

public class Math {

	public static float max(float[] arr) {

		float max = arr[0];
		for (int i = 0; i < arr.length; i++) {
			max = java.lang.Math.max(max, arr[i]);
		}

		return max;

	}

	public static float min(float[] arr) {

		float min = arr[0];
		for (int i = 0; i < arr.length; i++) {
			min = java.lang.Math.min(min, arr[i]);
		}

		return min;

	}

	public static Vector3f max(ArrayList<Vector3f> vectors) {

		float[] x = new float[vectors.size()];
		float[] y = new float[vectors.size()];
		float[] z = new float[vectors.size()];
		for (int i = 0; i < vectors.size(); i++) {
			x[i] = vectors.get(i).x;
			y[i] = vectors.get(i).y;
			z[i] = vectors.get(i).z;
		}

		return new Vector3f(max(x), max(y), max(z));

	}

	public static Vector3f min(ArrayList<Vector3f> vectors) {

		float[] x = new float[vectors.size()];
		float[] y = new float[vectors.size()];
		float[] z = new float[vectors.size()];
		for (int i = 0; i < vectors.size(); i++) {
			x[i] = vectors.get(i).x;
			y[i] = vectors.get(i).y;
			z[i] = vectors.get(i).z;
		}

		return new Vector3f(min(x), min(y), min(z));

	}

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


}
