package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public interface MovementStrategy {

    Vector2D chase(GhostImpl ghost, Vector2D playerPosition);
    
}
