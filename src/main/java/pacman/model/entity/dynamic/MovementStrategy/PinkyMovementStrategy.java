package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public class PinkyMovementStrategy implements MovementStrategy {
    
    public Vector2D chase(GhostImpl ghost, Vector2D playerPosition, Vector2D targetCorner) {
        // Blinky's specific chase logic: target is Pac-Man's position
        // System.out.println("Pinky is chasing Pac-Man at position: " + playerPosition);
        return playerPosition;
    }

}

/*
 * Four grid spaces ahead of Pac-Man (based on Pac-Man’s current direction)
 */