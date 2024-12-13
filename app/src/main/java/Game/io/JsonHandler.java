package Game.io;

import java.io.FileReader;
import java.io.IOException;

import Game.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Game.Item.BulletProofVest;
import Game.Item.HealthKit;
import Game.Player.Enemy;

public class JsonHandler {

    public static Mission importJson(String filePath) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Parse edifício
            Building building = new Building();
            JSONArray edificioArray = (JSONArray) jsonObject.get("edificio");
            for (Object location : edificioArray) {
                Room room = new Room((String) location);
                if (!building.getRooms().contains(room)) {
                    building.addRoom(room);
                }
            }

            // Parse ligações
            JSONArray conections = (JSONArray) jsonObject.get("ligacoes");
            for (Object conectionObj : conections) {
                JSONArray conection = (JSONArray) conectionObj;
                Room from = findRoom(building, (String) conection.get(0));
                Room to = findRoom(building, (String) conection.get(1));
                // Adiciona a ligação ao grafo
                building.getMap().addEdge(from, to);
                building.getConnections().addToRear(new Connection(from, to));
            }

            // Parse inimigos
            JSONArray enemyArray = (JSONArray) jsonObject.get("inimigos");
            for (Object enemyObj : enemyArray) {
                JSONObject enemyJson = (JSONObject) enemyObj;
                String name = (String) enemyJson.get("nome");
                int power = ((Long) enemyJson.get("poder")).intValue();
                String division = (String) enemyJson.get("divisao");
                // Verifica se a divisão existe no edifício
                Room room = findRoom(building, division);
                room.addEnemy(new Enemy(name, power, room));
            }

            // Parse itens
            JSONArray itemsArray = (JSONArray) jsonObject.get("itens");
            for (Object itemObj : itemsArray) {
                JSONObject itemJson = (JSONObject) itemObj;
                String division = (String) itemJson.get("divisao");
                //String tipo = (String) itemJson.get("tipo");
                Room room = findRoom(building, division);
                if (itemJson.containsKey("pontos-recuperados")) {
                    int recoveredPoints = ((Long) itemJson.get("pontos-recuperados")).intValue();
                    room.addItem(new HealthKit(room, recoveredPoints));
                } else if (itemJson.containsKey("pontos-extra")) {
                    int extraPoints = ((Long) itemJson.get("pontos-extra")).intValue();
                    room.addItem(new BulletProofVest(room, extraPoints));
                }
            }

            // Parse alvo
            JSONObject alvoJson = (JSONObject) jsonObject.get("alvo");
            String alvoDivisao = (String) alvoJson.get("divisao");
            String alvoTipo = (String) alvoJson.get("tipo");
            Room alvoRoom = findRoom(building, alvoDivisao);
            Target alvo = new Target(alvoTipo, alvoRoom);

            // Parse versão
            int version = ((Long) jsonObject.get("versao")).intValue();


            // Parse missão
            String codMissao = (String) jsonObject.get("cod-missao");
            Mission mission = new Mission(codMissao, version, alvo, building);

            JSONArray entradasSaidas = (JSONArray) jsonObject.get("entradas-saidas");
            for (Room room : building.getRooms()) {
                if (entradasSaidas.contains(room.getName())) {
                    room.setClassification("entrada-saida");
                }
            }

            return mission;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Search for a room by its name
     * @param building
     * @param roomName
     * @return the room with the given name or null if it doesn't exist
     */
    private static Room findRoom(Building building, String roomName) {
        for (Room room : building.getRooms()) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }
}
