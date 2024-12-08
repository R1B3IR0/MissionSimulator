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


        return mission;
    }

    public static void loadBuilding(JsonNode rootNode, Building building) {

        JsonNode roomsNode = rootNode.get("edificio");
        for (JsonNode roomNode : roomsNode) {
            Room room = new Room(roomNode.asText());
            building.addRoom(room);
        }

        JsonNode connectionsNode = rootNode.get("ligacoes");
        for (JsonNode connectionNode : connectionsNode) {
            String room1Name = connectionNode.get(0).asText();
            String room2Name = connectionNode.get(1).asText();

            Room room1 = building.getRoomByName(room1Name);
            Room room2 = building.getRoomByName(room2Name);

            if (room1 != null && room2 != null) {
                building.connectRooms(room1, room2);
            } else {
                System.err.println("Erro ao conectar salas: " + room1Name + " e " + room2Name);
            }
        }
    }

    private static void loadItems(JsonNode rootNode, Building building) {
        JsonNode itensNode = rootNode.get("itens");
        for (JsonNode itemNode : itensNode) {
            String roomName = itemNode.get("divisao").asText();
            Room room = building.getRoomByName(roomName);

            if (room == null) {
                System.err.println("Erro: Sala não encontrada para o item: " + roomName);
                continue;
            }


            String tipo = itemNode.get("tipo").asText();
            if (tipo.equals("kit de vida")) {
                int pontosRecuperados = itemNode.get("pontos-recuperados").asInt();
                HealthKit healthKit = new HealthKit(room, pontosRecuperados);
                room.addItem(healthKit);
            } else if (tipo.equals("colete")) {
                int pontosExtra = itemNode.get("pontos-extra").asInt();
                BulletProofVest vest = new BulletProofVest(room, pontosExtra);
                room.addItem(vest);
            }
        }
    }

    private static void loadEnemies(JsonNode rootNode, Building building) {
        JsonNode inimigosNode = rootNode.get("inimigos");
        for (JsonNode enemyNode : inimigosNode) {
            String roomName = enemyNode.get("divisao").asText();
            Room room = building.getRoomByName(roomName);

            if (room == null) {
                System.err.println("Erro: Sala não encontrada para o inimigo: " + roomName);
                continue;
            }

            String nome = enemyNode.get("nome").asText();
            int poder = enemyNode.get("poder").asInt();

            Enemy enemy = new Enemy(nome, poder, room);
            room.addEnemy(enemy);
        }
    }
}