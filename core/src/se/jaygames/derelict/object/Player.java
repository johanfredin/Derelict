package se.jaygames.derelict.object;

import se.fredin.gdxtensions.assetmanagement.Assets;
import se.fredin.gdxtensions.input.BaseInput;
import se.fredin.gdxtensions.object.GameObject;
import se.fredin.gdxtensions.utils.AnimationUtils;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

	private TextureRegion currentFrame;
	private final byte DEFAULT_SPEED = 4;
	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation upAnimation;
	private Animation downAnimation;
	
	//TODO: For now only fixed keyboard, fix later to map to any keys or gamepad or touchpad
	private BaseInput input;
	
	public Player(Vector2 position, BaseInput input) {
		super(position, 32, 32);
		this.speed = DEFAULT_SPEED;
		this.input = input;
		TextureRegion heroSpriteSheet = new TextureRegion((Texture) Assets.getInstance().get("sprites/objects/test/hero.png"));
		this.leftAnimation = AnimationUtils.getAnimation(heroSpriteSheet, .10f, 32, 32, "0-11");
		this.rightAnimation = AnimationUtils.getAnimation(heroSpriteSheet, .10f, 32, 32, false, false, 6,7,8);
		this.upAnimation = AnimationUtils.getAnimation(heroSpriteSheet, .10f, 32, 32, false, false, 9,10,11);
		this.downAnimation = AnimationUtils.getAnimation(heroSpriteSheet, .10f, 32, 32, false, false, "0-2");
		this.currentFrame = rightAnimation.getKeyFrame(stateTime, false);
	}

	@Override
	public void tick(float deltaTime, GameObject gameObject) {}
	
	public void tick(float deltaTime) {
		position.add(velocity);
		bounds.setPosition(position);
		animate(deltaTime);
		handleInput();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(currentFrame, position.x, position.y);
	}
	
	@Override
	protected void animate(float deltaTime) {
		super.animate(deltaTime);
		if(isHeadingUp()) {
			currentFrame = upAnimation.getKeyFrame(stateTime, true);
		} else if(isHeadingDown()) {
			currentFrame = downAnimation.getKeyFrame(stateTime, true);
		} else if(isHeadingRight()) {
			currentFrame = rightAnimation.getKeyFrame(stateTime, true);
		} else if(isHeadingLeft()) {
			currentFrame = leftAnimation.getKeyFrame(stateTime, true);
		} else {
			currentFrame = downAnimation.getKeyFrame(0.0f);
		}
	}

	@Override
	public void dispose() {}

	private void handleInput() {
		if(input.isUpButtonPressed()) {
			velocity.set(0, speed);
			setDirection(DIRECTION_UP);
		} else if(input.isDownButtonPressed()) {
			velocity.set(0, -speed);
			setDirection(DIRECTION_DOWN);
		} else if(input.isLeftButtonPressed()) {
			velocity.set(-speed, 0);
			setDirection(DIRECTION_LEFT);
		} else if(input.isRightButtonPressed()) {
			velocity.set(speed, 0);
			setDirection(DIRECTION_RIGHT);
		} else {
			velocity.set(0, 0);
			setDirection(DIRECTION_NONE);
		}
	}
	
	public InputProcessor getInput() {
		return input;
	}
}
