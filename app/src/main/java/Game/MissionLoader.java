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


        JsonNode itensNode = rootNode.get("itens");
        ArrayUnorderedList<Item> itens = new ArrayUnorderedList<>(); // Lista desordenada para itens

        for (JsonNode itemNode : itensNode) {
            String divisao = itemNode.get("divisao").asText();
            Room room = new Room(divisao, false);

            String tipo = itemNode.get("tipo").asText();
            if (tipo.equals("kit de vida")) {
                int pontosRecuperados = itemNode.get("pontos-recuperados").asInt();
                itens.addToRear(new HealthKit(room, pontosRecuperados));
            } else if (tipo.equals("colete")) {
                int pontosExtra = itemNode.get("pontos-extra").asInt();
                itens.addToRear(new BulletProofVest(room, pontosExtra));
            }
        }

        // Carrega os inimigos
        JsonNode inimigosNode = rootNode.get("inimigos");
        ArrayUnorderedList<Enemy> inimigos = new ArrayUnorderedList<>();
        for (JsonNode inimigoNode : inimigosNode) {
            String nome = inimigoNode.get("nome").asText();
            int poder = inimigoNode.get("poder").asInt();
            String divisao = inimigoNode.get("divisao").asText();
            Room room = new Room(divisao, false);
            inimigos.addToRear(new Enemy(nome, poder, room));
        }





        return mission;
    }
}
