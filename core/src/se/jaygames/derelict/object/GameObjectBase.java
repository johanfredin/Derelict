package se.jaygames.derelict.object;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameObjectBase {
	
	void tick(float deltaTime);
	
	void render(OrthographicCamera camera, SpriteBatch batch, Player player);

}
