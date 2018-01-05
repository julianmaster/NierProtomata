package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

import java.util.List;

public class Enemy1 extends Enemy {

	private final List<Vector2> path;
	private int life = 5;
	private int position = 0;

	private float speed = 80f;

	public Enemy1(GameScreen screen, Polygon shape, List<Vector2> path) {
		super(screen, shape);
		this.path = path;
	}

	@Override
	public void update(float delta) {
		float deltaX = Math.min(path.get(position).x * speed * delta, path.get(position).x - getShape().getX());
		float deltaY = Math.min(path.get(position).y * speed * delta, path.get(position).y - getShape().getY());

		getShape().translate(deltaX, deltaY);
//
//		if(Math.abs(getShape().getX() - path.get(position).x) < deltaX && Math.abs(getShape().getY() - path.get(position).y) < deltaY) {
//			getShape().translate(path.get(position).x, path.get(position).y);
//			position++;
//			position %= path.size();
//		}
//		else {
//			getShape().translate(deltaX, deltaY);
//		}
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
