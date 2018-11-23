package com.kursayin.platoformer.Render;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kursayin.platoformer.Components.PositionComponent;
import com.kursayin.platoformer.Components.TextureComponent;
import com.kursayin.platoformer.Platoformer;
import com.kursayin.platoformer.Systems.DrawSystem;
import com.kursayin.platoformer.PhysicWorld;;

public class PlatoformerMainRender {
    Platoformer platoformer;
    OrthographicCamera camera;
    SpriteBatch batch;
    Engine engine;
    DrawSystem drawSystem;
    PhysicWorld world;

    BackgroundRender backgroundRender;

    public PlatoformerMainRender(Platoformer platoformer) {
        this.platoformer = platoformer;
        batch = new SpriteBatch();
        backgroundRender = new BackgroundRender(batch);
        engine = new Engine();
        drawSystem = new DrawSystem(batch);
        Entity ankap = new Entity();
        ankap.add(new PositionComponent(50,50));
        TextureRegion tex = new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg")));
        ankap.add(new TextureComponent(tex));
        engine.addEntity(ankap);
        engine.addSystem(drawSystem);

        world=new PhysicWorld();
        world.show();
    }
    
    public void act(float delta) {

        backgroundRender.act(delta);
        world.act(delta);
    }
    
    public void draw() {
        batch.begin();
        backgroundRender.draw();
        engine.update(Gdx.graphics.getDeltaTime());
        world.render(Gdx.graphics.getDeltaTime());
        batch.end();
    }

    public void resize(int width, int height){
        world.resize(width, height);
    }

    public void dispose() {

    }
}
