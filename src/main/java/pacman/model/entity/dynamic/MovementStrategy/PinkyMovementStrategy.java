package pacman.model.entity.dynamic.MovementStrategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class PinkyMovementStrategy implements MovementStrategy {
    
    public Vector2D chase(GhostImpl ghost, Vector2D playerPosition, Direction playerDirection, Vector2D targetCorner, Vector2D blinkyPosition) {
        // Blinky's specific chase logic: target is Pac-Man's position
        // System.out.println("Pinky is chasing Pac-Man at position: " + playerPosition);
        
        int tileSize = 16;  // Assuming each tile is 16 units
        Vector2D targetTile = playerPosition; 

        switch (playerDirection) {
            case UP:
                targetTile = playerPosition.add(new Vector2D(0, -4 * tileSize));
                break;
            case DOWN:
                targetTile = playerPosition.add(new Vector2D(0, 4 * tileSize));
                break;
            case LEFT:
                targetTile = playerPosition.add(new Vector2D(-4 * tileSize, 0));
                break;
            case RIGHT:
                targetTile = playerPosition.add(new Vector2D(4 * tileSize, 0));
                break;
            default:
                // Default to Pac-Man's current position if no direction is found
                System.out.println("No direction found, defaulting to Pac-Man's current position.");
                break;
        }

        //System.out.println("Pinky is targeting " + targetTile.getX() + ", " + targetTile.getY());
        return targetTile;
    }

}

/*
 * Four grid spaces ahead of Pac-Man (based on Pac-Man’s current direction)
*/