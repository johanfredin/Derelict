package se.jaygames.derelict.object;

import se.fredin.gdxtensions.assetmanagement.Assets;
import se.fredin.gdxtensions.input.GeneralInput;
import se.fredin.gdxtensions.input.InputDevice;
import se.fredin.gdxtensions.object.GameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

	private TextureRegion currentFrame;
	private final byte DEFAULT_SPEED = 4;
	
	//TODO: For now only fixed keyboard, fix later to map to any keys or gamepad or touchpad
	private GeneralInput input;
	
	public Player(Vector2 position) {
		super(position);
		this.speed = DEFAULT_SPEED;
		this.input = new GeneralInput(InputDevice.KEYBOARD);
		this.currentFrame = new TextureRegion((Texture) Assets.getInstance().get("sprites/objects/test/hero.png"), 0, 0, 32, 32);
	}

	@Override
	public void tick(float deltaTime, GameObject gameObject) {
		position.add(velocity);
		bounds.setPosition(position);
		handleInput();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(currentFrame, position.x, position.y);
	}

	@Override
	public void dispose() {}

	private void handleInput() {
		if(input.upButtonPressed) {
			velocity.set(0, speed);
		} else if(input.downButtonPressed) {
			velocity.set(0, -speed);
		} else if(input.leftButtonPressed) {
			velocity.set(-speed, 0);
		} else if(input.rightButtonPressed) {
			velocity.set(speed, 0);
		} else {
			velocity.set(0, 0);
		}
	}

}
