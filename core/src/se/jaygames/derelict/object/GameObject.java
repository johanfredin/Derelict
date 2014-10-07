package se.jaygames.derelict.object;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class GameObject implements GameObjectBase, Disposable {

	protected Vector2 position;
	protected Vector2 velocity;
	protected Rectangle bounds;
	protected float speed;
	
	public GameObject(Vector2 position) {
		this.position = position;
		this.velocity = new Vector2();
		this.bounds = new Rectangle(position.x, position.y, 32, 32);
	}
	
	public GameObject(Vector2 position, float w, float h) {
		this(position);
		this.bounds.setWidth(w);
		this.bounds.setHeight(h);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	
	
	
	
}
