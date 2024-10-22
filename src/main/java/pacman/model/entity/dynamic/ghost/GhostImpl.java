package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.MovementStrategy.MovementStrategy;
import pacman.model.entity.dynamic.physics.*;
import pacman.model.level.Level;
import pacman.model.maze.Maze;

import java.util.*;

/**
 * Concrete implementation of Ghost entity in Pac-Man Game
 */
public class GhostImpl implements Ghost {

    private static final int minimumDirectionCount = 8;
    private final Layer layer = Layer.FOREGROUND;
    private final Image image;
    private final BoundingBox boundingBox;
    private final Vector2D startingPosition;
    private final Vector2D targetCorner;
    private KinematicState kinematicState;
    private GhostMode ghostMode;
    private Vector2D targetLocation;
    private Vector2D playerPosition;
    private Direction currentDirection;
    private Set<Direction> possibleDirections;
    private Map<GhostMode, Double> speeds;
    private int currentDirectionCount = 0;
    private String name;
    private MovementStrategy movementStrategy;

    public GhostImpl(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, Vector2D targetCorner, MovementStrategy movementStrategy) {
        this.image = image;
        this.boundingBox = boundingBox;
        this.kinematicState = kinematicState;
        this.startingPosition = kinematicState.getPosition();
        this.ghostMode = ghostMode;
        this.possibleDirections = new HashSet<>();
        this.targetCorner = targetCorner;
        this.targetLocation = getTargetLocation();
        this.currentDirection = null;
        this.movementStrategy = movementStrategy; // Set the movement strategy
    }


    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        this.speeds = speeds;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void update() {
        updateDirection();
        kinematicState.update();
        boundingBox.setTopLeft(kinematicState.getPosition());
    }

    private void updateDirection() {
        // Ghosts update their target location when they reach an intersection
        if (Maze.isAtIntersection(this.possibleDirections)) {
            this.targetLocation = getTargetLocation();
        }

        Direction newDirection = selectDirection(possibleDirections);

        // Ghosts must continue in a direction for a minimum time before changing direction
        if (currentDirection != newDirection) {
            currentDirectionCount = 0;
        }
        currentDirection = newDirection;

        switch (currentDirection) {
            case LEFT -> kinematicState.left();
            case RIGHT -> kinematicState.right();
            case UP -> kinematicState.up();
            case DOWN -> kinematicState.down();
        }
    }

    private Vector2D getTargetLocation() {
        return switch (ghostMode) {
            case CHASE -> playerPosition;
            case SCATTER -> targetCorner;
        };
    }

    private Direction selectDirection(Set<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return currentDirection;
        }

        // Ghosts must continue in a direction for a minimum time before changing direction
        if (currentDirection != null && currentDirectionCount < minimumDirectionCount) {
            currentDirectionCount++;
            return currentDirection;
        }

        Map<Direction, Double> distances = new HashMap<>();

        for (Direction direction : possibleDirections) {
            // Ghosts never choose to reverse travel
            if (currentDirection == null || direction != currentDirection.opposite()) {
                distances.put(direction, Vector2D.calculateEuclideanDistance(kinematicState.getPotentialPosition(direction), targetLocation));
            }
        }

        // Only go the opposite way if trapped
        if (distances.isEmpty()) {
            return currentDirection.opposite();
        }

        // Select the direction that will reach the target location fastest
        return Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    @Override
    public void setGhostMode(GhostMode ghostMode) {
        this.ghostMode = ghostMode;
        kinematicState.setSpeed(speeds.get(ghostMode));
        // Ensure direction is switched
        currentDirectionCount = minimumDirectionCount;

        System.out.println("Ghost mode changed to: " + ghostMode);
    }

    @Override
    public boolean collidesWith(Renderable renderable) {
        return boundingBox.collidesWith(kinematicState.getSpeed(), kinematicState.getDirection(), renderable.getBoundingBox());
    }

    @Override
    public void collideWith(Level level, Renderable renderable) {
        if (level.isPlayer(renderable)) {
            level.handleLoseLife();
        }
    }

    @Override
    public void update(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return kinematicState.getPreviousPosition();
    }

    @Override
    public double getHeight() {
        return boundingBox.getHeight();
    }

    @Override
    public double getWidth() {
        return boundingBox.getWidth();
    }

    @Override
    public Vector2D getPosition() {
        return kinematicState.getPosition();
    }

    @Override
    public void setPosition(Vector2D position) {
        kinematicState.setPosition(position);
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void reset() {
        // Return ghost to starting position
        kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(startingPosition)
                .build();
        boundingBox.setTopLeft(startingPosition);
        ghostMode = GhostMode.SCATTER;
        currentDirectionCount = minimumDirectionCount;
    }

    @Override
    public void setPossibleDirections(Set<Direction> possibleDirections) {
        this.possibleDirections = possibleDirections;
    }

    @Override
    public Direction getDirection() {
        return kinematicState.getDirection();
    }

    @Override
    public Vector2D getCenter() {
        return new Vector2D(boundingBox.getMiddleX(), boundingBox.getMiddleY());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
