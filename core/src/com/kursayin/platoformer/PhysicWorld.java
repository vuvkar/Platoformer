package com.kursayin.platoformer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class PhysicWorld {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private Body box;

    private Body ground;

    private boolean startedLeft = false;
    private boolean startedRight = false;
    private boolean onGround = false; 
    private boolean startedUp = false;
    private float maxVelocity = 20;


    private Vector2 direction = new Vector2(500, 500);

    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    
    public void render(float delta) {
        world.step(delta, VELOCITYITERATIONS, POSITIONITERATIONS);
        
        camera.position.set(box.getPosition().x, box.getPosition().y, 0);
        camera.update();

        debugRenderer.render(world, camera.combined);
    }

    public void act(float delta) {
        if(startedLeft &&  Math.abs(box.getLinearVelocity().x) < maxVelocity) {
            direction.x = -10;
            direction.y = 0;
            box.applyLinearImpulse (direction, box.getPosition(), true);
        }

        if(startedRight &&  Math.abs(box.getLinearVelocity().x) < maxVelocity) {
            direction.x = 10;
            direction.y = 0;
            box.applyLinearImpulse (direction, box.getPosition(), true);
        }
    }

    public PhysicWorld() {
        world = new World(new Vector2(0,-9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth()/25, Gdx.graphics.getHeight()/25);

        world.setContactListener(new WorldContactListener(){
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                if(fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" || fixB.getUserData() == "player" || fixB.getUserData() == "squat-player" ){
                    Fixture playerFix = fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" ? fixA : fixB;
                    Fixture objectFix = playerFix.getUserData() == fixA.getUserData()? fixB : fixA; 

                    if(objectFix.getUserData() != null){
                        if(objectFix.getUserData() == "ground"){
                            onGround = true;
                        }
                    } 
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                if(fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" || fixB.getUserData() == "player" || fixB.getUserData() == "squat-player" ){
                    Fixture playerFix = fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" ? fixA : fixB;
                    Fixture objectFix = playerFix.getUserData() == fixA.getUserData()? fixB : fixA; 

                    if(objectFix.getUserData() != null){
                        if(objectFix.getUserData() == "ground"){
                            onGround = false;
                        }
                    } 
                }
            }
        });


        Gdx.input.setInputProcessor(new InputController(){
            @Override
            public boolean keyDown(int keycode) {
                switch(keycode){
                    case Keys.W:
                        if(onGround) {
                            direction.x = 0;
                            direction.y = 500;
                            box.applyLinearImpulse (direction, box.getPosition(), true);
                        }
                        break;
                    case Keys.S:
                        box.getFixtureList().get(0).setSensor(true);
                        box.getFixtureList().get(1).setSensor(false);
                        // if(!onGround){
                        //     direction.x = 0;
                        //     direction.y = -300;
                        //     box.applyLinearImpulse (direction, box.getPosition(), true);
                        // }
                        break;
                    case Keys.A:
                        startedLeft = true;
                        box.setAngularVelocity(0);
                        break;
                    case Keys.D:
                        startedRight = true;
                        box.setAngularVelocity(0);
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode){
                switch(keycode){
                    case Keys.W:
                        startedUp = false;
                        break;
                    case Keys.S:
                        box.getFixtureList().get(0).setSensor(false);
                        box.getFixtureList().get(1).setSensor(true);
                        break;
                    case Keys.A:
                        startedLeft = false;
                        break;
                    case Keys.D:
                        startedRight = false;
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
        fixtureDef.friction = 10;
        fixtureDef.restitution = 0;

        ground = world.createBody(bodyDef);
        ground.createFixture(fixtureDef).setUserData("ground");

        groundShape.dispose();

        

        //BALL
        //body
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(-3,10);

        //shape
        CircleShape ballShapeDef = new CircleShape();
        ballShapeDef.setPosition(new Vector2(0,-.5f));
        ballShapeDef.setRadius(3f);

        //fixture
        fixtureDef.shape = ballShapeDef;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);
        
        ballShapeDef.dispose();

        //box
        //body
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(2.25f,10);

        float width = 4;
        float height = 4;


        //shape
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(2*width / 6, height / 4, new Vector2(width / 2, 3 * height / 4), 0);


        //fixture
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .1f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;

        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef).setUserData("player");

        boxShape.dispose();
        
        //box squat
        //box
        PolygonShape boxSquatShape = new PolygonShape();
        boxSquatShape.setAsBox(2*width / 6, height / 4, new Vector2(width / 2, height / 4), 0);
        
        // //fixture
        fixtureDef.shape = boxSquatShape;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;
        
        
        box.createFixture(fixtureDef).setUserData("squat-player");
        
        boxSquatShape.dispose();
        
        box.setAngularDamping(0);
        box.setLinearDamping(0.4f);
        MassData massData = box.getMassData();
        massData.mass = 20f;
        box.setMassData(massData);
        // box.setGravityScale(1.5f);
        box.setFixedRotation(true);
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