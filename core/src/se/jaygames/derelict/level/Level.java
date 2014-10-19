package se.jaygames.derelict.level;

import se.fredin.gdxtensions.level.TiledMapLevel;
import se.fredin.gdxtensions.utils.Dialog;
import se.fredin.gdxtensions.utils.ScreenType;
import se.fredin.gdxtensions.utils.ShapeRendererPlus;
import se.jaygames.derelict.object.Player;
import se.jaygames.derelict.screen.GameScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Level extends TiledMapLevel<GameScreen> {
	
	private ShapeRendererPlus shapeRendererPlus;
	private TiledMapTileLayer backgroundLayer;
	private TiledMapTileLayer foregroundLayer;
	private TiledMapTileLayer mainLayer;
	private Player player;
	private Array<Rectangle> hardBlocks;
	private Dialog dialog;
	
	public Level(String levelName, GameScreen gameScreen) {
		super(levelName, gameScreen);
		this.backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
		this.foregroundLayer = (TiledMapTileLayer) map.getLayers().get("foreground");
		this.mainLayer = (TiledMapTileLayer) map.getLayers().get("main");
		
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
		this.shapeRendererPlus = new ShapeRendererPlus(camera, ShapeType.Line);
		camera.setBounds(mapWidth, mapHeight);
		
		this.dialog = new Dialog("I believe whatever doesn't kill you simply makes you stranger", .05f, 20, false);
	}

	@Override
	public void tick(float deltaTime) {
		player.tick(deltaTime);
		camera.follow(player.getPosition());
		dialog.tick(deltaTime, "Hello Wayne");
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mapRenderer.setView(camera);
		mapRenderer.renderTileLayer(backgroundLayer);
		mapRenderer.renderTileLayer(mainLayer);
		player.render(batch);
		mapRenderer.renderTileLayer(foregroundLayer);
		batch.end();
		
		shapeRendererPlus.setProjectionMatrix(camera.combined);
		shapeRendererPlus.begin(ShapeType.Line);
		shapeRendererPlus.renderBoundaries(player.getBounds());
		shapeRendererPlus.renderBoundaries(this.hardBlocks);
		shapeRendererPlus.end();
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
