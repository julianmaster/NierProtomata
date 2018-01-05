package com.nierprotomata.game.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nierprotomata.game.model.entities.Enemy1;
import com.nierprotomata.game.model.entities.Entity;
import com.nierprotomata.game.model.entities.Player;
import com.nierprotomata.game.utils.ShapeConverter;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LevelGenerator {

	public static Entity generatePlayer(GameScreen screen, Camera camera) {
		Texture playerTex = TextureManager.get(Assets.PLAYER.ordinal());

		Polygon polygon = new Polygon(new float[]{0, 0, playerTex.getWidth(), 0, playerTex.getWidth()/2f, playerTex.getHeight()});
		polygon.setPosition(Constants.CAMERA_WIDTH / 2f - playerTex.getWidth() / 2f, Constants.CAMERA_HEIGHT / 4f - playerTex.getHeight() / 2f);
		polygon.setOrigin(playerTex.getWidth()/2f, playerTex.getHeight()/2f);

		return new Player(screen, polygon, camera);
	}

	public static List<Entity> generateLevel(GameScreen screen, int level) {
		if(level == 1) {
			return level1(screen);
		}
		else {
			return null;
		}
	}

	public static List<Entity> level1(GameScreen screen) {
		List<Entity> entities = new ArrayList<>();

		Texture enemy1Tex = TextureManager.get(Assets.ENEMY1.ordinal());
		Rectangle enemy1Rect = new Rectangle(Constants.CAMERA_WIDTH / 2f - enemy1Tex.getWidth() / 2f, Constants.CAMERA_HEIGHT * 7f/10f - enemy1Tex.getHeight() / 2f, enemy1Tex.getWidth(), enemy1Tex.getHeight());
		List<Vector2> path = new LinkedList<>();
		path.add(new Vector2(Constants.CAMERA_WIDTH * 8f/10f, Constants.CAMERA_HEIGHT * 7f/10f - enemy1Tex.getHeight()/2f));
		path.add(new Vector2(Constants.CAMERA_WIDTH * 1f/10f, Constants.CAMERA_HEIGHT * 7f/10f - enemy1Tex.getHeight()/2f));
		Enemy1 enemy1 = new Enemy1(screen, ShapeConverter.rectToPolygon(enemy1Rect), path);
		entities.add(enemy1);

		return entities;
	}
}
