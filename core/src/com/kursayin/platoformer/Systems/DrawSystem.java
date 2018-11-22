package com.kursayin.platoformer.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kursayin.platoformer.Components.PositionComponent;
import com.kursayin.platoformer.Components.TextureComponent;


public class DrawSystem extends EntitySystem{

    ImmutableArray<Entity> array;
    SpriteBatch spriteBatch;

    public DrawSystem(SpriteBatch batch) {
        this.spriteBatch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        array = engine.getEntitiesFor(Family.all(PositionComponent.class, TextureComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity: array) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            spriteBatch.draw(entity.getComponent(TextureComponent.class).textureRegion,pos.x,pos.y);
        }
    }
}
