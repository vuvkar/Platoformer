package com.kursayin.platoformer.Render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kursayin.platoformer.Platoformer;

public class PlatoformerMainRender {
    Platoformer platoformer;
    OrthographicCamera camera;
    SpriteBatch batch;

    BackgroundRender backgroundRender;

    public PlatoformerMainRender(Platoformer platoformer) {
        this.platoformer = platoformer;
        batch = new SpriteBatch();
        backgroundRender = new BackgroundRender(batch);
    }
    
    public void act(float delta) {
        backgroundRender.act(delta);
    }
    
    public void draw() {
        batch.begin();
        backgroundRender.draw();
        batch.end();
    }

    public void dispose() {

    }
}
