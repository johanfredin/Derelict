package se.jaygames.derelict.object;

import se.jaygames.derelict.utils.Assets;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
	public void tick(float deltaTime) {
		speed = 4;
		velocity.set(speed, speed);
		position.add(velocity);
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch, Player player) {
		batch.draw(currentFrame, position.x, position.y);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
