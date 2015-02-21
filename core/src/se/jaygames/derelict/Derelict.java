package se.jaygames.derelict;
import se.fredin.gdxtensions.assetmanagement.Assets;
import se.jaygames.derelict.screen.GameScreen;
import se.jaygames.derelict.utils.Loader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Starter class and Application Listener
 * @author Johan Fredin, Niklas Istenes
 *
 */
public class Derelict extends Game {
	
	@Override
	public void create() {	
		// We want to have control over the back button!
		Gdx.input.setCatchBackKey(true);
		Assets.LOAD_SYNCHRONOUSLY = true;
		Loader.load();
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

	
}
