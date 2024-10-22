package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.dynamic.player.Pacman;

public interface MovementStrategy {
    void move(GhostImpl ghost, Vector2D playerPosition);
    Vector2D getTargetLocation(GhostImpl ghost, Vector2D playerPosition);
}
