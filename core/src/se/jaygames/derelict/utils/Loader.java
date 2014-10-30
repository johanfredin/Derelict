package se.jaygames.derelict.utils;

import com.badlogic.gdx.graphics.Texture;

import se.fredin.gdxtensions.assetmanagement.Assets;

public class Loader {

	public static void load() {
		Assets.getInstance().load("sprites/objects/test/hero.png", Texture.class);
	}
	
	public static void unload() {
		Assets.getInstance().unload("sprites/objects/test/hero.png");
	}
}
