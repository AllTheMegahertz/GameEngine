package guis;

import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.Loader;

import java.util.List;

/**
 * Created by AllTheMegahertz on 9/1/2017.
 */

public class GuiRenderer {

	private final RawModel quad;
	private GuiShader shader;

	public GuiRenderer(Loader loader) {

		float[] positions = new float[] {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions);

		shader = new GuiShader();

	}

	public void render(List<GuiTexture> guis) {

		shader.start();

		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		for (GuiTexture guiTexture : guis) {

			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, guiTexture.getTexture());

			Matrix4f matrix = toolbox.Math.createTransformationMatrix(guiTexture.getPosition(), guiTexture.getScale());
			shader.loadTransformation(matrix);

			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

		}

		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

		shader.stop();

	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
