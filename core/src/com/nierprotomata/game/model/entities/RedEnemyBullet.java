package com.nierprotomata.game.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.nierprotomata.game.model.Constants;
import com.nierprotomata.game.utils.TextureManager;
import com.nierprotomata.game.view.Assets;
import com.nierprotomata.game.view.GameScreen;

public class RedEnemyBullet extends Entity {

    private final float degree;

    public RedEnemyBullet(GameScreen screen, Polygon shape, float degree) {
        super(screen, shape);
        this.degree = degree;
        this.setSpeed(Constants.ENEMY_BULLET_SPEED);
    }

    @Override
    public void update(float delta) {
        final float cos = MathUtils.cos(degree);
        final float sin = MathUtils.sin(degree);
        getDirection().set(cos, sin);
        updatePhysic();
    }

    @Override
    public void render(SpriteBatch batch) {
        Polygon shape = getShape();
        Texture player = TextureManager.get(Assets.RED_BULLET.ordinal());
        batch.draw(player, shape.getX(), shape.getY());
    }

    @Override
    public void triggerCollision(Entity otherCollider) {
        if(otherCollider instanceof Wall || otherCollider instanceof Bullet) {
            getScreen().getEntitiesToRemove().add(this);
        }
    }
}