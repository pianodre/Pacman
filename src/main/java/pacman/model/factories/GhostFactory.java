package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.MovementStrategy.BlinkyMovementStrategy;
import pacman.model.entity.dynamic.MovementStrategy.ClydeMovementStrategy;
import pacman.model.entity.dynamic.MovementStrategy.InkyMovementStrategy;
import pacman.model.entity.dynamic.MovementStrategy.MovementStrategy;
import pacman.model.entity.dynamic.MovementStrategy.PinkyMovementStrategy;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;

import java.util.Arrays;
import java.util.List;

/**
 * Concrete renderable factory for Ghost objects
 */
public class GhostFactory implements RenderableFactory {

    private static final int RIGHT_X_POSITION_OF_MAP = 448;
    private static final int TOP_Y_POSITION_OF_MAP = 16 * 3;
    private static final int BOTTOM_Y_POSITION_OF_MAP = 16 * 34;

    // Ghost images for different types of ghosts
    private static final Image BLINKY_IMAGE = new Image("maze/ghosts/blinky.png");
    private static final Image INKY_IMAGE = new Image("maze/ghosts/inky.png");
    private static final Image CLYDE_IMAGE = new Image("maze/ghosts/clyde.png");
    private static final Image PINKY_IMAGE = new Image("maze/ghosts/pinky.png");

    // List to hold all ghost images in order
    private static final List<Image> GHOST_IMAGES = Arrays.asList(
            BLINKY_IMAGE,
            INKY_IMAGE,
            CLYDE_IMAGE,
            PINKY_IMAGE
    );

    private static final List<String> GHOST_NAMES = Arrays.asList(
        "Blinky",
        "Inky",
        "Clyde",
        "Pinky"
    );


    List<Vector2D> targetCorners = Arrays.asList(
            new Vector2D(0, TOP_Y_POSITION_OF_MAP),
            new Vector2D(RIGHT_X_POSITION_OF_MAP, TOP_Y_POSITION_OF_MAP),
            new Vector2D(0, BOTTOM_Y_POSITION_OF_MAP),
            new Vector2D(RIGHT_X_POSITION_OF_MAP, BOTTOM_Y_POSITION_OF_MAP)
    );

    // Index to track the next ghost to be created
    private int ghostIndex = 0;

    @Override
    public Renderable createRenderable(Vector2D position) {
        try {
                position = position.add(new Vector2D(4, -4));

                Image ghostImage = GHOST_IMAGES.get(ghostIndex);
                String ghostName = GHOST_NAMES.get(ghostIndex);

                ghostIndex = (ghostIndex + 1) % GHOST_IMAGES.size();

                BoundingBox boundingBox = new BoundingBoxImpl(
                        position,
                        ghostImage.getHeight(),
                        ghostImage.getWidth()
                );

                KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                        .setPosition(position)
                        .build();

                MovementStrategy movementStrategy = switch (ghostName) {
                case "Blinky" -> new BlinkyMovementStrategy();
                case "Pinky" -> new PinkyMovementStrategy();
                case "Inky" -> new InkyMovementStrategy();
                case "Clyde" -> new ClydeMovementStrategy();
                default -> throw new IllegalArgumentException("Unknown ghost name: " + ghostName);
                };

                GhostImpl ghost = new GhostImpl(
                        ghostImage,
                        boundingBox,
                        kinematicState,
                        GhostMode.SCATTER,
                        targetCorners.get(ghostIndex % targetCorners.size()),
                        movementStrategy // Pass the specific movement strategy
                );

                ghost.setName(ghostName);
                System.out.println("Created ghost: " + ghost.getName());

                return ghost;
        } catch (Exception e) {
                throw new ConfigurationParseException(
                        String.format("Invalid ghost configuration | %s ", e)
                );
        }
    }

}
