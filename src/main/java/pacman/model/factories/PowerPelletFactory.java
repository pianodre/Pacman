package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.staticentity.collectable.PowerPellet;

/**
 * Concrete renderable factory for PowerPellet objects
 */
public class PowerPelletFactory implements RenderableFactory {
    private static final Image POWER_PELLET_IMAGE = new Image("maze/powerPellet.png");
    private static final int NUM_POINTS = 100;
    private final Renderable.Layer layer = Renderable.Layer.BACKGROUND;

    @Override
    public Renderable createRenderable(
            Vector2D position
    ) {
        try {

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    POWER_PELLET_IMAGE.getHeight(),
                    POWER_PELLET_IMAGE.getWidth()
            );

            return new PowerPellet(
                    boundingBox,
                    layer,
                    POWER_PELLET_IMAGE,
                    NUM_POINTS
            );

        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid pellet configuration | %s", e));
        }
    }
}
