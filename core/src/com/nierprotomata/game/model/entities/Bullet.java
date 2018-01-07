package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.nierprotomata.game.model.Constants;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

public class Bullet extends Entity {

	private float angleOffset = 90f;

	public Bullet(GameScreen screen, Polygon shape, float degrees) {
		super(screen, shape);
		this.setSpeed(Constants.PLAYER_BULLET_SPEED);

		getShape().setRotation(degrees);

		final float cos1 = MathUtils.cosDeg(getShape().getRotation() + angleOffset + 50f);
		final float sin1 = MathUtils.sinDeg(getShape().getRotation() + angleOffset + 50f);
		getShape().translate(cos1 * 4, sin1 * 4);
	}

	@Override
	public void update(float delta) {
		final float cos = MathUtils.cosDeg(getShape().getRotation() + angleOffset);
		final float sin = MathUtils.sinDeg(getShape().getRotation() + angleOffset);
		getDirection().set(cos, sin);
		updatePhysic();
	}

	@Override
	public void render(SpriteBatch batch) {
		Polygon shape = getShape();
		TextureRegion player = TextureManager.getTexture(Assets.BULLET.ordinal());
		TextureRegion playerShadow = TextureManager.getTexture(Assets.BULLET_SHADOW.ordinal());

		batch.draw(playerShadow, shape.getX()-1, shape.getY()-1, shape.getOriginX(), shape.getOriginY(), player.getRegionWidth(), player.getRegionHeight(), 1, 1,  shape.getRotation());
		batch.draw(player, shape.getX(), shape.getY(), shape.getOriginX(), shape.getOriginY(), player.getRegionWidth(), player.getRegionHeight(), 1, 1,  shape.getRotation());
	}

	@Override
	public void triggerCollision(Entity otherCollider) {
		if(otherCollider instanceof Wall || otherCollider instanceof Enemy || otherCollider instanceof RedEnemyBullet) {
			getScreen().getEntitiesToRemove().add(this);
		}
	}
}
