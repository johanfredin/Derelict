package se.jaygames.derelict.utils;

import se.fredin.gdxtensions.assetmanagement.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Loader {

	public static final String PACKFILES_PATH = "sprites/objects/utils/dialog/";
	public static final String TEST_PATH = "sprites/objects/test/";
	public static final String FONT_PATH = "fonts/";
	
	public static void load() {
		Assets a = Assets.getInstance();
		a.load(TEST_PATH + "hero.png", Texture.class);
		a.load(PACKFILES_PATH + "dialog.pack", TextureAtlas.class);
		a.load(FONT_PATH + "font.fnt", BitmapFont.class);
		a.load(TEST_PATH + "bullet.png", Texture.class);
	}
	
	public static void unload() {
		Assets a = Assets.getInstance();
		a.unload(TEST_PATH + "hero.png");
		a.unload(PACKFILES_PATH + "dialog.pack");
		a.unload(TEST_PATH + "bullet.png");
	}
}
