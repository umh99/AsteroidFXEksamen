package dk.sdu.mmmi.cbse.playersystemtest;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerControlSystemTest {

    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private World world;
    private Entity player;
    private BulletSPI mockBulletSPI;

    @BeforeEach
    void setUp() {
        // Create a mock BulletSPI using Mockito
        mockBulletSPI = mock(BulletSPI.class);
        when(mockBulletSPI.createBullet(any(Entity.class), any(GameData.class))).thenReturn(new Entity());

        // Extend PlayerControlSystem to inject the mock BulletSPI
        playerControlSystem = new PlayerControlSystem() {
            @Override
            protected java.util.Collection<? extends BulletSPI> getBulletSPIs() {
                return Collections.singletonList(mockBulletSPI);
            }
        };

        gameData = new GameData();
        world = new World();
        player = new dk.sdu.mmmi.cbse.playersystem.Player();
        player.setX(100);
        player.setY(100);
        player.setRotation(0);

        world.addEntity(player);
        gameData.setDisplayWidth(200);
        gameData.setDisplayHeight(200);
    }

    @Test
    void testPlayerRotatesLeft() {
        gameData.getKeys().setKey(GameKeys.LEFT, true);
        playerControlSystem.process(gameData, world);
        assertEquals(-5, player.getRotation(), "Player should rotate left by 5 degrees");
    }

    @Test
    void testPlayerRotatesRight() {
        gameData.getKeys().setKey(GameKeys.RIGHT, true);
        playerControlSystem.process(gameData, world);
        assertEquals(5, player.getRotation(), "Player should rotate right by 5 degrees");
    }

    @Test
    void testPlayerMovesForward() {
        gameData.getKeys().setKey(GameKeys.UP, true);
        player.setRotation(0);
        playerControlSystem.process(gameData, world);
        assertTrue(player.getX() > 100, "Player should move to the right");
        assertEquals(100, player.getY(), "Player Y should remain unchanged when facing right");
    }

    @Test
    void testPlayerFiresBullet() {
        gameData.getKeys().setKey(GameKeys.SPACE, true);
        int before = world.getEntities().size();
        playerControlSystem.process(gameData, world);
        int after = world.getEntities().size();
        assertTrue(after > before, "A bullet should be added to the world when SPACE is pressed");
        verify(mockBulletSPI, times(1)).createBullet(any(Entity.class), any(GameData.class));
    }

    @Test
    void testPlayerWrapsScreen() {
        player.setX(-10);
        player.setY(-10);
        playerControlSystem.process(gameData, world);
        assertEquals(1, player.getX(), "Player X should wrap to 1 if less than 0");
        assertEquals(1, player.getY(), "Player Y should wrap to 1 if less than 0");
    }
}
