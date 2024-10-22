package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.MovementStrategy.ClydeMovementStrategy;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;

public class ClydeFactory implements RenderableFactory {

    private static final Image CLYDE_IMAGE = new Image("maze/ghosts/clyde.png");
    private static final int RIGHT_X_POSITION_OF_MAP = 448;
    private static final int TOP_Y_POSITION_OF_MAP = 16 * 3;
    private static final int BOTTOM_Y_POSITION_OF_MAP = 16 * 34;

    private static final Vector2D TARGET_CORNER = new Vector2D(0, BOTTOM_Y_POSITION_OF_MAP);

    @Override
    public Renderable createRenderable(Vector2D position) {
        try {
            position = position.add(new Vector2D(4, -4));

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    CLYDE_IMAGE.getHeight(),
                    CLYDE_IMAGE.getWidth()
            );

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .build();

            GhostImpl clyde = new GhostImpl(
                    CLYDE_IMAGE,
                    boundingBox,
                    kinematicState,
                    GhostMode.SCATTER,
                    TARGET_CORNER,
                    new ClydeMovementStrategy() // Use Clyde's movement strategy
            );

            clyde.setName("Clyde");
            System.out.println("Created ghost: " + clyde.getName());

            return clyde;
        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid ghost configuration | %s ", e)
            );
        }
    }
}
