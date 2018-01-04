package com.nierprotomata.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

public class Bullet extends Entity {

	public float speed = 200f;

	private float angleOffset = 90f;

	public Bullet(GameScreen screen, Rectangle rect, float degrees) {
		super(screen, rect);

		getShape().setRotation(degrees);

		final float cos1 = MathUtils.cosDeg(getShape().getRotation() + angleOffset);
		final float sin1 = MathUtils.sinDeg(getShape().getRotation() + angleOffset);
		final float cos2 = MathUtils.cosDeg(getShape().getRotation());
		final float sin2 = MathUtils.sinDeg(getShape().getRotation());
		getShape().translate(cos1 * 3 - cos2 * getShape().getBoundingRectangle().width / 2f, sin1 * 3 - sin2 * getShape().getBoundingRectangle().width / 2f);
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
			getScreen().getEntities().remove(this);
			CollisionManager.remove(this);
		}
	}
}
