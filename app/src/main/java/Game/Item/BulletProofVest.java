package Game.Item;

import Game.Player.Agent;
import Game.Room;

/**
 * Represents a bulletproof vest item that can be applied to an agent.
 * The vest grants extra health points to the agent when applied.
 */
public class BulletProofVest extends Item {
    /** The number of extra health points granted by the bulletproof vest */
    private int extraPoints;

    /**
     * Constructs a new BulletProofVest with a specified room and extra health points.
     *
     * @param room The room in which the bulletproof vest is located
     * @param extraPoints The number of extra health points provided by the vest
     */
    public BulletProofVest(Room room, int extraPoints) {
        super(room, "colete");
        this.extraPoints = extraPoints;
    }

    /**
     * Gets the extra health points granted by the bulletproof vest.
     *
     * @return The extra health points granted by the vest
     */
    public int getExtraPoints() {
        return extraPoints;
    }

    /**
     * Sets the extra health points granted by the bulletproof vest.
     *
     * @param extraPoints The new number of extra health points provided by the vest
     */
    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    /**
     * Applies the bulletproof vest to the specified agent, increasing their health by the number of extra points.
     *
     * @param agent The agent to which the bulletproof vest is applied
     */
    public void applyKit(Agent agent) {
        int health = agent.getHealth();
        health += extraPoints;
        agent.setHealth(health);

        // System.out.println("Colete à prova de bala com " + extraPoints + " pontos adicionado.");
    }
}
