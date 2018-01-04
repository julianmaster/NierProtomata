package com.nierprotomata.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.nierprotomata.game.view.GameScreen;

public abstract class Entity implements Collision {
	private final GameScreen screen;
	private Polygon shape;

	public Entity(GameScreen screen, Rectangle rect) {
		this.screen = screen;
		this.shape = new Polygon(new float[]{0,0,rect.width,0,rect.width,rect.height,0,rect.height});
		shape.setPosition(rect.x, rect.y);
	}

	public abstract void update(float delta);
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
}
