package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gamedata, World world) {

        // Load splitter én gang i stedet for i hvert kald
        ServiceLoader<IAsteroidSplitter> loader = ServiceLoader.load(IAsteroidSplitter.class);
        IAsteroidSplitter splitter = loader.findFirst().orElse(null);

        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {

                if (e1.getId().equals(e2.getId())) continue;

                if (this.collides(e1, e2)) {

                    // Split begge hvis de er asteroider
                    if (splitter != null) {
                        if (isAsteroid(e1)) {
                            splitter.createAsteroids(e1, world);
                        }
                        if (isAsteroid(e2)) {
                            splitter.createAsteroids(e2, world);
                        }
                    }
                    System.out.println("[DEBUG] Removing entity: " + e1.getId());
                    world.removeEntity(e1);
                    world.removeEntity(e2);
                    break; // e1 er nu fjernet, undgå flere sammenligninger
                }
            }
        }
    }

    public boolean collides(Entity e1, Entity e2) {
        float dx = (float) e1.getX() - (float) e2.getX();
        float dy = (float) e1.getY() - (float) e2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (e1.getRadius() + e2.getRadius());
    }

    private boolean isAsteroid(Entity entity) {
        return entity.getClass().getSimpleName().equals("Asteroid");
    }
}