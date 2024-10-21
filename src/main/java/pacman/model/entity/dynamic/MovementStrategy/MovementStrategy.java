package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.player.Pacman;

public interface MovementStrategy {
    void move(GhostImpl ghost, Pacman pacMan);
}
