package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.nierprotomata.game.model.CollisionManager;
import com.nierprotomata.game.model.Constants;
import com.nierprotomata.game.utils.ShapeConverter;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

public class Player extends Entity {

	private final Camera cam;

	private int life = 3;
	private float speed = 120f;

	private float angleOffset = -90f;

	private float fireTimer = 0f;

	public Player(GameScreen screen, Polygon polygon, Camera cam) {
		super(screen, polygon);
		this.cam = cam;
	}

	@Override
	public void update(float delta) {
		Polygon shape = getShape();
		Texture player = TextureManager.get(Assets.PLAYER.ordinal());

		Vector3 mouseInWorld3D = new Vector3();
		mouseInWorld3D.x = Gdx.input.getX();
		mouseInWorld3D.y = Gdx.input.getY();
		cam.unproject(mouseInWorld3D);

		float angle = (float)Math.atan2(mouseInWorld3D.y - shape.getBoundingRectangle().y - player.getHeight() / 2f, mouseInWorld3D.x - shape.getBoundingRectangle().x - player.getWidth()/ 2f) * 180f / (float)Math.PI + angleOffset;
		getShape().setRotation(angle);

		float x = shape.getX();
		float y = shape.getY();
		if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
			y += speed * delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			x -= speed * delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			y -= speed * delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			x += speed * delta;
		}
		shape.setPosition(x, y);


		fireTimer -= delta;
		if(fireTimer <= 0f) {
			fireTimer = 0f;
			if(Gdx.input.isTouched()) {
				fireTimer = Constants.FIRE_WAIT;
				Texture bulletTex = TextureManager.get(Assets.BULLET.ordinal());
				Rectangle bulletRect = new Rectangle(getShape().getTransformedVertices()[4], getShape().getTransformedVertices()[5], bulletTex.getWidth(), bulletTex.getHeight());
				Bullet bullet = new Bullet(getScreen(), ShapeConverter.rectToPolygon(bulletRect), getShape().getRotation());
				getScreen().getEntitiesToAdd().add(bullet);
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		Polygon shape = getShape();
		Texture player = TextureManager.get(Assets.PLAYER.ordinal());
		Texture playerShadow = TextureManager.get(Assets.PLAYER_SHADOW.ordinal());
		TextureRegion texPlayer = new TextureRegion(player);
		TextureRegion texPlayerShadow = new TextureRegion(playerShadow);

		batch.draw(texPlayerShadow, shape.getX()-1, shape.getY()-1, shape.getOriginX(), shape.getOriginY(), player.getWidth(), player.getHeight(), 1, 1,  shape.getRotation());
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
	}
}
