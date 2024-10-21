package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.player.Pacman;

public class InkyMovementStrategy implements MovementStrategy {
    @Override
    public void move(GhostImpl ghost, Pacman pacMan) {
        System.out.println("Inky is moving towards Pac-Man at position: " + pacMan.getPosition());
    }
}