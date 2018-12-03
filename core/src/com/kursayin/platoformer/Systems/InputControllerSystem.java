package com.kursayin.platoformer.Systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.kursayin.platoformer.PhysicWorld;

public class InputControllerSystem {
    PhysicWorld world;
    InputProcessor inputProcessor;

    public InputControllerSystem(PhysicWorld world) {
        this.world = world;
        configureInputController();
    }

    private void configureInputController() {
        inputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        world.startedMovingCharacter(PhysicWorld.GoingDirection.UP);
                        break;
                    case Input.Keys.A:
                        world.startedMovingCharacter(PhysicWorld.GoingDirection.LEFT);
                        break;
                    case Input.Keys.D:
                        world.startedMovingCharacter(PhysicWorld.GoingDirection.RIGHT);
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        world.releaseDirection(PhysicWorld.GoingDirection.UP);
                        break;
                    case Input.Keys.A:
                        world.releaseDirection(PhysicWorld.GoingDirection.LEFT);
                        break;
                    case Input.Keys.D:
                        world.releaseDirection(PhysicWorld.GoingDirection.RIGHT);
                        break;
                }
                return true;
            }
        };

        Gdx.input.setInputProcessor(inputProcessor);
    }
}
