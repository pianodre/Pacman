package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyMovementStrategy implements MovementStrategy {
    
    public Vector2D getTargetLocation(GhostImpl ghost, Vector2D playerPosition) {
        // Blinky's specific chase logic: target is Pac-Man's position
        System.out.println("Blinky is moving towards Pac-Man at position: " + playerPosition);
        return playerPosition;
    }

    @Override
    public void move(GhostImpl ghost, Vector2D playerPosition) {
        System.out.println("Clyde is moving towards Pac-Man at position: " + playerPosition);
    }
}