package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.nierprotomata.game.view.GameScreen;

public class Wall extends Entity {

	public Wall(GameScreen screen, Polygon shape) {
		super(screen, shape);
	}

	@Override
	public void update(float delta) {
		// Nothing
	}

	@Override
	public void render(SpriteBatch batch) {
		// Nothing
	}

	@Override
	public void triggerCollision(Entity otherCollider) {
		// Nothing
	}
}
