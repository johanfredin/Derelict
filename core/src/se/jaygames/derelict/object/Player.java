package se.jaygames.derelict.object;

import se.fredin.gdxtensions.object.GameObject;
import se.fredin.gdxtensions.res.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

	private TextureRegion currentFrame;
	
	public Player(Vector2 position) {
		super(position);
		this.currentFrame = new TextureRegion((Texture) Assets.getInstance().get("sprites/objects/test/hero.png"), 0, 0, 32, 32);
	}

	@Override
	public void tick(float deltaTime, GameObject gameObject) {
		speed = 4;
		velocity.set(speed, speed);
		position.add(velocity);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(currentFrame, position.x, position.y);
	}

	@Override
	public void dispose() {
		
	}


}
