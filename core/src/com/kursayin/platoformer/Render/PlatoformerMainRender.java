package com.kursayin.platoformer.Render;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kursayin.platoformer.Constants;
import com.kursayin.platoformer.Platoformer;
import com.kursayin.platoformer.Systems.DrawSystem;
import com.kursayin.platoformer.PhysicWorld;
import com.kursayin.platoformer.Systems.InputControllerSystem;;

public class PlatoformerMainRender {
    Platoformer platoformer;
    OrthographicCamera camera;
    SpriteBatch batch;
    Engine engine;
    DrawSystem drawSystem;
    PhysicWorld physicWorld;
    WorldRender worldRender;
    InputControllerSystem inputControllerSystem;

    BackgroundRender backgroundRender;

    public PlatoformerMainRender(Platoformer platoformer) {
        this.platoformer = platoformer;
        float worldHeightinTiles = 6;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,  (worldHeightinTiles / Constants.SCREEN_HEIGHT) * Constants.SCREEN_WIDTH, worldHeightinTiles);
        batch = new SpriteBatch();
        backgroundRender = new BackgroundRender(batch, camera);
        engine = new Engine();
        drawSystem = new DrawSystem(batch);
        engine.addSystem(drawSystem);
        worldRender = new WorldRender("first.tmx", batch);

        camera.update();
        physicWorld = new PhysicWorld(camera, worldRender.tiledMap);

        inputControllerSystem = new InputControllerSystem(physicWorld);
    }

    public void act(float delta) {
        physicWorld.act(delta);
    }

    public void draw() {
        float delta = Gdx.graphics.getDeltaTime();
        camera.update();
        batch.begin();
        backgroundRender.draw();
        engine.update(delta);
        physicWorld.render(delta);
        batch.end();
        worldRender.renderer.setView(camera);
        worldRender.render();
    }

    public void resize(int width, int height) {
      //  physicWorld.resize(width, height);
    }

    public void dispose() {
        worldRender.dispose();
    }
}
