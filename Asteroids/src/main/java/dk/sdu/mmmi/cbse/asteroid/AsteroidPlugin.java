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



    private Entity createAsteroid(GameData gamedata) {
        Entity asteroid = new Asteroid();
        Random rand = new Random();
        int size = 6;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(rand.nextInt(gamedata.getDisplayWidth()));
        asteroid.setY(rand.nextInt(gamedata.getDisplayHeight()));
        asteroid.setRadius(size);
        asteroid.setRotation(rand.nextInt(90));
        return asteroid;
    }

    @Override
    public void start(GameData gameData, World world) {
            scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r);
                t.setDaemon(true); // allows JVM to exit when only daemon threads are running
                t.setName("Asteroid-Spawner");
                return t;
            });

            scheduler.scheduleAtFixedRate(() -> {
                Entity asteroid = createAsteroid(gameData);
                world.addEntity(asteroid);
            }, 0, 3, TimeUnit.SECONDS);
        }

    @Override
    public void stop(GameData gameData, World world) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }

    }

}


