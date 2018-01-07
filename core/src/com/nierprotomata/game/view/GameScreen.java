package com.nierprotomata.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.nierprotomata.game.NierProtomata;
import com.nierprotomata.game.model.*;
import com.nierprotomata.game.model.entities.Entity;
import com.nierprotomata.game.model.entities.Wall;
import com.nierprotomata.game.utils.ShapeConverter;
import com.nierprotomata.game.utils.TextureManager;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    private final NierProtomata game;

    private Entity player;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> entitiesToAdd = new ArrayList<>();
    private List<Entity> entitiesToRemove = new ArrayList<>();

    private boolean debug = false;

    public GameScreen(NierProtomata game) {
        this.game = game;
    }

    @Override
    public void show() {
        entities.add(new Wall(this, ShapeConverter.rectToPolygon(new Rectangle(28, 25, 11, 188))));
        entities.add(new Wall(this, ShapeConverter.rectToPolygon(new Rectangle(39, 14, 244, 11))));
        entities.add(new Wall(this, ShapeConverter.rectToPolygon(new Rectangle(283, 25, 11, 188))));
        entities.add(new Wall(this, ShapeConverter.rectToPolygon(new Rectangle(39, 213, 244, 11))));

        player = LevelGenerator.generatePlayer(this, game.getCam());
        entities.add(player);

        entities.addAll(LevelGenerator.generateLevel(this, 1));
    }

    @Override
    public void render(float delta) {
        SpriteBatch batch = game.getBatch();

        // Update
        for(Entity entity : entities) {
            entity.update(delta);
        }

        // Collision
        CollisionManager.update(entities);

        // Add new entities
        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();

        // Remove old entities
        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();

        // Render
        batch.begin();
        batch.draw(TextureManager.getTexture(Assets.GROUND.ordinal()), 0, 0);
        batch.draw(TextureManager.getTexture(Assets.WALL.ordinal()), 0, 0);
        for(Entity entity : entities) {
            entity.render(batch);
        }
        batch.end();

        // Debug
        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            debug = !debug;
        }
        if(debug) {
            ShapeRenderer shapeRenderer = game.getRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for(Entity entity : entities) {
                entity.drawDebug(shapeRenderer);
            }
            shapeRenderer.end();
        }
    }

    public Entity getPlayer() {
        return player;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    public List<Entity> getEntitiesToRemove() {
        return entitiesToRemove;
    }

    @Override
    public void dispose() {
    }
}
