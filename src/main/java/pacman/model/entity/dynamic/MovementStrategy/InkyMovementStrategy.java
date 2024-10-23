package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyMovementStrategy implements MovementStrategy {
    
    public Vector2D chase(GhostImpl ghost, Vector2D playerPosition, Vector2D targetCorner) {
        // Inky's (Blue Ghost) specific chase logic: target is Pac-Man's position
        // System.out.println("Inky is chasing Pac-Man at position: " + playerPosition);
        return playerPosition;
    }

}

/*
 * Bashful’s target position is found by,
 * first determining the position two grid spaces ahead of Pac-Man, 
 * and then doubling the vector from Shadow/Blinky to that position. 
 * For example, if Pac-Man is moving right at (3, 6) and Shadow/Blinky is at (1, 9), 
 * the position two spaces ahead of Pac-Man would be (5, 6). 
 * The vector from Shadow/Blinky to (5, 6) is (4, -3). 
 * Doubling this vector gives (8, -6), making Bashful’s target location (9, 3).
 */