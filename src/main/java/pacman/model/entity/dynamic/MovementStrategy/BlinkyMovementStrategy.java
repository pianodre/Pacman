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