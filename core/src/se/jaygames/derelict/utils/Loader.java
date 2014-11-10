package se.jaygames.derelict.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import se.fredin.gdxtensions.assetmanagement.Assets;

public class Loader {

	public static final String PACKFILES_PATH = "sprites/objects/utils/dialog/";
	public static final String TEST_PATH = "sprites/objects/test/";
	
	public static void load() {
		Assets a = Assets.getInstance();
		a.load(TEST_PATH + "hero.png", Texture.class);
		a.load(PACKFILES_PATH + "dialog.pack", TextureAtlas.class);
	}
	
	public static void unload() {
		Assets a = Assets.getInstance();
		a.unload(TEST_PATH + "hero.png");
		a.unload(PACKFILES_PATH + "dialog.pack");
	}
}
