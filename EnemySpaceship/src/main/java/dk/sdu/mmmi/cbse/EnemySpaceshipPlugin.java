package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemySpaceshipPlugin implements IGamePluginService {
    private Random random = new Random();
    private Entity enemySpaceship;

    public EnemySpaceshipPlugin() {
    }


    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemySpaceship);
    }


    @Override
    public void start(GameData gameData, World world) {
        enemySpaceship = createEnemySpaceship(gameData);
        world.addEntity(enemySpaceship);
    }

    private Entity createEnemySpaceship(GameData gameData) {

        Entity enemySpaceship = new EnemySpaceship();
        enemySpaceship.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemySpaceship.setX(random.nextInt(gameData.getDisplayHeight()));
        enemySpaceship.setY(random.nextInt(gameData.getDisplayWidth()));
        enemySpaceship.setRadius(8);
        return enemySpaceship;
    }
}