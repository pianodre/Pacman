package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.MovementStrategy.BlinkyMovementStrategy;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;

public class BlinkyFactory implements RenderableFactory {

    private static final Image BLINKY_IMAGE = new Image("maze/ghosts/blinky.png");
    private static final int RIGHT_X_POSITION_OF_MAP = 448;
    private static final int TOP_Y_POSITION_OF_MAP = 16 * 3;

    private static final Vector2D TARGET_CORNER = new Vector2D(RIGHT_X_POSITION_OF_MAP, TOP_Y_POSITION_OF_MAP);

    @Override
    public Renderable createRenderable(Vector2D position) {
        try {
            position = position.add(new Vector2D(4, -4));

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    BLINKY_IMAGE.getHeight(),
                    BLINKY_IMAGE.getWidth()
            );

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .build();

            GhostImpl blinky = new GhostImpl(
                    BLINKY_IMAGE,
                    boundingBox,
                    kinematicState,
                    GhostMode.SCATTER,
                    TARGET_CORNER,
                    new BlinkyMovementStrategy() // Use Blinky's movement strategy
            );

            blinky.setName("Blinky");
            System.out.println("Created ghost: " + blinky.getName());

            return blinky;
        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid ghost configuration | %s ", e)
            );
        }
    }
}
