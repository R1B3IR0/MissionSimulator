package Game;

import Game.Player.Agent;

public class MissionSimulator {
    private Mission mission;
    private Agent agent;

    public MissionSimulator(Mission mission) {
        this.mission = mission;
        this.agent = new Agent();
    }
}
