package com.nierprotomata.game.model;

import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

	private static List<Entity> entities = new ArrayList<>();
	private static List<Entity> entitiesToRemove = new ArrayList<>();

	public static boolean add(Entity entity) {
		return entities.add(entity);
	}

	public static boolean remove(Entity entity) {
		return entitiesToRemove.add(entity);
	}

	public static void update() {
		for(Entity entity1 : entities) {
			for(Entity entity2 : entities) {
				if(entity1 != entity2) {
					if(Intersector.overlapConvexPolygons(entity1.getShape(), entity2.getShape())) {
						entity1.triggerCollision(entity2);
					}
				}
			}
		}

		for(Entity entity : entitiesToRemove) {
			entities.remove(entity);
		}
		entitiesToRemove.clear();
	}
}
