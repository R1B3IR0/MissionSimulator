package Client;

import Game.*;
import Game.Menu.Menu;
import Game.Player.Agent;
import Game.io.JsonHandler;

public class DemoGame {
    public static void main(String[] args) {
        try {
            // Caminho do arquivo JSON (ajuste conforme necessário)
            String filePath = "app//src//main//resources//mission.json";
            Mission mission = JsonHandler.importJson(filePath);

            if (mission == null) {
                System.out.println("Erro ao carregar o edifício a partir do JSON.");
                return;
            }
            System.out.println("JSON carregado com sucesso.");

            Building building = mission.getBuilding();
            building.generateMap();
            building.storeEntryExitRooms(); // Guarda as salas de entrada e saída

            Agent agent = new Agent();

            // Escolhe uma sala de entrada para o Agente.
            agent.chooseEntryExitRoom(mission.getBuilding().getRoomsWithEntryExit());

            MissionSimulator simulator = new MissionSimulator(mission, agent);


            Menu.mainMenu(simulator);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
