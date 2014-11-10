package se.jaygames.derelict.screen.ingame;

import se.fredin.gdxtensions.font.AnimatedBitmapFont;
import se.fredin.gdxtensions.scene2d.Dialog;
import se.fredin.gdxtensions.screen.ingame.InGameScreen;
import se.jaygames.derelict.level.Level;
import se.jaygames.derelict.utils.Loader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public class Dialogs extends InGameScreen {

	private Dialog dialog;
	private AnimatedBitmapFont font;
	
	public Dialogs(Level levelBase) {
		super(levelBase, Loader.PACKFILES_PATH + "dialog.pack");
		this.font = new AnimatedBitmapFont();
		TextFieldStyle style = new TextFieldStyle(font, Color.RED, null, null, skin.getDrawable("bg"));
		this.dialog = new Dialog("I believde I can Achieve whateeevaaaah and this is not too criminally insane wayne or is it?", style);
		dialog.setLineBreakIndex((short) 10);
		System.out.println(dialog.getAnimatedText().getAmountOfLineBreaks());
		this.stage.addActor(dialog);
	}
	
	public void render() {
		stage.draw();
	}
	
	public void tick(float delta) {
		stage.act(delta);
	}
	
	public void setPosition(Vector2 position) {
		this.dialog.setPosition(position.x, position.y);
	}
	
	public void setPosition(float x, float y) {
		this.dialog.setPosition(x, y);
	}
	
	
}
