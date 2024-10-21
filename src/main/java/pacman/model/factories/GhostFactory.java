package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
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
            // Adjust the position slightly for proper placement
            position = position.add(new Vector2D(4, -4));

            // Get the next ghost image in sequence
            Image ghostImage = GHOST_IMAGES.get(ghostIndex);

            // Increment the index, wrap around if necessary
            ghostIndex = (ghostIndex + 1) % GHOST_IMAGES.size();

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    ghostImage.getHeight(),
                    ghostImage.getWidth()
            );

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .build();

            // Return a new GhostImpl with the selected ghost image
            return new GhostImpl(
                    ghostImage,
                    boundingBox,
                    kinematicState,
                    GhostMode.SCATTER,
                    targetCorners.get(ghostIndex % targetCorners.size())
            );
        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid ghost configuration | %s ", e)
            );
        }
    }
}
