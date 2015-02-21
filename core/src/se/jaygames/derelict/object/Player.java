package se.jaygames.derelict.object;

import se.fredin.gdxtensions.assetmanagement.Assets;
import se.fredin.gdxtensions.collision.CollisionHandler;
import se.fredin.gdxtensions.collision.CollisionHandler.Filter;
import se.fredin.gdxtensions.input.BaseInput;
import se.fredin.gdxtensions.object.GameObject;
import se.fredin.gdxtensions.utils.AnimationUtils;
import se.fredin.gdxtensions.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

	public static final byte MAX_HEALTH = 100;
	
	private final float DEFAULT_SPEED = 150f * Settings.GAMESPEED;
	private final float ANIM_SPEED_NORMAL = 0.10f;

	private TextureRegion currentFrame;
	private Sprite playerSprite;
	private float health;
	private float tempvelocity;
	private float healthColorPulseTimer;

	private boolean isGoalReached;
	private boolean isDead;
	private boolean isCollidingWithWall;
	private boolean isVisible;
	private boolean isAbleToWallJump = true;
	private boolean isTeleported;
	
	private Vector2 teleportLandingPosition;
	
	/** Check if we fell of a ledge */
	private boolean deathFromFalling;

	private Animation leftWalk, rightWalk;
	private Animation leftJump, rightJump;
	private Animation leftFall, rightFall;
	private Animation rightHurt, leftHurt;

	private Color playerColor;
	
	//TODO: For now only fixed keyboard, fix later to map to any keys or gamepad or touchpad
	private BaseInput input;
	
	public Player(Vector2 position, BaseInput input, CollisionHandler collisionHandler) {
		super(position, collisionHandler, 32, 32);
		this.speed = DEFAULT_SPEED;
		
		this.health = MAX_HEALTH;
		
		// Start with not moving
		this.direction = DIRECTION_NONE;
		
		this.input = input;
		TextureRegion heroSpriteSheet = new TextureRegion((Texture) Assets.getInstance().get("sprites/objects/test/hero.png"));
		this.leftWalk = AnimationUtils.getAnimation(heroSpriteSheet, ANIM_SPEED_NORMAL, 32, 32, "0-11");
		this.rightWalk = AnimationUtils.getAnimation(heroSpriteSheet, ANIM_SPEED_NORMAL, 32, 32, false, false, 6,7,8);
		
		// Don't have animations for these yet, going with walking copies for now .......................................
		this.rightJump = AnimationUtils.getAnimation(heroSpriteSheet, ANIM_SPEED_NORMAL, 32, 32, false, false, 9,10,11);
		this.leftJump = AnimationUtils.getAnimation(heroSpriteSheet, ANIM_SPEED_NORMAL, 32, 32, false, false, "0-2");
		this.rightFall = AnimationUtils.getAnimation(heroSpriteSheet, ANIM_SPEED_NORMAL, 32, 32, false, false, 9,10,11);
		this.leftFall = AnimationUtils.getAnimation(heroSpriteSheet, ANIM_SPEED_NORMAL, 32, 32, false, false, "0-2");
		this.rightHurt = AnimationUtils.getAnimation(heroSpriteSheet, 0, 32, 32, false, false, 0);
		this.leftHurt = AnimationUtils.getAnimation(heroSpriteSheet, 0, 32, 32, true, false, 0);
		// ..............................................................................................................
		this.currentFrame = rightWalk.getKeyFrame(stateTime, false);
		this.speed = DEFAULT_SPEED;
		this.velocity.set(1.0f, 0.0f);
		this.currentFrame = rightWalk.getKeyFrame(0);
		this.playerSprite = new Sprite(currentFrame);
		this.playerColor = new Color(Color.WHITE);
		this.isTeleported = false;
		this.isVisible = true;
	}

	/**
	 * Set the health of the player
	 * @param health the health
	 */
	public void setHealth(float health) {
		this.health = health;
	}
	
	public void setAbleToWallJump(boolean isAbleToWallJump) {
		this.isAbleToWallJump = isAbleToWallJump;
	}

	/**
	 * Find out if we are currently jumping e.g heading upwards
	 * 
	 * @return
	 */
	public boolean isJumping() {
		return this.isJumping;
	}

	/**
	 * Find out if we are dead
	 * @return
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * Find out if we died from falling of a ledge
	 * @return
	 */
	public boolean isDeathFromFalling() {
		return deathFromFalling;
	}

	@Override
	public void render(SpriteBatch batch) {
		if (isVisible) {
			// We only draw the player if it should be visible
			playerSprite.setColor(isDead ? Color.LIGHT_GRAY : playerColor);
			playerSprite.draw(batch);
		}
	}

	/**
	 * Find out if we reached the goal
	 * @return
	 */
	public boolean isGoalReached() {
		return isGoalReached;
	}
	
	/**
	 * Set to true when we have reached the orb
	 * @param isGoalReached
	 */
	public void setGoalReached(boolean isGoalReached) {
		this.isGoalReached = isGoalReached;
	}

	/**
	 * If the player is currently facing a wall we don't want to for example do
	 * any parallax scrolling.
	 * @return whether or not the player is facing a wall.
	 */
	public boolean isNotMoving() {
		return isCollidingWithWall || speed == 0.0f;
	}

	/**
	 * Get the width of the current frame texture region
	 * @return width
	 */
	public int getTextureWidth() {
		return currentFrame == null ? 32 : currentFrame.getRegionWidth();
	}

	/**
	 * Get the height of the current frame texture region
	 * @return height
	 */
	public int getTextureHeight() {
		return currentFrame == null ? 32 : currentFrame.getRegionHeight();
	}

	/**
	 * Set the player to visible or not
	 * @param isVisible
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Check if the player is visible
	 * @return
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/** 
	 * Used to set the players speed back to its regular speed 
	 */
	public void resetToDefaultSpeed() {
		this.speed = DEFAULT_SPEED;
	}

	/**
	 * Adds health to the player
	 * @param amount the amount of health to add
	 */
	public void increaseHealth(float amount) {
		if(health < MAX_HEALTH) {
			this.health += amount;
		}
	}

	public float getHealth() {
		return health;
	}

	/**
	 * Sets the players health to zero
	 */
	public void kill() {
		this.health = 0.0f;
	}

	public float getBoundsY() {
		return bounds.y;
	}

	@Override
	public void tick(float deltatime, GameObject player) {
		// Enter debug ticking mode if that is enabled
		if (Settings.FREE_FLYING_MODE) {
			debugTick(deltatime);
			return;
		}

		// Check if the cow is hurt and update health
		updateHealth(deltatime);
		handleInput();
		animate(deltatime);

		boolean isTouched = input.isJumpButtonPressed();
		
		/*
		 * Jump and falling logic
		 */
		if (gravity >= TERMINAL_VELOCITY) {
			gravity = TERMINAL_VELOCITY;
		} else {
			tempvelocity = ACCELERATION * deltatime;

			if (gravity >= 0 && isJumping) {
				isJumping = false;
			}
		}

	
		if (onGround) {
			isJumping = false;
			if (isTouched) {
				// Jump!
				isJumping = true;
				gravity = -JUMP;
			}
			
			if (!isJumping) {
				gravity = 0;
			} 
			
		}

		/*
		 * Update the velocity
		 */
		velocity.set(0.0f,  deltatime * (gravity + tempvelocity / 2.0f));
		// Set the direction of the cow and move it unless its time to tap ---
		if (isHeadingLeft() && !isGoalReached) {
			velocity.set(deltatime * this.speed, deltatime * (gravity + tempvelocity / 2.0f));
		} else if (isHeadingRight() && !isGoalReached) {
			velocity.set(deltatime * -this.speed, deltatime * (gravity + tempvelocity / 2.0f));
		} else if (direction == DIRECTION_NONE && !isTouched || isGoalReached) {
			velocity.set(0.0f, deltatime * (gravity + tempvelocity / 2.0f));
		}
		
		if (isTeleported) {
			if (getPosition().dst(getTeleportLandingPosition()) > 32f) {
				isTeleported = false;
			}
		}

		/*
		 * Try to move the player with the new velocity
		 */
		Vector2 newPosition = new Vector2(0, 0);
		newPosition.add(this.position);
		playerSprite.setPosition(position.x, position.y);

		// Update the players position if we haven't died
		if(!isDead && !isGoalReached) {
			newPosition.sub(velocity);
		}

		tryMove(newPosition);

		// Update the gravity with the temporary velocity to be used in the next
		// cycle
		gravity += tempvelocity;

		//TODO:Just to kill player while testing
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			kill();
		}
		
	}
	
	private void handleInput() {
		if(input.isLeftButtonPressed()) {
			System.out.println("left btn pressed");
			direction = DIRECTION_LEFT;
		} if(input.isRightButtonPressed()) {
			System.out.println("right btn pressed");
			direction = DIRECTION_RIGHT;
		} else {
			if(input.noMovementKeysPressed()) {
				direction = DIRECTION_NONE;
			}
		}
	}

	@Override
	protected void handleVerticalCollisionFromAbove(Vector2 newPosition) {
		Rectangle tmpBounds = new Rectangle(bounds);
		tmpBounds.y = newPosition.y;
		tmpBounds.height = (newPosition.y - bounds.y) * -1;
		Rectangle downBounds = collisionHandler.getBoundsAt(tmpBounds, (byte)(Filter.HARD |Filter.SOFT), this);
		if (downBounds != null) {
			onGround = true;
			Vector2 groundedPosition = new Vector2(position.x, downBounds.y + downBounds.height);
			setPosition(groundedPosition);
		} else {
			onGround = false;
			setPosition(position.x, newPosition.y);
		}
	}

	@Override
	protected void handleHorizontalCollision(Vector2 newPosition) {
		Rectangle tmpHorzBounds = new Rectangle(bounds);
		tmpHorzBounds.x = newPosition.x + left;
		Rectangle horzBounds = collisionHandler.getBoundsAt(tmpHorzBounds, (byte) (Filter.HARD), this);
		if (horzBounds != null) {
			isCollidingWithWall = true;
			if (!onGround) {
				checkIfTimeForWallJump();
			} else {
				//TODO: Set speed to 0;
			}
			setPosition(position.x, position.y);
		} else {
			isCollidingWithWall = false;
			setPosition(newPosition.x, position.y);
		}
	}

	@Override
	protected void animate(float deltaTime) {
		super.animate(deltaTime);
		if(speed != 0.0f) {
			if (direction == DIRECTION_NONE) {
				currentFrame = rightWalk.getKeyFrame(stateTime, true);
			} else if (direction == DIRECTION_RIGHT) {
				if ((isJumping() && !isDead) || gravity < 0) {
					currentFrame = rightJump.getKeyFrame(stateTime, false);
				} else if (isFalling() && !isDead) {
					currentFrame = rightFall.getKeyFrame(stateTime, true);
				} else if (isDead) {
					currentFrame = rightHurt.getKeyFrame(stateTime, false);
				} else if (isGoalReached) {
					currentFrame = rightWalk.getKeyFrame(0, false);
				} else {
					currentFrame = rightWalk.getKeyFrame(stateTime, true);
				}
			} else if (direction == DIRECTION_LEFT) {
				if ((isJumping() && !isDead) || gravity < 0) {
					currentFrame = leftJump.getKeyFrame(stateTime, false);
				} else if (isFalling() && !isDead) {
					currentFrame = leftFall.getKeyFrame(stateTime, true);
				} else if (isDead) {
					currentFrame = leftHurt.getKeyFrame(stateTime, false);
				} else if (isGoalReached) {
					currentFrame = leftWalk.getKeyFrame(0, false);
				} else {
					currentFrame = leftWalk.getKeyFrame(stateTime, true);
				}
			}
		} 

		playerSprite.setRegion(currentFrame);
	}

	/**
	 * If we have unlocked the ability to wall jump we call this method when the
	 * player is in the air and colliding with a hard wall.
	 */
	private void checkIfTimeForWallJump() {
		if (isAbleToWallJump && input.isJumpButtonPressed()) {
			isJumping = true;
			gravity = -JUMP;
			switchXDirection();
		}
	}

	/**
	 * Updates and handles the players health, should be called each tick
	 * @param deltatime time since last render
	 */
	private void updateHealth(float deltatime) {

		if (health <= 0f || position.y + getTextureHeight() <= 0) {
			isDead = true;
			
			if (position.y + getTextureHeight() <= 0) {
				deathFromFalling = true;
			}
			currentFrame = isHeadingRight() ? rightHurt.getKeyFrame(0) : leftHurt.getKeyFrame(0);
			return;
		}

		// If we are down to 25% health we start pulsating the with a red color
		if(health < MAX_HEALTH / 4) {
			float green = 1;
			float blue = 1;

			healthColorPulseTimer += deltatime;
			if(healthColorPulseTimer < .5f) {
				green = Math.min(0.0f, -healthColorPulseTimer * 2f);
				blue = Math.min(0.0f, -healthColorPulseTimer * 2f);
				playerColor.lerp(1.0f, green, blue, 1.0f, deltatime);
			} else if(healthColorPulseTimer >= .5f && healthColorPulseTimer <= 1.0f) {
				green = Math.min(1.0f, healthColorPulseTimer * 2f);
				blue =  Math.min(1.0f, healthColorPulseTimer * 2f);
				playerColor.lerp(1.0f, green, blue, 1.0f, deltatime);
			} else {
				healthColorPulseTimer = 0.0f;
			}
		} else {
			playerColor.set(1, 1, 1, 1);
			healthColorPulseTimer = 0.0f;
		}

		if (health >= MAX_HEALTH) {
			health = MAX_HEALTH;
		}
	}

	/**
	 * Used only in DEBUG_MODE. Lets us move the cow with the keyboard without
	 * any gravity or collision detection
	 * 
	 * @param deltaTime
	 */
	private void debugTick(float deltaTime) {
		velocity.set(speed, gravity);
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			direction = DIRECTION_LEFT;
			speed = -4f;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			direction = DIRECTION_RIGHT;
			speed = 4f;
		} else if (Gdx.input.isKeyPressed(Keys.UP)) {
			gravity = 4f;
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			gravity = -4f;
		} else {
			speed = 0;
			gravity = 0;
		}
		position.add(velocity);
		animate(deltaTime);
		bounds.setPosition(position);
	}

	@Override
	public void dispose() {
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isTeleported() {
		return isTeleported;
	}

	public void setTeleported(boolean isTeleported) {
		this.isTeleported = isTeleported;
	}

	public Vector2 getTeleportLandingPosition() {
		return teleportLandingPosition;
	}

	public void setTeleportLandingPosition(Vector2 teleportLandingPosition) {
		this.teleportLandingPosition = teleportLandingPosition;
	}
	
	public InputProcessor getInput() {
		return input;
	}

}
