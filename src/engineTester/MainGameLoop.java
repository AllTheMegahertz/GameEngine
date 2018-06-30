package engineTester;

import collision.CollisionEngine;
import entities.*;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import terrain.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 8/15/2017.
 */

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();

		MasterRenderer renderer = new MasterRenderer();

		Loader loader = new Loader();

		CollisionEngine collisionEngine = new CollisionEngine();
		World world = new World(collisionEngine);

		ModelTexture texture = new ModelTexture(loader.loadTexture("white"));

		ModelData treeData = OBJLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getIndices(), treeData.getTextureCoords(), treeData.getNormals());
		TexturedModel tree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("lowPolyTree")));

		Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));

//		OldTerrain terrain  = new OldTerrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
//		OldTerrain terrain2  = new OldTerrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));

		ModelData playerData = OBJLoader.loadOBJ("cube");
		RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getIndices(), playerData.getTextureCoords(), playerData.getNormals());
		Player player = new Player(new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture"))), new Location(world, 20, 40, 20, 0, 0), 0, 0, 0, 1);

//		world.addBlock(new Block(BlockType.Dirt, new BlockPosition(new int[] {150, 1, 219})));
//		world.addBlock(new Block(BlockType.Dirt, new BlockPosition(new int[] {150, 1, 220})));
//		world.addBlock(new Block(BlockType.Dirt, new BlockPosition(new int[] {150, 2, 220})));

		ArrayList<GuiTexture> guis = new ArrayList<GuiTexture>();
		//guis.add(new GuiTexture(loader.loadTexture("crosshair"), new Vector2f(0, 0), new Vector2f(0.014f, 0.025f)));

		GuiRenderer guiRenderer = new GuiRenderer(loader);

		Camera camera = new Camera(player);

		Terrain terrain = new Terrain(0, 0);
		Terrain terrain2 = new Terrain(1, 0);
		Terrain terrain3 = new Terrain(2, 0);

		world.addBlocks(terrain.getBlocks());
//		world.addBlocks(terrain2.getBlocks());
//		world.addBlocks(terrain3.getBlocks());

		//Game Loop
		while (!Display.isCloseRequested()) {

			player.move();
			camera.move();

			renderer.processWorld(world);
//			renderer.processEntity(player);

			renderer.render(light, camera);
			guiRenderer.render(guis);

			DisplayManager.updateDisplay();

		}

		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
