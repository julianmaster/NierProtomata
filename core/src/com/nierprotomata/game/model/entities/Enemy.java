package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.math.Polygon;
import com.nierprotomata.game.view.GameScreen;

public abstract class Enemy extends Entity {

	public Enemy(GameScreen screen, Polygon shape) {
		super(screen, shape);
	}
}
