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
    private Vector2D playerPosition = new Vector2D(0, 0);
    private Vector2D blinkyPosition;
    private Direction playerDirection;
    private Direction currentDirection;
    private Set<Direction> possibleDirections;
    private Map<GhostMode, Double> speeds;
    private int currentDirectionCount = 0;
    private String name;
    private MovementStrategy movementStrategy;
    private Image frightImage = new Image("maze/ghosts/frightened.png"); // Frightened mode image
    private Image currentImage; // Tracks the current image
    private static final int[] GHOST_SCORES = {200, 400, 800, 1600};
    private int ghostsEatenDuringFrightenedMode = 0;

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
        this.currentImage = image; // Initialize current image to the default image
    }

    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        this.speeds = speeds;
    }

    @Override
    public Image getImage() {
        return currentImage; // Return the current image
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

    /*
     * Passes in the ghost mode so each movementStrategy can be used
     */
    private Vector2D getTargetLocation() {
        // System.out.println("Player Position updated to: " + playerPosition); // PLAYER POSITION TEST
        return switch (ghostMode) {
            case CHASE -> movementStrategy.chase(this, playerPosition, playerDirection, targetCorner, blinkyPosition);
            case SCATTER -> targetCorner;
            case FRIGHTENED -> {
                setImage(frightImage); // Update the image to frightened mode
                yield getRandomTargetLocation();
            }
            default -> throw new IllegalStateException("Unexpected ghost mode: " + ghostMode); // Throws error in case of invalid ghost mode
        };
    }

    // Fright mode logic
    private Vector2D getRandomTargetLocation() {
        List<Direction> directions = new ArrayList<>(possibleDirections);
        if (directions.isEmpty()) {
            return kinematicState.getPosition(); // Stay in place if no valid directions
        }

        // Select a random direction from the possible directions
        Direction randomDirection = directions.get(new Random().nextInt(directions.size()));

        // Return the position the ghost would move to in that direction
        return kinematicState.getPotentialPosition(randomDirection);
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

        if (ghostMode != GhostMode.FRIGHTENED) {
            setImage(image); // Reset to default image if not in frightened mode
        }
        if (ghostMode == GhostMode.FRIGHTENED) {
            setImage(frightImage);
        }

        // Ensure direction is switched
        currentDirectionCount = minimumDirectionCount;
    }

    @Override
    public GhostMode getGhostMode() {
        return ghostMode;
    }

    @Override
    public boolean collidesWith(Renderable renderable) {
        return boundingBox.collidesWith(kinematicState.getSpeed(), kinematicState.getDirection(), renderable.getBoundingBox());
    }

    @Override
    public void collideWith(Level level, Renderable renderable) {
        if (ghostMode != GhostMode.FRIGHTENED) {
            if (level.isPlayer(renderable)) {
                System.out.println("pacman hit a ghost");
                level.handleLoseLife();
            }
        } else {
            if (level.isPlayer(renderable)) {
                System.out.println("Ghost is FRIGHTENED");
                level.notifyObserversWithScoreChange(GHOST_SCORES[ghostsEatenDuringFrightenedMode]);
                level.resetFrightenedGhost(this);
            }
        }
    }

    @Override
    public void update(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public void update(Direction playerDirection) {
        this.playerDirection = playerDirection;
    }

    @Override
    public void updateBlinky(Vector2D blinkyPosition) {
        this.blinkyPosition = blinkyPosition;
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
        setImage(image); // Reset the image to the default image when reset
        System.out.println("GHOST RESET RAN");
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

    private void setImage(Image image) {
        this.currentImage = image; // Update the current image
    }

}
