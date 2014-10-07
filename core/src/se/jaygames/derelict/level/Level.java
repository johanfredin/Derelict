package se.jaygames.derelict.level;

import se.jaygames.derelict.object.Player;
import se.jaygames.derelict.screen.GameScreen;
import se.jaygames.derelict.utils.Assets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Level {
	
	private OrthographicCamera camera;
	private TiledMap map;
	private TiledMapTileLayer backgroundLayer;
	private TiledMapTileLayer foregroundLayer;
	private OrthogonalTiledMapRenderer mapRenderer;
	private Player player;
	
	
	public Level(String string, GameScreen gameScreen) {
		this.camera = gameScreen.getCamera();
		this.map = (TiledMap) Assets.getInstance().get(string);
		this.backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");
		this.foregroundLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, gameScreen.getSpriteBatch());
		
		MapObject spawnObject = map.getLayers().get("objects-32px").getObjects().get("spawn");
		float spawnX = spawnObject.getProperties().get("x", Float.class);
		float spawnY = spawnObject.getProperties().get("y", Float.class);
		Vector2 spawnPoint = new Vector2(spawnX, spawnY);
		this.player = new Player(spawnPoint);
	}

	public void tick(float deltaTime) {
		player.tick(deltaTime);
		moveCamera(player, deltaTime);
	}

	private void moveCamera(Player player2, float deltaTime) {
		float x = player.getPosition().x;
		float y = player.getPosition().y;
		camera.position.set(x, y, 0);
	}

	public void render(SpriteBatch batch, OrthographicCamera camera) {

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mapRenderer.setView(camera);
		mapRenderer.renderTileLayer(backgroundLayer);
		player.render(camera, batch, null);
		mapRenderer.renderTileLayer(foregroundLayer);
		batch.end();
		
		camera.update();
	}

	public void dispose() {
		player.dispose();
		mapRenderer.dispose();
	}

}
