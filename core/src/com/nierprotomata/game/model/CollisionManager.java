package com.nierprotomata.game.model;

import com.badlogic.gdx.math.Intersector;
import com.nierprotomata.game.model.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

	public static void update(List<Entity> entities) {
		for(Entity entity1 : entities) {
			for(Entity entity2 : entities) {
				if(entity1 != entity2) {
					if(Intersector.overlapConvexPolygons(entity1.getShape(), entity2.getShape())) {
						entity1.triggerCollision(entity2);
					}
				}
			}
		}
	}
}
