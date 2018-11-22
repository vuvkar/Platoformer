package com.kursayin.platoformer.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class PhysicWorld  implements Screen{
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    
    public void render(float delta) {
        debugRenderer.render(world, camera.combined);
    }

    public void act(float delta) {

    }

    @Override
    public void show() {
        world = new World(new Vector2(0,-9.81f), true);

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10);

        //body
        BodyDef body = new BodyDef();
        body.type = BodyType.DynamicBody;
        body.position.set(0,0);

        //circle
        CircleShape shape = new CircleShape();
        shape.setRadius(.5f);

        //fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0;

        world.createBody(body).createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}