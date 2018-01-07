package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nierprotomata.game.model.Constants;
import com.nierprotomata.game.utils.AnimationsManager;
import com.nierprotomata.game.utils.AudioManager;
import com.nierprotomata.game.utils.ShapeConverter;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Animations;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;
import com.nierprotomata.game.view.Sounds;

import java.util.List;

public class Enemy1 extends Enemy {

	private final List<Vector2> path;
	private int life = 5;
	private int position = 0;

	private float fireTimer = 0f;
	private boolean redBullet = false;

	private boolean runAnimation = false;
	private float stateTime = 0;

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
				TextureRegion redBulletTex = TextureManager.getTexture(Assets.RED_BULLET.ordinal());
				Rectangle redBulletRect = new Rectangle(getShape().getX(), getShape().getY(), redBulletTex.getRegionWidth(), redBulletTex.getRegionHeight());
				RedEnemyBullet redBullet = new RedEnemyBullet(getScreen(), ShapeConverter.rectToPolygon(redBulletRect), fireDegree);
				getScreen().getEntitiesToAdd().add(redBullet);
			}
			else {
				TextureRegion purpleBulletTex = TextureManager.getTexture(Assets.PURPLE_BULLET.ordinal());
				Rectangle purpleBulletRect = new Rectangle(getShape().getX(), getShape().getY(), purpleBulletTex.getRegionWidth(), purpleBulletTex.getRegionHeight());
				PurpleEnemyBullet purpleBullet = new PurpleEnemyBullet(getScreen(), ShapeConverter.rectToPolygon(purpleBulletRect), fireDegree);
				getScreen().getEntitiesToAdd().add(purpleBullet);
			}

			AudioManager.getSound(Sounds.ENEMY_SHOOT.ordinal()).play();
			redBullet = !redBullet;
		}

		if(runAnimation) {
			stateTime += delta;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		Animation explosion = AnimationsManager.get(Animations.ENEMY1_EXPLOSION.ordinal());
		if(!explosion.isAnimationFinished(stateTime) && runAnimation) {
			TextureRegion explosionKeyFrame = (TextureRegion)explosion.getKeyFrame(stateTime);
			batch.draw(explosionKeyFrame, getShape().getX() + (getShape().getBoundingRectangle().width - explosionKeyFrame.getRegionWidth()) / 2f, getShape().getY() + (getShape().getBoundingRectangle().height - explosionKeyFrame.getRegionHeight()) / 2);
		}

		TextureRegion enemy1 = TextureManager.getTexture(Assets.ENEMY1.ordinal());
		batch.draw(enemy1, getShape().getX(), getShape().getY(), enemy1.getRegionWidth()/2f, enemy1.getRegionHeight()/2f, (float)enemy1.getRegionWidth(), (float)enemy1.getRegionHeight(), 1, 1, 0f);
	}

	@Override
	public void triggerCollision(Entity otherCollider) {
		if(otherCollider instanceof Bullet) {
			if(life > 1) {
				life--;
				runAnimation = true;
				stateTime = 0f;
				AudioManager.getSound(Sounds.ENEMY_HIT.ordinal()).play();

			}
			else {
				getScreen().getEntitiesToRemove().add(this);
				AudioManager.getSound(Sounds.ENEMY_BOSS_DESTROY.ordinal()).play();
			}

		}
	}
}
