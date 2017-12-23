package renderEngine;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import shaders.StaticShader;
import toolbox.Math;

import java.util.List;
import java.util.Map;

/**
 * Created by AllTheMegahertz on 8/15/2017.
 */

public class EntityRenderer {

	private StaticShader shader;

	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {

		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}

	public void render(Map<TexturedModel, List<Entity>> entities) {

		for (TexturedModel model : entities.keySet()) {

			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);

			for (Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}

			unbindTexturedModel();

		}

	}

	public void prepareTexturedModel(TexturedModel model) {

		RawModel rawModel = model.getRawModel();

		//Binds VAO of the given model
		GL30.glBindVertexArray(rawModel.getVaoID());
		//Enabling attribute list 0 (Positional Data)
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		shader.loadNumberOfRows(model.getTexture().getNumberOfRows());

		if (model.getTexture().isTransparent()) {
			MasterRenderer.disableCulling();
		}
		else {
			MasterRenderer.enableCulling();
		}

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());

	}

	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Math.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadUseFakeLighting(entity.getModel().getTexture().isUseFakeLighting());

		int column = entity.getTextureIndex() % entity.getModel().getTexture().getNumberOfRows();
		float xOffset = (float) column / (float) entity.getModel().getTexture().getNumberOfRows();
		int row = entity.getTextureIndex() / entity.getModel().getTexture().getNumberOfRows();
		float yOffset = (float) row / (float) entity.getModel().getTexture().getNumberOfRows();

		shader.loadOffset(new Vector2f(xOffset, yOffset));
	}

}
