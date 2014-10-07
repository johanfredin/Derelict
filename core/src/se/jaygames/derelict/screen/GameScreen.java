package se.jaygames.derelict.screen;

import se.jaygames.derelict.level.Level;
import se.jaygames.derelict.screen.ingame.Dialog;
import se.jaygames.derelict.utils.Assets;
import se.jaygames.derelict.utils.WorldType;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

/**
 * The GameScreen is the screen responsible for all the in game content.
 * As soon as user starts a new level this screen gets called.
 * @author Johan Fredin, Niklas Istenes
 */
public class GameScreen extends BaseScreen {
	
	private byte levelIndex = 0;
	private State state = State.PLAYING;
	private Level level;
	private WorldType worldType = WorldType.START;
	
	/**
	 * Different states that this screen can have
	 * @author Johan Fredin
	 */
	private enum State {
		PLAYING,
		RETURN_TO_LEVEL_SELECT,
		RETURN_TO_MYCOW,
		REPLAY,
		SWITCHING_LEVEL, 
		RETURN_TO_CREDITS
	}

	/**
	 * Create a new game screen and sets up what kind of world we will be playing in.
	 * Also loads all the in game assets required
	 * @param game
	 * @param levelIndex which level we want to start playing
	 */
	public GameScreen(Game game, byte levelIndex) {
		super(game);
		this.levelIndex = levelIndex;
//		AudioUtils.getInstance().playMusic(nameOfTrackToPlay, true);
		setupLevel();
	}
	
	/** Retrieve the game instance */
	public Game getGame() {
		return this.game;
	}
	
	public byte getLevelIndex() {
		return levelIndex;
	}

	public boolean isFinalLevel() {
		return levelIndex >= 10;
	}

	public void nextLevel() {
		state = State.SWITCHING_LEVEL;
		levelIndex++;		
	}
	
	public void replayLevel() {
		state = State.REPLAY;
	}
	
	public void clearLevel(ScreenType screenToGoTO) {
		state = State.RETURN_TO_CREDITS;
	}
	
	@Override
	public void render(float delta) {
		if (delta > 0.1f) { 
			delta = 0.1f;
		}
		
		if(state == State.PLAYING) {
			level.tick(delta);
			level.render(batch, camera);
		} else {
			switch(state) {
			case RETURN_TO_LEVEL_SELECT:
				break;
			case RETURN_TO_MYCOW:
				break;
			case RETURN_TO_CREDITS:
				break;
			default:
				this.switchLevel();
				break;
			}
		}
	}
	
	@Override
	public void resize(int width, int height) {
		//very useful and easy function to get preferred width and height and still keeping the same aspect ratio :)
		Vector2 size = Scaling.fill.apply(width, height, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

		camera.setToOrtho(false, size.x, size.y);
		camera.update();
	}

	@Override
	public void pause() {
		super.pause();
		if(Dialog.screenTapped) {
			Dialog.isPaused = true;
		}
	}

	@Override
	public void resume() {
		super.resume();
		
		if(Dialog.screenTapped) {
			Dialog.isPaused = true;
		}
	}

	@Override
	public void hide() {
		level.dispose();
		Assets.getInstance().unloadTileMap(worldType, levelIndex, true);
		Gdx.app.log(this.getClass().getSimpleName(), "hide called");
	}
	
	/**
	 * Disposes current level and starts a new one.
	 * Called when we are switching or replaying a level
	 * If we are moving to the next level we will unload the current tiled map
	 * and load the next. If we  are replaying the level we will reload the tiled map
	 * this is due to a bug with doors going up 32 pixels on restart.
	 */
	private void switchLevel() {
		level.dispose();	
		
		byte indexOfLevelToUnload = 0;
		switch(state) {
		case REPLAY:
			indexOfLevelToUnload = levelIndex;
			break;
		case SWITCHING_LEVEL:
			indexOfLevelToUnload = (byte)(levelIndex - 1);
		default:
			break;
		} 
		
		Assets.getInstance().unloadTileMap(worldType, indexOfLevelToUnload, true);
		setupLevel();
	}
	
	/**
	 * Reloads the tilemap and reinstantiates the level, makes a switch on what world we are at as well
	 */
	private void setupLevel() {
		Assets.getInstance().loadTileMap(worldType, levelIndex, true);
		level = new Level("levels/" + worldType.getDisplayName() + "/level-" + levelIndex + ".tmx", this);
		state = State.PLAYING;
	}

	public SpriteBatch getSpriteBatch() {
		return this.batch;
	}

	public OrthographicCamera getCamera() {
		return this.camera;
	}
	
}
