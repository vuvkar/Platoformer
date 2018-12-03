package com.kursayin.platoformer.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kursayin.platoformer.Constants;

public class BackgroundRender {
    Texture background;
    Batch batch;
    Camera camera;

    public BackgroundRender(Batch batch, Camera camera) {
        this.camera = camera;

        background = new Texture(Gdx.files.internal("background/sky.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        this.batch = batch;
    }

    public void draw() {
       // Vector3 position = camera.view.getTranslation(new Vector3());
        //batch.draw(background, position.x, position.y, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }
}
