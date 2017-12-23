package renderEngine;

import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shaders.TerrainShader;
import terrain.OldTerrain;
import toolbox.Math;

import java.util.List;

/**
 * Created by AllTheMegahertz on 8/17/2017.
 */

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {

		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();

	}

	public void render (List<OldTerrain> oldTerrains) {

		for (OldTerrain oldTerrain : oldTerrains) {

			prepareTerrain(oldTerrain);
			loadModelMatrix(oldTerrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, oldTerrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTerrain();

		}

	}

	public void prepareTerrain(OldTerrain oldTerrain) {

		RawModel rawModel = oldTerrain.getModel();

		//Binds VAO of the given model
		GL30.glBindVertexArray(rawModel.getVaoID());
		//Enabling attribute list 0 (Positional Data)
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, oldTerrain.getTexture().getTextureID());

	}

	private void unbindTerrain() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void loadModelMatrix(OldTerrain oldTerrain) {
		Matrix4f transformationMatrix = Math.createTransformationMatrix(new Vector3f(oldTerrain.getX(), 0, oldTerrain.getZ()), 0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}

}
