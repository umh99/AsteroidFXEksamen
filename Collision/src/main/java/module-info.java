import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    exports dk.sdu.mmmi.cbse.collisionsystem;
    requires Common;
    requires CommonAsteroids;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
    uses dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
}