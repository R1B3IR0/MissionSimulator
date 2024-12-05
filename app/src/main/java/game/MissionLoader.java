package game;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;


public class MissionLoader {

    /**
     * Carrega uma missão do arquivo JSON e mapeia para o objeto Mission.
     */
    public static Mission loadMission(InputStream inputStream) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Carregar o JSON como um nó genérico
        JsonNode rootNode = objectMapper.readTree(inputStream);

        // Mapear o JSON para a classe Mission
        Mission mission = new Mission();
        mission.setCodMissao(rootNode.get("cod-missao").asText());
        mission.setVersao(rootNode.get("versao").asInt());

        // Mapeia o alvo
        JsonNode alvoNode = rootNode.get("alvo");
        Target target = new Target(alvoNode.get("tipo").asText());
        Room targetRoom = new Room(alvoNode.get("divisao").asText(), false);
        target.setRoom(targetRoom);
        mission.setAlvo(target);


        return mission;
    }

}

