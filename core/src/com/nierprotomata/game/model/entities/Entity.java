package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nierprotomata.game.model.Collision;
import com.nierprotomata.game.view.GameScreen;

public abstract class Entity implements Collision {
	private final GameScreen screen;

	private Polygon shape;
	//Have a speed variable
	private float speed = 0;
	//direction variable
	private Vector2 direction = new Vector2();
	//have a velocity variable (direction * speed)
	private Vector2 velocity = new Vector2();

	public Entity(GameScreen screen, Polygon shape) {
		this.screen = screen;
		this.shape = shape;
//		this.shape = new Polygon(new float[]{0,0,rect.width,0,rect.width,rect.height,0,rect.height});
//		shape.setPosition(rect.x, rect.y);
	}

	public abstract void update(float delta);
	public void updatePhysic() {
		velocity = direction.cpy().scl(speed * Gdx.graphics.getDeltaTime());
		shape.translate(velocity.x, velocity.y);
	}
	public abstract void render(SpriteBatch batch);

	public void drawDebug(ShapeRenderer shapeRenderer) {
		shapeRenderer.polygon(shape.getTransformedVertices());
	}

	public GameScreen getScreen() {
		return screen;
	}

	public Polygon getShape() {
		return shape;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
}
