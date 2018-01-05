package com.nierprotomata.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nierprotomata.game.model.Constants;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.GameScreen;

public class NierProtomata extends Game {
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private ShapeRenderer renderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
		renderer = new ShapeRenderer();

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f);
		cam.update();

		// load all assets
		load();

		this.setScreen(new GameScreen(this));
	}

	public void load() {
		TextureManager.load(Gdx.files.internal("wall.png"));
		TextureManager.load(Gdx.files.internal("ground.png"));
		TextureManager.load(Gdx.files.internal("player_full_life.png"));
		TextureManager.load(Gdx.files.internal("player_full_life_shadow.png"));
		TextureManager.load(Gdx.files.internal("player_mid_life.png"));
		TextureManager.load(Gdx.files.internal("player_mid_life_shadow.png"));
		TextureManager.load(Gdx.files.internal("player_low_life.png"));
		TextureManager.load(Gdx.files.internal("player_low_life_shadow.png"));
		TextureManager.load(Gdx.files.internal("bullet.png"));
		TextureManager.load(Gdx.files.internal("bullet_shadow.png"));
		TextureManager.load(Gdx.files.internal("enemy1.png"));
		TextureManager.load(Gdx.files.internal("red_bullet.png"));
		TextureManager.load(Gdx.files.internal("purple_bullet.png"));
	}

	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderer.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		TextureManager.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public ShapeRenderer getRenderer() {
		return renderer;
	}
}
