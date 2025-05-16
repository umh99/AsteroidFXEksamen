package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;


public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        // Check if the asteroid is large enough to split
        if (e.getRadius() <= 5) {
            return; // Do not split if the asteroid is too small
        }

        // Create two smaller asteroids
        for (int i = 0; i < 2; i++) {
            Entity smallerAsteroid = new Entity();
            smallerAsteroid.setX(e.getX());
            smallerAsteroid.setY(e.getY());
            smallerAsteroid.setRadius(e.getRadius() / 2);

            // Randomize the direction of the smaller asteroids
            double randomAngle = Math.random() * 360;
            smallerAsteroid.setRotation(randomAngle);

            // Set the polygon coordinates for the smaller asteroid
            int newSize = (int) smallerAsteroid.getRadius();
            smallerAsteroid.setPolygonCoordinates(newSize, -newSize, -newSize, -newSize, -newSize, newSize, newSize, newSize);

            // Add the smaller asteroid to the world
            world.addEntity(smallerAsteroid);
        }

        // Remove the original asteroid from the world
        world.removeEntity(e);
    }
}
