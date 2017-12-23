package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

/**
 * Created by AllTheMegahertz on 8/15/2017.
 */

public class DisplayManager {

	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;
	private static final int FPS_CAP = 60;

	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay() {


		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayModeAndFullscreen(new DisplayMode(WIDTH, HEIGHT));
			Display.setFullscreen(true);
			Display.setTitle("GameEngine");
			Display.create(new PixelFormat(), attribs);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);

		lastFrameTime = System.nanoTime();

	}

	public static void updateDisplay() {

		Display.update();
		Display.sync(FPS_CAP);

		delta = System.nanoTime() - lastFrameTime;
		lastFrameTime = System.nanoTime();

	}

	public static float getFrameTime() {
		return delta / 1000000000f;
	}

	public static void closeDisplay() {
		Display.destroy();
	}

}
