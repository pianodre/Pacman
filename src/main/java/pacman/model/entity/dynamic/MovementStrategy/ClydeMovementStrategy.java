package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.player.Pacman;

public class ClydeMovementStrategy implements MovementStrategy {
    @Override
    public void move(GhostImpl ghost, Pacman pacMan) {
        System.out.println("Clyde is moving towards Pac-Man at position: " + pacMan.getPosition());
    }
}