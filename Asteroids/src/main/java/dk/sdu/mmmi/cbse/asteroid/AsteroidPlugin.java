package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsteroidPlugin implements IGamePluginService {

    private ScheduledExecutorService scheduler;

    @Override
    public void start(GameData gameData, World world) {
        // Initialize and start the scheduler
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
            System.out.println("Asteroid spawned at: " + asteroid.getX() + ", " + asteroid.getY());
        }, 0, 5, TimeUnit.SECONDS); // Spawn every 5 seconds
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Stop the scheduler
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }

        // Remove all asteroids from the world
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();

        // Randomize size
        int size = rnd.nextInt(10) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);

        // Randomize position within game boundaries
        asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));

        // Set radius and random rotation
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(360));

        return asteroid;
    }
}