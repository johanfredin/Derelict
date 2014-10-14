package se.jaygames.derelict.level;

import se.fredin.gdxtensions.assetmanagement.Assets;
import se.fredin.gdxtensions.level.TiledMapLevel;
import se.fredin.gdxtensions.utils.ScreenType;
import se.jaygames.derelict.object.Player;
import se.jaygames.derelict.screen.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Level extends TiledMapLevel<GameScreen> {
	
	private TiledMapTileLayer backgroundLayer;
	private TiledMapTileLayer foregroundLayer;
	private Player player;
	private Array<Rectangle> hardBlocks;
	
	
	@SuppressWarnings("unchecked")
	public Level(String levelName, GameScreen gameScreen) {
		super(levelName, gameScreen);
		this.camera = gameScreen.getCamera();
		this.map = (TiledMap) Assets.getInstance().get(levelName);
		this.backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");
		this.foregroundLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");
		
		MapObject spawnObject = map.getLayers().get("objects-32px").getObjects().get("spawn");
		float spawnX = spawnObject.getProperties().get("x", Float.class);
		float spawnY = spawnObject.getProperties().get("y", Float.class);
		Vector2 spawnPoint = new Vector2(spawnX, spawnY);
		this.player = new Player(spawnPoint);
		
		this.hardBlocks = new Array<Rectangle>();
		Array<RectangleMapObject> blockObjects = map.getLayers().get("collision").getObjects().getByType(RectangleMapObject.class);
		for(RectangleMapObject rectangleMapObject : blockObjects) {
			hardBlocks.add(rectangleMapObject.getRectangle());
		}
	}

	public void tick(float deltaTime) {
		player.tick(deltaTime, null);
		moveCamera(player.getPosition(), mapWidth, mapHeight);
	}

	public void render(SpriteBatch batch, OrthographicCamera camera) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mapRenderer.setView(camera);
		mapRenderer.renderTileLayer(backgroundLayer);
		player.render(batch);
		mapRenderer.renderTileLayer(foregroundLayer);
		batch.end();
	}

	public void dispose() {
		player.dispose();
		mapRenderer.dispose();
	}

	@Override
	public void switchLevel() {
	}

	@Override
	public void restart(boolean allowedToShowAdd) {
	}

	@Override
	public void end(ScreenType screenToGoTo, boolean allowedToShowAdd) {
	}

}
