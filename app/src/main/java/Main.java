
import Game.Building;
import Game.Mission;
import Game.Room;
import Game.io.JsonHandler;
import Structures.collections.lists.UnorderedLinkedList;
import Structures.collections.lists.UnorderedListADT;


public class Main {
    public static void main(String[] args) {
        String filePath = "app//src//main//resources//mission.json";
        //Mission mission = JsonHandler.importJson(filePath);
        //System.out.println(mission);

        // Cria um edifício e adiciona as salas
        Building building = JsonHandler.importJson(filePath);

        // Gera o mapa
        building.generateMap();
        building.visualizeGraph();


        if (building.getMap() != null && !building.getMap().toString().isEmpty()) {
            System.out.println("Mapa gerado com sucesso!");
        } else {
            System.out.println("Falha ao gerar o mapa.");
        }
    }
}
