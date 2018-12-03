package com.kursayin.platoformer.Render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.kursayin.platoformer.Constants;

public class WorldRender {
    OrthogonalTiledMapRenderer renderer;
    Batch batch;
    TiledMap tiledMap;

    public WorldRender(String mapName, Batch batch) {
        this.batch = batch;
        tiledMap = new TmxMapLoader().load("map/" + mapName);
        renderer = new OrthogonalTiledMapRenderer(tiledMap, 13f / Constants.SCREEN_HEIGHT, batch);
    }
    public void render() {
        renderer.render();
    }

    public void dispose () {
        renderer.dispose();
    }
}
