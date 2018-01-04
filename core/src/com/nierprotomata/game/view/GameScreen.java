package com.nierprotomata.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.nierprotomata.game.NierProtomata;
import com.nierprotomata.game.model.*;
import com.nierprotomata.game.utils.TextureManager;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    private final NierProtomata game;

    private Player player;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> entitiesToAdd = new ArrayList<>();

    private boolean debug = true;

    public GameScreen(NierProtomata game) {
        this.game = game;
    }

    @Override
    public void show() {
        entities.add(new Wall(this, new Rectangle(28, 25, 11, 188)));
        entities.add(new Wall(this, new Rectangle(39, 14, 244, 11)));
        entities.add(new Wall(this, new Rectangle(283, 25, 11, 188)));
        entities.add(new Wall(this, new Rectangle(39, 213, 244, 11)));

        Texture playerTex = TextureManager.get(Assets.PLAYER.ordinal());
        player = new Player(this, new Rectangle(Constants.CAMERA_WIDTH / 2f - playerTex.getWidth() / 2f, Constants.CAMERA_HEIGHT / 4f - playerTex.getHeight() / 2f, TextureManager.get(Assets.PLAYER.ordinal()).getWidth(), TextureManager.get(Assets.PLAYER.ordinal()).getHeight()), game.getCam());
        entities.add(player);

        for(Entity entity : entities) {
            CollisionManager.add(entity);
        }
    }

    @Override
    public void render(float delta) {
        SpriteBatch batch = game.getBatch();

        // Update
        for(Entity entity : entities) {
            entity.update(delta);
        }

        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();

        CollisionManager.update();


        // Render
        batch.begin();
        batch.draw(TextureManager.get(Assets.GROUND.ordinal()), 0, 0);
        batch.draw(TextureManager.get(Assets.WALL.ordinal()), 0, 0);
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

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    @Override
    public void dispose() {
    }
}
