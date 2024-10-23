package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyMovementStrategy implements MovementStrategy {
    
    public Vector2D chase(GhostImpl ghost, Vector2D playerPosition, Direction playerDirection, Vector2D targetCorner, Vector2D blinkyPosition) {
        // Inky's (Blue Ghost) specific chase logic: target is Pac-Man's position
        // System.out.println("Inky is chasing Pac-Man at position: " + playerPosition);

        int tileSize = 16;  // Assuming each tile is 16 units
        Vector2D twoTilesAhead = playerPosition; 

        switch (playerDirection) { // Sets the target tile two squares ahead of pacman
            case UP:
                twoTilesAhead = playerPosition.add(new Vector2D(0, -2 * tileSize));
                break;
            case DOWN:
                twoTilesAhead = playerPosition.add(new Vector2D(0, 2 * tileSize));
                break;
            case LEFT:
                twoTilesAhead = playerPosition.add(new Vector2D(-2 * tileSize, 0));
                break;
            case RIGHT:
                twoTilesAhead = playerPosition.add(new Vector2D(2 * tileSize, 0));
                break;
            default:
                // Default to Pac-Man's current position if no direction is found
                System.out.println("No direction found, defaulting to Pac-Man's current position.");
                break;
        }
        
        Vector2D doubleVector = new Vector2D(
            (twoTilesAhead.getX() - blinkyPosition.getX()) * 2,
            (twoTilesAhead.getY() - blinkyPosition.getY()) * 2
        );

        Vector2D inkyTarget = blinkyPosition.add(doubleVector);
        System.out.println(
            "Inky Target: " + inkyTarget.getX()/16 + ", " + inkyTarget.getY()/16 + 
            ". \nPacman Target: " + playerPosition.getX()/16 + ", " + playerPosition.getY()/16);

        return inkyTarget;
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