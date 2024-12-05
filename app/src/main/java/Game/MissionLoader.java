package Game;

import Game.Item.BulletProofVest;
import Game.Item.HealthKit;
import Game.Item.Item;
import Game.Player.Enemy;
import Structures.collections.lists.ArrayUnorderedList;
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


        JsonNode alvoNode = rootNode.get("alvo");
        Room targetRoom = new Room(alvoNode.get("divisao").asText(), false);
        Target target = new Target(alvoNode.get("tipo").asText(), targetRoom);
        mission.setAlvo(target);


        // Inicializar as coleções de itens e inimigos
        ArrayUnorderedList<Item> itens = new ArrayUnorderedList<>();
        ArrayUnorderedList<Enemy> inimigos = new ArrayUnorderedList<>();

        // Criar os quartos para itens
        JsonNode itensNode = rootNode.get("itens");
        for (JsonNode itemNode : itensNode) {
            String divisao = itemNode.get("divisao").asText();
            Room room = new Room(divisao, false);

            // Determinar o tipo de item
            String tipo = itemNode.get("tipo").asText();
            if (tipo.equals("kit de vida")) {
                int pontosRecuperados = itemNode.get("pontos-recuperados").asInt();
                HealthKit healthKit = new HealthKit(room, pontosRecuperados);
                itens.addToRear(healthKit);
                room.addItem(healthKit);
            } else if (tipo.equals("colete")) {
                int pontosExtra = itemNode.get("pontos-extra").asInt();
                BulletProofVest vest = new BulletProofVest(room, pontosExtra);
                itens.addToRear(vest);
                room.addItem(vest);
            }
        }


        JsonNode inimigosNode = rootNode.get("inimigos");
        for (JsonNode inimigoNode : inimigosNode) {
            String nome = inimigoNode.get("nome").asText();
            int poder = inimigoNode.get("poder").asInt();
            String divisao = inimigoNode.get("divisao").asText();
            Room room = new Room(divisao, false);

            Enemy enemy = new Enemy(nome, poder, room);
            inimigos.addToRear(enemy);
            room.addEnemy(enemy);
        }


        for (Item item : itens) {
            mission.getRooms().addToRear(item.getRoom());
        }
        for (Enemy enemy : inimigos) {
            mission.getRooms().addToRear(enemy.getDivisao());
        }





        return mission;
    }
}
