package se.jaygames.derelict.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

public abstract class BaseScreen implements Screen {

	public static final short VIEWPORT_WIDTH = 400;
	public static final short VIEWPORT_HEIGHT = 240;
	
	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected Game game;
	
	
	public BaseScreen(Game game) {
		this.game = game;
		this.camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		this.batch = new SpriteBatch();
	}
	
	@Override
	public void resize(int width, int height) {
		//very useful and easy function to get preferred width and height and still keeping the same aspect ratio :)
		Vector2 size = Scaling.fillY.apply(width, height, width, VIEWPORT_HEIGHT);

		camera.setToOrtho(false, size.x, size.y);
		camera.update();
	}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		batch.dispose();
		game.dispose();
	}

}
