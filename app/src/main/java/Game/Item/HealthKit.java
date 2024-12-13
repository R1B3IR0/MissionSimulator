package Game.Item;

import Game.Player.Agent;
import Game.Room;

/**
 * Represents a health kit item that can be applied to an agent.
 * The health kit restores a specified number of health points to the agent.
 */
public class HealthKit extends Item {

    /** The number of health points the health kit can recover */
    private int pointsRecovered;

    /**
     * Constructs a new HealthKit with a specified room and points to be recovered.
     *
     * @param room The room in which the health kit is located
     * @param pointsRecovered The number of health points the health kit can restore
     */
    public HealthKit(Room room, int pointsRecovered) {
        super(room, "kit de vida");
        this.pointsRecovered = pointsRecovered;
    }

    /**
     * Gets the number of health points that the health kit can recover.
     *
     * @return The number of health points the health kit can recover
     */
    public int getPointsRecovered() {
        return pointsRecovered;
    }

    /**
     * Sets the number of health points that the health kit can recover.
     *
     * @param pointsRecovered The new number of health points to be recovered
     */
    public void setPointsRecovered(int pointsRecovered) {
        this.pointsRecovered = pointsRecovered;
    }

    /**
     * Applies the health kit to the specified agent, restoring health points.
     * If the agent's health is already full, the health kit cannot be used.
     * If the health exceeds the maximum (100), only the remaining health is restored.
     *
     * @param agent The agent to which the health kit is applied
     */
    @Override
    public void applyKit(Agent agent) {
        int health = agent.getHealth();   // Current health of the agent

        if (health >= 100) {
            System.out.println("Health is full. Cannot use HealthKit.");
        } else {
            // Points to recover
            int pointsToAdd = pointsRecovered;

            if (health + pointsToAdd > 100) {
                pointsToAdd = 100 - health;  // Ensure health doesn't exceed 100
            }

            health += pointsToAdd;
            agent.setHealth(health);

            System.out.println("Kit de vida com " + pointsRecovered + " pontos, dos quais " + pointsToAdd +
                    " foram recuperados.");
        }
    }
}
