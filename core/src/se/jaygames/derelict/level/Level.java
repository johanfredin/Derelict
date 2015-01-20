package se.jaygames.derelict.level;

import se.fredin.gdxtensions.collision.CollisionHandler;
import se.fredin.gdxtensions.level.TiledMapLevel;
import se.fredin.gdxtensions.screen.ingame.Dialogs;
import se.fredin.gdxtensions.utils.ScreenType;
import se.fredin.gdxtensions.utils.ShapeRendererPlus;
import se.fredin.gdxtensions.xml.DialogXMLParser;
import se.jaygames.derelict.object.Player;
import se.jaygames.derelict.screen.GameScreen;
import se.jaygames.derelict.utils.Loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	private Dialogs dialogs;
	
	public Level(String levelName, GameScreen gameScreen) {
		super(levelName, gameScreen);
		this.backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
		this.foregroundLayer = (TiledMapTileLayer) map.getLayers().get("foreground");
		this.mainLayer = (TiledMapTileLayer) map.getLayers().get("main");
		
		MapObject spawnObject = map.getLayers().get("objects-32px").getObjects().get("spawn");
		float spawnX = spawnObject.getProperties().get("x", Float.class);
		float spawnY = spawnObject.getProperties().get("y", Float.class);
		Vector2 spawnPoint = new Vector2(spawnX, spawnY);
		
		this.player = new Player(spawnPoint, baseInput);
		this.hardBlocks = new Array<Rectangle>();
		Array<RectangleMapObject> blockObjects = map.getLayers().get("collision").getObjects().getByType(RectangleMapObject.class);
		for(RectangleMapObject rectangleMapObject : blockObjects) {
			hardBlocks.add(rectangleMapObject.getRectangle());
		}
		this.shapeRendererPlus = new ShapeRendererPlus(camera, ShapeType.Line);
//		this.camera.setBounds(mapWidth, mapHeight);
		DialogXMLParser dialogXMLParser = new DialogXMLParser("dialogs/example.xml");
		this.dialogs = new Dialogs(baseInput, dialogXMLParser.getXMLDialog("meet-hero-one"), Loader.PACKFILES_PATH + "dialog.pack", Loader.FONT_PATH + "font.fnt", Color.RED);
		
		this.inputMultiplexer.addProcessor(baseInput);
		this.inputMultiplexer.addProcessor(dialogs.getStage());
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		this.collisionHandler = new CollisionHandler(player);
	}

	@Override
	public void tick(float deltaTime) {
		player.tick(deltaTime);
		camera.follow(player.getPosition());
		dialogs.tick(deltaTime);
		collisionHandler.checkForCollision(hardBlocks);
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
		
		dialogs.render();
	}
	
	@Override
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
