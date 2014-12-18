package se.jaygames.derelict.screen.ingame;

import se.fredin.gdxtensions.font.AnimatedBitmapFont;
import se.fredin.gdxtensions.scene2d.Dialog;
import se.fredin.gdxtensions.screen.ingame.InGameScreen;
import se.jaygames.derelict.level.Level;
import se.jaygames.derelict.utils.Loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public class Dialogs extends InGameScreen {

	private Dialog dialog;
	private AnimatedBitmapFont font;
	
	public Dialogs(Level levelBase) {
		super(levelBase, Loader.PACKFILES_PATH + "dialog.pack");
		this.font = new AnimatedBitmapFont(Gdx.files.internal(Loader.FONT_PATH + "font.fnt"));
		
		TextFieldStyle style = new TextFieldStyle(font, Color.RED, null, null, skin.getDrawable("bg"));
		this.dialog = new Dialog("I believe whatever is fine and i dont care wayne, why dont you go bother them sheep while you're at it", style);
		this.addActorsToStage(dialog.getBounds(), dialog);
	}
	
	public void render() {
		stage.draw();
	}
	
	
	float tmp = 0;
	
	public void tick(float delta) {
		stage.act(delta);
		tmp += delta;
		System.out.println(tmp);
		if(tmp >= 3.0f && !dialog.isTimeToCloseDialog()) {
			dialog.closeDialog(1.8f);
		}
	}
	
	public void setPosition(Vector2 position) {
		this.dialog.setPosition(position.x, position.y);
	}
	
	public void setPosition(float x, float y) {
		this.dialog.setPosition(x, y);
	}
	
	
}
