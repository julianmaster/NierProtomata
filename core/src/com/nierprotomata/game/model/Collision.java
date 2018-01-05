package com.nierprotomata.game.model;

import com.nierprotomata.game.model.entities.Entity;

public interface Collision {
	public void triggerCollision(Entity otherCollider);
}
