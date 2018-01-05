package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nierprotomata.game.model.Constants;
import com.nierprotomata.game.utils.ShapeConverter;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

import java.util.List;

public class Enemy1 extends Enemy {

	private final List<Vector2> path;
	private int life = 5;
	private int position = 0;

	private float fireTimer = 0f;
	private boolean redBullet = false;

	public Enemy1(GameScreen screen, Polygon shape, List<Vector2> path) {
		super(screen, shape);
		this.path = path;
		this.setSpeed(Constants.ENEMY1_SPEED);
	}

	@Override
	public void update(float delta) {
		float degree = MathUtils.atan2(path.get(position).y - getShape().getY(), path.get(position).x - getShape().getX());
		getDirection().set(MathUtils.cos(degree), MathUtils.sin(degree));
		updatePhysic();

		if(Math.abs(path.get(position).y - getShape().getY()) < 1f && Math.abs(path.get(position).x - getShape().getX()) < 1f) {
			position++;
			position %= path.size();
		}

		fireTimer -= delta;
		if(fireTimer <= 0f) {
			fireTimer = Constants.ENEMY1_FIRE_RATE;
			float fireDegree = MathUtils.atan2(getScreen().getPlayer().getShape().getY() -getShape().getY(), getScreen().getPlayer().getShape().getX() - getShape().getX());

			if(redBullet) {
				Texture redBulletTex = TextureManager.get(Assets.RED_BULLET.ordinal());
				Rectangle redBulletRect = new Rectangle(getShape().getX(), getShape().getY(), redBulletTex.getWidth(), redBulletTex.getHeight());
				RedEnemyBullet redBullet = new RedEnemyBullet(getScreen(), ShapeConverter.rectToPolygon(redBulletRect), fireDegree);
				getScreen().getEntitiesToAdd().add(redBullet);
			}
			else {
				Texture purpleBulletTex = TextureManager.get(Assets.PURPLE_BULLET.ordinal());
				Rectangle purpleBulletRect = new Rectangle(getShape().getX(), getShape().getY(), purpleBulletTex.getWidth(), purpleBulletTex.getHeight());
				PurpleEnemyBullet purpleBullet = new PurpleEnemyBullet(getScreen(), ShapeConverter.rectToPolygon(purpleBulletRect), fireDegree);
				getScreen().getEntitiesToAdd().add(purpleBullet);
			}

			redBullet = !redBullet;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		Texture enemy1 = TextureManager.get(Assets.ENEMY1.ordinal());
		TextureRegion enemy1Tex = new TextureRegion(enemy1);

		batch.draw(enemy1Tex, getShape().getX(), getShape().getY(), enemy1.getWidth()/2f, enemy1.getHeight()/2f, (float)enemy1.getWidth(), (float)enemy1.getHeight(), 1, 1, 0f);
	}

	@Override
	public void triggerCollision(Entity otherCollider) {
		if(otherCollider instanceof Bullet) {
			if(life > 1) {
				life--;
			}
			else {
				getScreen().getEntitiesToRemove().add(this);
			}
		}
	}
}
