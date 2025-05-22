package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;


public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createAsteroids(Entity original, World world) {

        float newRadius = original.getRadius() /2;

        if (newRadius < 3) return;

        for (int i = 0; i < 2; i++) {
            Entity smallAsteroid = new Asteroid();
            smallAsteroid.setRadius(newRadius);
            double angle = Math.random() * 2 * Math.PI;
            double distance = original.getRadius() + 5; // Garanteret uden for kollisionsafstand

            smallAsteroid.setX(original.getX() + Math.cos(angle) * distance);
            smallAsteroid.setY(original.getY() + Math.sin(angle) * distance);
            smallAsteroid.setRotation((int) (Math.random() * 360));
            smallAsteroid.setPolygonCoordinates(
                    newRadius, -newRadius, -newRadius, -newRadius, -newRadius, newRadius, newRadius, newRadius
            );

            world.addEntity(smallAsteroid);
            System.out.println("[DEBUG] Splitting asteroid with radius: " + smallAsteroid.getRadius());
        }
    }
}