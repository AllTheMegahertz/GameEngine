package renderEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.newdawn.slick.opengl.renderer.SGL.GL_LINEAR;
import static org.newdawn.slick.opengl.renderer.SGL.GL_TEXTURE_MIN_FILTER;

/**
 * Created by AllTheMegahertz on 8/15/2017.
 */

public class Loader {

	//Lists of VAOs and VBOs in order to delete them on closing
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();

	//Returns a RawModel object when passed an array of raw positional data of all the vertices in a model
	public RawModel loadToVAO(float[] positions, int[] indices, float[] textureCoords, float[] normals) {

		int vaoID = createVAO();
		bindIndiciesBuffer(indices);
		//Stores positional data in attribute list at position 0
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();

		return new RawModel(vaoID, indices.length);

	}

	public RawModel loadToVAO(float[] positions) {

		int vaoID = createVAO();
		storeDataInAttributeList(0, 2, positions);
		unbindVAO();

		return new RawModel(vaoID, positions.length / 2);

	}

	public int loadTexture(String fileName) {

		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
//			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		int textureID = texture.getTextureID();
		textures.add(textureID);

		return textureID;

	}

	//Deletes all VAOs and VBOs
	public void cleanUp() {

		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}

		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}

		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}

	}

	//Creates and activates an empty Vertex Array Object, adds it to the list, and returns its ID
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		//Creates empty Vertex Buffer Object and stores its ID
		int vboID = GL15.glGenBuffers();
		//Adds the new VBO to the list
		vbos.add(vboID);
		//Binds VBO by passing its type ID
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		//Stores data in VBO as an Array Buffer and specifies that the data is final
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		/* Stores VBO into VAO in position attributeNumber. The length of each vertex is 3, because they are 3D vectors. The type of data is float. The data is not normalized.
		There is no distance between each vertex, and there is no offset, because it should start at the beginning of the data. */
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		//Unbinds current VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	//Unbinds VAO
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private void bindIndiciesBuffer(int[] indicies) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indicies);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	//Creates a Float Buffer and stores data in it from a passed float array
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); //Makes the buffer the same length as the passed float array
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
