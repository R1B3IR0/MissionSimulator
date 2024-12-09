package Game.Item;

import Game.Player.Agent;
import Game.Room;

public class HealthKit extends Item{

    private int pointsRecovered;

    public HealthKit(Room room, int pointsRecovered) {
        super(room, "kit de vida");
        this.pointsRecovered = pointsRecovered;
    }

    public int getPointsRecovered() {
        return pointsRecovered;
    }

    public void setPointsRecovered(int pointsRecovered) {
        this.pointsRecovered = pointsRecovered;
    }

    @Override
    public void applyKit() {
        Agent agent = new Agent();

        int health = agent.getHealth();   // 90
        
        if(health >= 100) {
            System.out.println("Vida cheia.");
        } else {
            // Pontos a recuperar
            int pointsToAdd = pointsRecovered;  // 25

            if(health + pointsToAdd > 100) {
                pointsToAdd = 100 - health;
            }

            health += pointsToAdd;
            
            agent.setHealth(health);
            
            System.out.println("Kit de vida com " + pointsRecovered + " pontos dos quais " + pointsToAdd +
                    " foram adicionados.");
        }
    }

    @Override
    public String toString() {
        String text = "";

        text += super.toString() + "\n";
        text += "Pontos recuperados: " + pointsRecovered + "\n";

        return text;
    }
}

