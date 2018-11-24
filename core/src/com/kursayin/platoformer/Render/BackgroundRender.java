package com.kursayin.platoformer.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kursayin.platoformer.Constants;

public class BackgroundRender {
    Texture background;
    Batch batch;

    private float velocity = 10;

    private int x = 0;

    public BackgroundRender(Batch batch) {
        background = new Texture(Gdx.files.internal("background/sky.png"));
        // background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        this.batch = batch;
    }

    public void act(float delta) {
        // x += velocity ;
    }

    public void draw() {
        batch.draw(background, 0, 0, x, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }
}
