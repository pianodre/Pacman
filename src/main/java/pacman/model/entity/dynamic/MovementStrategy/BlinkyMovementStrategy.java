package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.player.Pacman;

public class BlinkyMovementStrategy implements MovementStrategy {
    @Override
    public void move(GhostImpl ghost, Pacman pacMan) {
        // Blinky's specific chase logic
        System.out.println("Blinky is moving towards Pac-Man at position: " + pacMan.getPosition());
    }
}

public class BlinkyMovementStrategy implements MovementStrategy {
    @Override
    public Vector2D getTargetLocation(GhostImpl ghost, Vector2D playerPosition) {
        // Blinky's specific chase logic: target is Pac-Man's position
        System.out.println("Blinky is moving towards Pac-Man at position: " + playerPosition);
        return playerPosition;
    }
}