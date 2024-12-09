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

        int points = agent.getHealth();   // 90
        
        if(points >= 100) {
            System.out.println("Vida cheia.");
        } else {
            // Pontos a recuperar
            int pointsToAdd = pointsRecovered;  // 25

            if(points + pointsToAdd > 100) {
                pointsToAdd = 100 - points;
            }

            points += pointsToAdd;
            
            agent.setHealth(points);
            
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

