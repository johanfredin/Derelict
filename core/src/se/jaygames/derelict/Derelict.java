package se.jaygames.derelict;
import se.jaygames.derelict.screen.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Starter class and Application Listener
 * @author Johan Fredin, Niklas Istenes
 *
 */
public class Derelict extends Game {
	
	/** This is the resolution limit (width * height) for deciding if device is low end or not */
	private static final int LOW_END_LIMIT = 480 * 320;
	
	/** Renders bounding boxes and makes the cow fly */
	public static boolean FREE_FLYING_MODE = false;
	
	/** Renders the bounding boxes but wont let us fly */
	public static boolean SHOULD_RENDER_BOUNDING_BOXES = false;
	
	/** If true we will use the levels from our test directory */
	public static final boolean USE_TEST_LEVELS = false;
	
	/** Makes the cow invincible */
	public static boolean GOD_MODE = true;

	/** Mutes all sounds and music */
	public static boolean MUTE_ALL = true;

	/** Lets us start the game without the load screens */
	public static boolean DEVELOPER_MODE = true;
	
	/** Used to find out what brand we are running */
	public static String androidBrandName;
	
	@Override
	public void create() {	
		// We want to have control over the back button!
		Gdx.input.setCatchBackKey(true);
		setScreen(new GameScreen(this, (byte)0));
	}
	
	@Override
	public void dispose() {
		Gdx.app.log("Derelict " + this.getClass().getSimpleName(), "\t dispose called");
		super.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	/**
	 * Used to determine if we are running on a low end device.
	 * We set the limit for low end to be 480 * 320 in width and height.
	 * @return
	 */
	public static boolean isLowEndDevice() {
		return Gdx.graphics.getWidth() * Gdx.graphics.getHeight() <= LOW_END_LIMIT;
	}
	
	/**
	 * Sony devices have a weird way of loosing texture content. We need to approach these devices differently
	 * @return
	 */
	public static boolean isSonyDevice() {
		return androidBrandName != null && androidBrandName.equalsIgnoreCase("Sony");
	}
	
	@SuppressWarnings("rawtypes")
	public static void log(Class clazz, String message) {
		System.out.println(clazz.getSimpleName() + "\t" + message);
	}
	
}
