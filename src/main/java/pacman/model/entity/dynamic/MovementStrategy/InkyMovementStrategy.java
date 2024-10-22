package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyMovementStrategy implements MovementStrategy {
    
    public Vector2D chase(GhostImpl ghost, Vector2D playerPosition) {
        // Blinky's specific chase logic: target is Pac-Man's position
        System.out.println("Inky is chasing Pac-Man at position: " + playerPosition);
        return playerPosition;
    }

}