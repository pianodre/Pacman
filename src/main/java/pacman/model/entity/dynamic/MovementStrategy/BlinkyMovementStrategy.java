package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class BlinkyMovementStrategy implements MovementStrategy {

    public Vector2D chase(GhostImpl ghost, Vector2D playerPosition, Direction playerDirection, Vector2D targetCorner, Vector2D blinkyPosition) {
        // Blinky's specific chase logic: target is Pac-Man's position
        // System.out.println("Blinky is chasing Pac-Man at position: " + playerPosition);
        return playerPosition;
    }

}

/*
 * Pac-Man’s position
 */