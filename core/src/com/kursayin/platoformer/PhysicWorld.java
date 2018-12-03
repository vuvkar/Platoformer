package com.kursayin.platoformer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class PhysicWorld {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private TiledMap map;

    private Body box;

    private Body ground;

    private boolean startedLeft = false;
    private boolean startedRight = false;
    private boolean onGround = false;
    private boolean startedUp = false;
    private float maxVelocity = 20;

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Vector2 direction = new Vector2(50 * Constants.BSCALE, 50 * Constants.BSCALE);

    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    public PhysicWorld(OrthographicCamera camera, TiledMap map) {
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        this.map = map;
        this.camera = camera;

        configureContactListener();
        startWorldCreation();
    }

    public void startWorldCreation() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        createGround();
        createCharacter();

    }

    public void createCharacter() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f,10);

        float width = 8f * Constants.BSCALE;
        float height = 16f * Constants.BSCALE;

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(2f * width / 6f, height / 4f, new Vector2(width / 2f, 3f * height / 4f), 0);

        //fixture
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .1f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;

        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef).setUserData("player");

        boxShape.dispose();

        PolygonShape boxSquatShape = new PolygonShape();
        boxSquatShape.setAsBox(2f * width / 6f, height / 4f, new Vector2(width / 2f, height / 4f), 0);

        fixtureDef.shape = boxSquatShape;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;

        box.createFixture(fixtureDef).setUserData("squat-player");

        boxSquatShape.dispose();

        box.setLinearDamping(0.4f);

        MassData massData = box.getMassData();
        massData.mass = 0.1f;
        box.setMassData(massData);

        box.setFixedRotation(true);
    }

    public void createGround() {
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals("ground") || layer.getName().equals("pipes")) {
                for (MapObject object: layer.getObjects()) {
                    if(object instanceof RectangleMapObject) {
                        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                        bodyDef.type = BodyDef.BodyType.StaticBody;
                        bodyDef.position.set((rectangle.x + rectangle.width / 2 ) * Constants.BSCALE, (rectangle.y + rectangle.height /2f) * Constants.BSCALE);
                        PolygonShape groundShape = new PolygonShape();
                        groundShape.setAsBox(rectangle.width / 2f * Constants.BSCALE, rectangle.height / 2f * Constants.BSCALE);
                        fixtureDef.shape = groundShape;
                        ground = world.createBody(bodyDef);
                        ground.createFixture(fixtureDef).setUserData("ground");

                        groundShape.dispose();
                    }
                }
            }
        }
    }

    public void render(float delta) {
        world.step(delta, VELOCITYITERATIONS, POSITIONITERATIONS);
        camera.position.set(box.getPosition().x, camera.position.y, 0);
        debugRenderer.render(world, camera.combined);
    }

    public void startedMovingCharacter(GoingDirection directionTo) {
        switch (directionTo) {
            case LEFT:
                startedLeft = true;
                box.setAngularVelocity(0);
                break;
            case RIGHT:
                startedRight = true;
                box.setAngularVelocity(0);
                break;
            case UP:
                if (onGround) {
                    direction.x = 0;
                    direction.y = 50 * Constants.BSCALE;
                    box.applyLinearImpulse(direction, box.getLocalCenter(), true);
                }
        }
    }

    public void releaseDirection(GoingDirection direction) {
        switch (direction) {
            case UP:
                startedUp = false;
                break;
            case LEFT:
                startedLeft = false;
                break;
            case RIGHT:
                startedRight = false;
                break;
        }

    }

    public void act(float delta) {
        if (startedLeft && Math.abs(box.getLinearVelocity().x) < maxVelocity) {
            direction.x = -1 * Constants.BSCALE;
            direction.y = 0;
            box.applyLinearImpulse(direction, box.getPosition(), true);
        }

        if (startedRight && Math.abs(box.getLinearVelocity().x) < maxVelocity) {
            direction.x = 1 * Constants.BSCALE;
            direction.y = 0;
            box.applyLinearImpulse(direction, box.getPosition(), true);
        }
    }

    public void configureContactListener() {
        world.setContactListener(new WorldContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                if (fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" || fixB.getUserData() == "player" || fixB.getUserData() == "squat-player") {
                    Fixture playerFix = fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" ? fixA : fixB;
                    Fixture objectFix = playerFix.getUserData() == fixA.getUserData() ? fixB : fixA;

                    if (objectFix.getUserData() != null) {
                        if (objectFix.getUserData() == "ground") {
                            onGround = true;
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                if (fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" || fixB.getUserData() == "player" || fixB.getUserData() == "squat-player") {
                    Fixture playerFix = fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" ? fixA : fixB;
                    Fixture objectFix = playerFix.getUserData() == fixA.getUserData() ? fixB : fixA;

                    if (objectFix.getUserData() != null) {
                        if (objectFix.getUserData() == "ground") {
                            onGround = false;
                        }
                    }
                }
            }
        });
    }

    public enum GoingDirection {
        UP, LEFT, RIGHT
    }
}