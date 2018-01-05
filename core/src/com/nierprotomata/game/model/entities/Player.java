package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.nierprotomata.game.model.Constants;
import com.nierprotomata.game.utils.ShapeConverter;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

public class Player extends Entity {

	private final Camera cam;

	private int life = 3;

	private float angleOffset = -90f;
	private float fireTimer = 0f;

	public Player(GameScreen screen, Polygon polygon, Camera cam) {
		super(screen, polygon);
		this.cam = cam;
		this.setSpeed(Constants.PLAYER_SPEED);
	}

	@Override
	public void update(float delta) {
		Polygon shape = getShape();

		Texture player;
		if(life == 3) {
			player = TextureManager.get(Assets.PLAYER_FULL_LIFE.ordinal());
		}
		else if(life == 2) {
			player = TextureManager.get(Assets.PLAYER_MID_LIFE.ordinal());
		}
		else {
			player = TextureManager.get(Assets.PLAYER_LOW_LIFE.ordinal());
		}

		Vector3 mouseInWorld3D = new Vector3();
		mouseInWorld3D.x = Gdx.input.getX();
		mouseInWorld3D.y = Gdx.input.getY();
		cam.unproject(mouseInWorld3D);

		float angle = MathUtils.atan2(mouseInWorld3D.y - shape.getBoundingRectangle().y - player.getHeight() / 2f, mouseInWorld3D.x - shape.getBoundingRectangle().x - player.getWidth()/ 2f) * 180f / (float)Math.PI + angleOffset;
		getShape().setRotation(angle);

		int x = 0, y = 0;
		if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
			y++;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			x--;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			y--;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			x++;
		}

		getDirection().set(x, y);
		updatePhysic();

		fireTimer -= delta;
		if(fireTimer <= 0f) {
			fireTimer = 0f;
			if(Gdx.input.isTouched()) {
				fireTimer = Constants.PLAYER_FIRE_RATE;
				Texture bulletTex = TextureManager.get(Assets.BULLET.ordinal());
				Rectangle bulletRect = new Rectangle(getShape().getTransformedVertices()[4], getShape().getTransformedVertices()[5], bulletTex.getWidth(), bulletTex.getHeight());
				Bullet bullet = new Bullet(getScreen(), ShapeConverter.rectToPolygon(bulletRect), getShape().getRotation());
				getScreen().getEntitiesToAdd().add(bullet);
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {

		Texture player;
		Texture playerShadow;
		if(life == 3) {
			player = TextureManager.get(Assets.PLAYER_FULL_LIFE.ordinal());
			playerShadow = TextureManager.get(Assets.PLAYER_FULL_LIFE_SHADOW.ordinal());
		}
		else if(life == 2) {
			player = TextureManager.get(Assets.PLAYER_MID_LIFE.ordinal());
			playerShadow = TextureManager.get(Assets.PLAYER_MID_LIFE_SHADOW.ordinal());
		}
		else {
			player = TextureManager.get(Assets.PLAYER_LOW_LIFE.ordinal());
			playerShadow = TextureManager.get(Assets.PLAYER_LOW_LIFE_SHADOW.ordinal());
		}

		Polygon shape = getShape();
		TextureRegion texPlayer = new TextureRegion(player);
		TextureRegion texPlayerShadow = new TextureRegion(playerShadow);

		batch.draw(texPlayerShadow, shape.getX()-1, shape.getY()-1, shape.getOriginX(), shape.getOriginY(), playerShadow.getWidth(), playerShadow.getHeight(), 1, 1,  shape.getRotation());
		batch.draw(texPlayer, shape.getX(), shape.getY(), shape.getOriginX(), shape.getOriginY(), player.getWidth(), player.getHeight(), 1, 1,  shape.getRotation());
	}

	@Override
	public void triggerCollision(Entity otherCollider) {

		if(otherCollider instanceof Wall) {
			Vector2 previousPosition = new Vector2(getShape().getX(), getShape().getY());

			boolean collided = true;
			int i = 1;
			do {
				getShape().setPosition(previousPosition.x + i, previousPosition.y);
				collided = Intersector.overlapConvexPolygons(getShape(), otherCollider.getShape());

				if(collided) {
					getShape().setPosition(previousPosition.x - i, previousPosition.y);
					collided = Intersector.overlapConvexPolygons(getShape(), otherCollider.getShape());
				}

				if(collided) {
					getShape().setPosition(previousPosition.x, previousPosition.y + i);
					collided = Intersector.overlapConvexPolygons(getShape(), otherCollider.getShape());
				}

				if(collided) {
					getShape().setPosition(previousPosition.x, previousPosition.y - i);
					collided = Intersector.overlapConvexPolygons(getShape(), otherCollider.getShape());
				}
				i++;
			}while (collided);
		}

		if(otherCollider instanceof RedEnemyBullet || otherCollider instanceof PurpleEnemyBullet) {
			life--;
			if(life == 2) {
				Texture playerTex = TextureManager.get(Assets.PLAYER_MID_LIFE.ordinal());
				getShape().setVertices(new float[]{playerTex.getWidth()/2f, 0, playerTex.getWidth(), 0, playerTex.getWidth()/2f, playerTex.getHeight(), 0, playerTex.getHeight()/2f});
				getShape().setOrigin(playerTex.getWidth()/2f, playerTex.getHeight()/2f);
			}
			else {
				Texture playerTex = TextureManager.get(Assets.PLAYER_LOW_LIFE.ordinal());
				getShape().setVertices(new float[]{0, 0, playerTex.getWidth(), 0, playerTex.getWidth()/2f, playerTex.getHeight()});
				getShape().setOrigin(playerTex.getWidth()/2f, playerTex.getHeight()/2f);
			}
		}
	}
}
