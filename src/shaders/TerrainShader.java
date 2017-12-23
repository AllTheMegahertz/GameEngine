package shaders;

import entities.Camera;
import entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Math;

/**
 * Created by AllTheMegahertz on 8/17/2017.
 */

public class TerrainShader extends ShaderProgram {

	//TODO: Implement specular lighting

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.glsl";

	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationLightPosition;
	private int locationLightColor;
	private int locationFogColor;

	public TerrainShader() {
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
		locationFogColor = super.getUniformLocation("fogColor");
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

	public void loadFogColor(Vector3f fogColor) {
		super.loadVector3f(locationFogColor, fogColor);
	}

}
