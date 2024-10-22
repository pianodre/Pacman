package pacman.model.entity.staticentity.collectable;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.physics.BoundingBox;

/**
 * Represents the PowerPellet in Pac-Man game.
 * The collection of a PowerPellet grants temporary power to the player.
 */
public class PowerPellet extends Pellet {
    private final int duration; // Duration of the power effect

    public PowerPellet(BoundingBox boundingBox, Layer layer, Image image, int points, int duration) {
        super(boundingBox, layer, image, points);
        this.duration = duration;
    }

    /**
     * Returns the duration of the power effect granted by this PowerPellet.
     *
     * @return Duration in seconds.
     */
    public int getDuration() {
        return duration;
    }

    @Override
    public void collect() {
        System.out.println("Hit Power Pellet");
        super.collect();
        // Add additional behavior for collecting a PowerPellet
        grantPowerToPlayer();  // Implement the effect
    }

    /**
     * Grants power to the player upon collection of the PowerPellet.
     * This method should include logic to activate the player's power and manage the duration.
     */
    private void grantPowerToPlayer() {
        // Implement the logic to grant power to the player
        // Example:
        // Player player = Game.getPlayer();  // Retrieve the current player instance
        // player.setInvincible(true);
        // Schedule a task to revert this after 'duration' seconds
        // Timer.schedule(new TimerTask() {
        //     @Override
        //     public void run() {
        //         player.setInvincible(false);
        //     }
        // }, duration * 1000);  // Convert seconds to milliseconds
    }
}
