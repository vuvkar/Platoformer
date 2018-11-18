package com.kursayin.platoformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kursayin.platoformer.Render.PlatoformerMainRender;
import com.kursayin.platoformer.UI.PlatoformerUI;

public class Platoformer extends ApplicationAdapter {
	PlatoformerMainRender mainRender;
	PlatoformerUI platoformerUI;
	
	@Override
	public void create () {
		mainRender = new PlatoformerMainRender(this);
		platoformerUI = new PlatoformerUI();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();
		platoformerUI.act(delta);
		mainRender.act(delta);

		mainRender.draw();
		platoformerUI.draw();


	}
	
	@Override
	public void dispose () {
		platoformerUI.dispoe();
		mainRender.dispose();
	}
}
