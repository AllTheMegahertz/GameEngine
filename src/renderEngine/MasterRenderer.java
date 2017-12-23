package renderEngine;

import blocks.Block;
import blocks.BlockPosition;
import blocks.BlockType;
import blocks.Face;
import entities.*;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrain.OldTerrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AllTheMegahertz on 8/17/2017.
 */

public class MasterRenderer {

	private static final float FOV = 90;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private static final Vector3f FOG_COLOR = new Vector3f(0.5f, 0.5f, 0.5f);

	private Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();
	private EntityRenderer entityRenderer;

	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;


	public MasterRenderer() {

		createProjectionMatrix();
		this.entityRenderer = new EntityRenderer(shader, projectionMatrix);
		this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);

	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<OldTerrain> oldTerrains = new ArrayList<OldTerrain>();

	public void render(Light light, Camera camera) {

		prepare();

		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		shader.loadFogColor(FOG_COLOR);

		entityRenderer.render(entities);

		shader.stop();
		entities.clear();

		terrainShader.start();
		terrainShader.loadLight(light);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadFogColor(FOG_COLOR);

		terrainRenderer.render(oldTerrains);

		terrainShader.stop();
		oldTerrains.clear();


	}

	public void processEntity(Entity entity) {

			TexturedModel model = entity.getModel();
			List<Entity> batch = entities.get(model);

			if (batch != null) {
				batch.add(entity);
			}
			else {
				List<Entity> newBatch = new ArrayList<Entity>();
				newBatch.add(entity);
				entities.put(model, newBatch);
			}

	}

	public void processTerrain(OldTerrain oldTerrain) {
			oldTerrains.add(oldTerrain);
	}

	//Prepares OpenGL to render the game by clearing the color from the last frame
	public void prepare() {

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		//Sets background color
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(FOG_COLOR.x, FOG_COLOR.y, FOG_COLOR.z, 1);

	}

	public void processWorld(World world) {

		for (Entity entity : world.getEntities()) {
			processEntity(entity);
		}

		for (Block block : world.getOpenBlocks()) {
			for (Face face : block.getFaces()) {
				processEntity(face.getEntity());
			}
		}

	}

	//Don't worry about this stuff
	private void createProjectionMatrix() {

		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / java.lang.Math.tan(java.lang.Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;

	}

	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
	}

}
