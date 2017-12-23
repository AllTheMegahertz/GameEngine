package shaders;

import entities.Camera;
import entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Math;

/**
 * Created by AllTheMegahertz on 8/15/2017.
 */

public class StaticShader extends ShaderProgram {

	//TODO: Implement specular lighting

	private static final String VERTEX_FILE = "src/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.glsl";

	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationLightPosition;
	private int locationLightColor;
	private int locationUseFakeLighting;
	private int locationFogColor;
	private int locationNumberOfRows;
	private int locationOffset;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	protected void getAllUniformLocations() {
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationLightPosition = super.getUniformLocation("lightPosition");
		locationLightColor = super.getUniformLocation("lightColor");
		locationUseFakeLighting = super.getUniformLocation("useFakeLighting");
		locationFogColor = super.getUniformLocation("fogColor");
		locationNumberOfRows = super.getUniformLocation("numberOfRows");
		locationOffset = super.getUniformLocation("offset");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(locationTransformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(locationProjectionMatrix, projection);
	}

	public void loadViewMatrix(Camera camera) {
		super.loadMatrix(locationViewMatrix, Math.createViewMatrix(camera));
	}

	public void loadLight(Light light) {
		super.loadVector3f(locationLightPosition, light.getPosition());
		super.loadVector3f(locationLightColor, light.getColor());
	}

	public void loadUseFakeLighting(boolean b) {
		super.loadBoolean(locationUseFakeLighting, b);
	}

	public void loadFogColor(Vector3f fogColor) {
		super.loadVector3f(locationFogColor, fogColor);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadInt(locationNumberOfRows, numberOfRows);
	}

	public void loadOffset(Vector2f offset) {
		super.loadVector2f(locationOffset, offset);
	}

}
