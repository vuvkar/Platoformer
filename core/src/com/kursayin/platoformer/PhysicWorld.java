package com.kursayin.platoformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class PhysicWorld  implements Screen{
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    
    public void render(float delta) {
        debugRenderer.render(world, camera.combined);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
    }

    public void act(float delta) {

    }

    @Override
    public void show() {
        world = new World(new Vector2(0,-9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth()/25, Gdx.graphics.getHeight()/25);

        Gdx.input.setInputProcessor(new InputController(){
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.ESCAPE)
                    Gdx.app.exit();
                return true;
            }
        });

        //BALL
        //body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(0,2);

        //circle
        CircleShape ballShapeDef = new CircleShape();
        ballShapeDef.setRadius(.5f);

        // ChainShape boxShapeDef = new ChainShape();
        // boxShapeDef.createChain(new Vector2[]{new Vector2(-1,1), new Vector2(1,1), new Vector2(1,3), new Vector2(-1,3)});

        //fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShapeDef;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        ballShapeDef.dispose();

        //GROUND
        //body
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(0,0);

        //shape
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] { new Vector2(-50, 0), new Vector2(50, 0) });
        
        // fixture
        fixtureDef.shape = groundShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        groundShape.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
        camera.update();
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