package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.MovementStrategy.PinkyMovementStrategy;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;

public class PinkyFactory implements RenderableFactory {

    private static final Image PINKY_IMAGE = new Image("maze/ghosts/pinky.png");
    private static final int RIGHT_X_POSITION_OF_MAP = 448;
    private static final int TOP_Y_POSITION_OF_MAP = 16 * 3;
    private static final int BOTTOM_Y_POSITION_OF_MAP = 16 * 34;

    private static final Vector2D TARGET_CORNER = new Vector2D(0, TOP_Y_POSITION_OF_MAP);

    @Override
    public Renderable createRenderable(Vector2D position) {
        try {
            position = position.add(new Vector2D(4, -4));

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    PINKY_IMAGE.getHeight(),
                    PINKY_IMAGE.getWidth()
            );

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .build();

            GhostImpl pinky = new GhostImpl(
                    PINKY_IMAGE,
                    boundingBox,
                    kinematicState,
                    GhostMode.SCATTER,
                    TARGET_CORNER,
                    new PinkyMovementStrategy() // Use Pinky's movement strategy
            );

            pinky.setName("Pinky");
            System.out.println("Created ghost: " + pinky.getName());

            return pinky;
        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid ghost configuration | %s ", e)
            );
        }
    }
}
