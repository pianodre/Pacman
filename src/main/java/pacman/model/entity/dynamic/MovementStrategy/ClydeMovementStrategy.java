package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public class ClydeMovementStrategy implements MovementStrategy {

    public Vector2D chase(GhostImpl ghost, Vector2D playerPosition, Vector2D targetCorner) {
        // Cylde's (Orange Ghost) specific chase logic: target is Pac-Man's position
        
        Vector2D cyldePosition = ghost.getPosition();

        double x = Math.pow(cyldePosition.getX() - playerPosition.getX(), 2); // X Pos
        double y = Math.pow(cyldePosition.getY() - playerPosition.getY(), 2); // Y Pos

        double distanceFromPacman = Math.sqrt(x+y)/16; // each square is 16x16 pixels

        // System.out.println("Clyde is " + distanceFromPacman + " from pacman");

        if (distanceFromPacman <= 8) {
            return playerPosition;
        } else {
            return targetCorner;
        }
    }

}

/*
 * If more than 8 grid spaces away from Pac- Man (straight line distance), 
 * its target location is Pac-Man. Otherwise, 
 * its target location is the bottom-left corner
 */