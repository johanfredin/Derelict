package se.jaygames.derelict.utils;

import se.jaygames.derelict.Derelict;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Logger;

/**
 * Handles all the game assets
 * @authors Johan Fredin, Niklas Istenes 
 *
 */
public class Assets implements Disposable {

	private AssetManager manager;
	private static Assets assets;

	public static Assets getInstance() {
		if(assets == null) {
			assets = new Assets();
		}
		return assets;
	}

	/**
	 * Lets us retrieve an object from the loaded assets.
	 * Usually the object needs to be cast to the type 
	 * we want (like a Texture for example)
	 * @param fileName
	 * @return
	 */
	public Object get(String fileName) {
		return manager.get(fileName);
	}

	/**
	* Returns a new sprite and sets yDown to true.
	* @param path the path for the texture.
	* @return
	*/
	public Sprite getSprite(String path) {
		Sprite sprite = new Sprite((Texture) get(path));
		return sprite;
	}

	/**
	 * Returns a sprite and lets us flip the values we want
	 * @param path
	 * @param flipX whether to flip the x value
	 * @param flipY whether to flip the y value
	 * @return
	 */
	public Sprite getSprite(String path, boolean flipX, boolean flipY) {
		Sprite sprite = new Sprite((Texture) get(path));
		sprite.flip(flipX, flipY);
		return sprite;
	}

	/**
	 * Unloads all the assets and disposes of the asset manager.
	 * When this method is called the manager should not be used anymore.
	 */
	@Override
	public void dispose() {
		manager.dispose();
	}

	/**
	 * Unloads all assets currently handled by the manager
	 */
	public void clear() {
		manager.clear();
	}

	/**
	 * Loads all the assets required for the menu screens
	 */
	public void loadMenuAssets() {
		// Set the manager for the textures
		Texture.setAssetManager(manager);
	}

	/**
	 * Unloads all the menu screen assets
	 */
	public void unloadMenuAssets() {
		
	}

	/**
	 * Load the in game assets
	 */
	public void loadInGameAssets() {
		
		// Set the manager for the textures
		Texture.setAssetManager(manager);
	}
	
	/**
	 * Loads a tile map with the given index
	 * @param worldType the type of map to load (beach, grass etc)
	 * @param index the index of the map we want to load
	 * @param isTestLevel whether to retrieve the map from the test levels path or not
	 */
	public void loadTileMap(WorldType worldType, byte index, boolean isTestLevel) {
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	   
		Parameters p = new Parameters();
		p.textureMagFilter = TextureFilter.Nearest;
		p.textureMinFilter = TextureFilter.Nearest;
		
		String path = isTestLevel ? "levels/level-0" : "levels/" + worldType.getDisplayName().toLowerCase() + "/level-";
		manager.load(path + index + ".tmx", TiledMap.class, p);
		manager.finishLoading();
	}
	
	/**
	 * Unloads a tile map with the given index if the map is loaded
	 * @param worldType the type of map to load (beach, grass etc)
	 * @param index the index of the map we want to load
	 * @param isTestLevel whether to retrieve the map from the test levels path or not
	 */
	public void unloadTileMap(WorldType worldType, byte index, boolean isTestLevel) {
		try {
			String path = isTestLevel ? "levels/level-0" : "levels/" + worldType.getDisplayName().toLowerCase() + "/level-";
				manager.unload(path + index + ".tmx");
		} catch (GdxRuntimeException gre) {
			Logger l = new Logger("IOError", Logger.ERROR);
			l.error(gre.getLocalizedMessage());
		}
	}
	
	/**
	 * Unload the in game assets
	 */
	public void unloadInGameAssets() {
	}
	
	private Assets() {
		manager = new AssetManager();
		if(Derelict.DEVELOPER_MODE) {
			loadInGameAssets();
			loadMenuAssets();
			Texture.setAssetManager(manager);
			manager.finishLoading();
		}
	}
	
	public int getProgress() {
		return (int) (manager.getProgress() * 100);
	}

	public boolean isLoading() {
		return !manager.update();
	}
	
	public boolean isLoaded(String path) {
		return manager.isLoaded(path);
	}
	
	public void finishLoading() {
		manager.finishLoading();
		Texture.setAssetManager(manager);
	}

}