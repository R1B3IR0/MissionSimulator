import Game.Building;
import Game.MissionLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        try {
            // Carregar o arquivo JSON
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("mission.json");
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo mission.json não encontrado.");
            }

            // Criar o objeto ObjectMapper para ler o JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);  // Ler o JSON do arquivo

            // Criar o edifício
            Building building = new Building();

            // Carregar as salas e conexões para o edifício
            MissionLoader.loadBuilding(rootNode, building);  // Passa rootNode (JSON) e building

            // Exibir o edifício carregado
            System.out.println("Edifício carregado:");
            System.out.println(building.toString());  // Chama o toString() do Building

        } catch (Exception e) {
            System.err.println("Erro ao carregar a missão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
