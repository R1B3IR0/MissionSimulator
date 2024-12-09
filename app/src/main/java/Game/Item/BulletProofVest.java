package Game.Item;


import Game.Player.Agent;
import Game.Room;

public class BulletProofVest extends Item {
    private int extraPoints;

    public BulletProofVest(Room room, int extraPoints) {
        super(room, "colete");
        this.extraPoints = extraPoints;
    }

    public int getExtraPoints() {
        return extraPoints;
    }

    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    @Override
    public void applyKit() {
        Agent agent = new Agent();

        int health = agent.getHealth();

        health += extraPoints;

        agent.setHealth(health);

        System.out.println("Colete à prova de bala com " + extraPoints + " pontos adicionado.");
    }


}

