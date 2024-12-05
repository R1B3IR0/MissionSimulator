package Game;

import Game.Item.BulletProofVest;
import Game.Item.HealthKit;
import Game.Player.Enemy;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class MissionLoader {

    public static Mission loadMission(InputStream inputStream) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(inputStream);

        Mission mission = new Mission();
        mission.setCodMissao(rootNode.get("cod-missao").asText());
        mission.setVersao(rootNode.get("versao").asInt());

        Building building = new Building();
        loadBuilding(rootNode, building);


        JsonNode alvoNode = rootNode.get("alvo");
        Room targetRoom = building.getRoomByName(alvoNode.get("divisao").asText());
        if (targetRoom == null) throw new IllegalArgumentException("Sala do alvo não encontrada");

        mission.setAlvo(new Target(alvoNode.get("tipo").asText(), targetRoom));


        loadItemsAndEnemies(rootNode, building);

        return mission;
    }

    private static void loadBuilding(JsonNode rootNode, Building building) {

        rootNode.get("edificio").forEach(roomNode -> building.addRoom(new Room(roomNode.asText(), false)));


        rootNode.get("ligacoes").forEach(connectionNode -> {
            Room room1 = building.getRoomByName(connectionNode.get(0).asText());
            Room room2 = building.getRoomByName(connectionNode.get(1).asText());
            if (room1 != null && room2 != null) {
                building.connectRooms(room1, room2);
            } else {
                System.err.println("Erro ao conectar salas: " + connectionNode.get(0) + " e " + connectionNode.get(1));
            }
        });
    }

    private static void loadItemsAndEnemies(JsonNode rootNode, Building building) {

        rootNode.get("itens").forEach(itemNode -> {
            Room room = building.getRoomByName(itemNode.get("divisao").asText());
            if (room == null) return; // Ignore item se sala não for encontrada
            String tipo = itemNode.get("tipo").asText();
            if ("kit de vida".equals(tipo)) {
                room.addItem(new HealthKit(room, itemNode.get("pontos-recuperados").asInt()));
            } else if ("colete".equals(tipo)) {
                room.addItem(new BulletProofVest(room, itemNode.get("pontos-extra").asInt()));
            }
        });


        rootNode.get("inimigos").forEach(enemyNode -> {
            Room room = building.getRoomByName(enemyNode.get("divisao").asText());
            if (room == null) return;
            room.addEnemy(new Enemy(enemyNode.get("nome").asText(), enemyNode.get("poder").asInt(), room));
        });
    }
}
