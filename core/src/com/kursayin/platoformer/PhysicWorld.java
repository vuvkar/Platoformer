package com.kursayin.platoformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class PhysicWorld {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private float speed = 500;
    private Vector2 movement = new Vector2();
    private Body box;

    private Vector2 direction = new Vector2(500, 500);

    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    
    public void render(float delta) {
        world.step(delta, VELOCITYITERATIONS, POSITIONITERATIONS);
        
        camera.position.set(box.getPosition().x, box.getPosition().y, 0);
        camera.update();

        debugRenderer.render(world, camera.combined);
    }

    public void act(float delta) {

    }

    public PhysicWorld() {
        world = new World(new Vector2(0,-9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth()/25, Gdx.graphics.getHeight()/25);

        Gdx.input.setInputProcessor(new InputController(){
            @Override
            public boolean keyDown(int keycode) {
                switch(keycode){
                    case Keys.ESCAPE:
                        Gdx.app.exit();
                        break;
                    case Keys.W:
                        direction.x = 0;
                        direction.y = 50;
                        box.applyLinearImpulse (direction, box.getLocalCenter(), true);
                        break;
                    case Keys.S:
                        break;
                    case Keys.A:
                        direction.x = 50;
                        direction.y = 0;
                        box.applyLinearImpulse (direction, box.getLocalCenter(), true);
                        break;
                    case Keys.D:
                        direction.x = -50;
                        direction.y = 0;
                        box.applyLinearImpulse (direction, box.getLocalCenter(), true);
                        break;
                }
                return true;
            }
        });

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //GROUND
        //body
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(0,0);

        //shape
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] { new Vector2(-500, 0), new Vector2(500, 0) });
        
        // fixture
        fixtureDef.shape = groundShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        groundShape.dispose();

        //box
        //body
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(2.25f,10);

        //shape
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(.5f, 1);

        //fixture
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;

        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef);

        boxShape.dispose();

        //BALL
        //body
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(3,5);

        //shape
        CircleShape ballShapeDef = new CircleShape();
        ballShapeDef.setPosition(new Vector2(0,-.5f));
        ballShapeDef.setRadius(.5f);

        // ChainShape boxShapeDef = new ChainShape();
        // boxShapeDef.createChain(new Vector2[]{new Vector2(-1,1), new Vector2(1,1), new Vector2(1,3), new Vector2(-1,3)});

        //fixture
        fixtureDef.shape = ballShapeDef;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        ballShapeDef.dispose();

        //box squat
        //box
        // PolygonShape boxSquatShape = new PolygonShape();
        // boxSquatShape.setAsBox(0.5f, 0.5f, new Vector2(0,-.5f), 0);

        // //fixture
        // fixtureDef.shape = boxSquatShape;
        // fixtureDef.friction = .75f;
        // fixtureDef.restitution = .1f;
        // fixtureDef.density = 5;

        // box.createFixture(fixtureDef);

        // boxSquatShape.dispose();
        
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
        camera.update();
    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }

    public void dispose() {

    }
}