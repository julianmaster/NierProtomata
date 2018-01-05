package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

public class Bullet extends Entity {

	private float speed = 200f;
	private float angleOffset = 90f;

	public Bullet(GameScreen screen, Polygon shape, float degrees) {
		super(screen, shape);

		getShape().setRotation(degrees);

		final float cos1 = MathUtils.cosDeg(getShape().getRotation() + angleOffset + 50f);
		final float sin1 = MathUtils.sinDeg(getShape().getRotation() + angleOffset + 50f);
		getShape().translate(cos1 * 4, sin1 * 4);
	}

	@Override
	public void update(float delta) {
		final float cos = MathUtils.cosDeg(getShape().getRotation() + angleOffset);
		final float sin = MathUtils.sinDeg(getShape().getRotation() + angleOffset);
		getShape().translate(cos * speed * delta, sin * speed * delta);
	}

	@Override
	public void render(SpriteBatch batch) {
		Polygon shape = getShape();
		Texture player = TextureManager.get(Assets.BULLET.ordinal());
		Texture playerShadow = TextureManager.get(Assets.BULLET_SHADOW.ordinal());
		TextureRegion texPlayer = new TextureRegion(player);
		TextureRegion texPlayerShadow = new TextureRegion(playerShadow);

		batch.draw(texPlayerShadow, shape.getX()-1, shape.getY()-1, shape.getOriginX(), shape.getOriginY(), player.getWidth(), player.getHeight(), 1, 1,  shape.getRotation());
		batch.draw(texPlayer, shape.getX(), shape.getY(), shape.getOriginX(), shape.getOriginY(), player.getWidth(), player.getHeight(), 1, 1,  shape.getRotation());
	}

	@Override
	public void triggerCollision(Entity otherCollider) {
		if(otherCollider instanceof Wall) {
			getScreen().getEntitiesToRemove().add(this);
		}
		else if(otherCollider instanceof Enemy) {
			getScreen().getEntitiesToRemove().add(this);
		}
	}
}