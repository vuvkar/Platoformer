package com.kursayin.platoformer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" || fixB.getUserData() == "player" || fixB.getUserData() == "squat-player" ){
            Fixture playerFix = fixA.getUserData() == "player" || fixA.getUserData() == "squat-player" ? fixA : fixB;
            Fixture objectFix = playerFix.getUserData() == fixA.getUserData()? fixB : fixA; 

            if(objectFix.getUserData() != null){
                
            } 
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}