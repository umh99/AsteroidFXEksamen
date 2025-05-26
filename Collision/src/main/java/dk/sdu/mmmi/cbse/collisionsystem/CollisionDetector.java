package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;

/**
 * Detects collisions after the main entity-processing phase and removes the
 * colliding entities.  Whenever a collision yields points (e.g. hitting an
 * asteroid or enemy) it sends a tiny JSON payload to the scoring microservice.
 */
public class CollisionDetector implements IPostEntityProcessingService {


    private static final String SCORE_URL = "http://localhost:8080/score";
    private final HttpClient http = HttpClient.newHttpClient();

    private void sendScore(int value) {
        try {
            URL url = new URL("http://localhost:8080/score");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String json = "{\"value\":" + value + "}";
            try (OutputStream os = con.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }
            con.getInputStream().close();
            con.disconnect();
        } catch (Exception ignored) { }
    }
    // ------------------------------------------------------------------

    @Override
    public void process(GameData gameData, World world) {

        IAsteroidSplitter splitter = ServiceLoader.load(IAsteroidSplitter.class)
                .findFirst()
                .orElse(null);

        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.getId().equals(e2.getId())) continue;   // same entity

                if (collides(e1, e2)) {

                    /* ---------- scoring points ---------- */
                    if (isAsteroid(e1) || isAsteroid(e2)) {
                        sendScore(100);                  // asteroid hit
                    } else {
                        sendScore(200);                  // enemy hit
                    }
                    /* ------------------------------------ */

                    /* split asteroids if applicable */
                    if (splitter != null) {
                        if (isAsteroid(e1)) splitter.createAsteroids(e1, world);
                        if (isAsteroid(e2)) splitter.createAsteroids(e2, world);
                    }

                    world.removeEntity(e1);
                    world.removeEntity(e2);
                    break;  // restart nested loop
                }
            }
        }
    }

    /* ---------- helpers ---------- */

    private boolean collides(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distance = Math.hypot(dx, dy);
        return distance < (a.getRadius() + b.getRadius());
    }

    private boolean isAsteroid(Entity e) {
        return e.getClass().getSimpleName().equals("Asteroid");
    }
}
