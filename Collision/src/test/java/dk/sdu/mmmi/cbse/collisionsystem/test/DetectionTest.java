package dk.sdu.mmmi.cbse.collisionsystem.test;

import dk.sdu.mmmi.cbse.common.data.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DetectionTest {

    @Test
    void testCollisionDetection() {
        // Create two entities
        Entity playerShip = new Entity();
        playerShip.setX(50);
        playerShip.setY(50);
        playerShip.setRadius(10);


        Entity asteroid = new Entity();
        asteroid.setX(55);
        asteroid.setY(55);
        asteroid.setRadius(10);

        // Check if collision is detected
        assertTrue(isColliding(playerShip, asteroid), "Collision should be detected");

        // Move asteroid further away
        asteroid.setX(100);
        asteroid.setY(100);

        // Check if collision is not detected (set as asserttrue to test for failing)
        assertFalse(isColliding(playerShip, asteroid), "Collision should not be detected");
    }

    private boolean isColliding(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}