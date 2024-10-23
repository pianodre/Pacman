package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public interface MovementStrategy {

    Vector2D chase(GhostImpl ghost, Vector2D playerPosition, Direction playerDirection, Vector2D targetCorner, Vector2D blinkyPosition);
    
}
