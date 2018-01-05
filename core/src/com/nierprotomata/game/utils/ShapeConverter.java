package com.nierprotomata.game.utils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class ShapeConverter {

	public static Polygon rectToPolygon(Rectangle rect) {
		Polygon polygon = new Polygon(new float[]{0,0,rect.width,0,rect.width,rect.height,0,rect.height});
		polygon.setPosition(rect.x, rect.y);
		return polygon;
	}
}
