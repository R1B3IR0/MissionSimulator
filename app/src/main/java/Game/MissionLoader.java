package Game;


import Game.Item.BulletProofVest;
import Game.Item.HealthKit;
import Game.Player.Enemy;
import Structures.collections.lists.UnorderedLinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


public class MissionLoader {
    public static UnorderedLinkedList<Room> RoomList = new UnorderedLinkedList<>();


    public static void readFromJson(String filePath) {
        JSONParser parser = new JSONParser();


        try {
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            String codMissao = (String) jsonObject.get("cod-missao");
            long versao = (long) jsonObject.get("versao");


            System.out.println("Código da Missão: " + codMissao);
            System.out.println("Versão: " + versao);

            //Adicionar quarto à lista
            JSONArray quarto = (JSONArray) jsonObject.get("edificio");
            for (Object objroom : quarto) {
                String nomeQuarto = (String) objroom;
                System.out.println("Nome do quarto: " + nomeQuarto);

                Room room = new Room(nomeQuarto);
                RoomList.addToRear(room);
            }


            // Adicionar inimigos à room
            JSONArray inimigos = (JSONArray) jsonObject.get("inimigos");
            System.out.println("Inimigos:");
            for (Object inimigoObj : inimigos) {
                JSONObject inimigo = (JSONObject) inimigoObj;
                String nome = (String) inimigo.get("nome");
                int poder = ((Long) inimigo.get("poder")).intValue();
                String divisao = (String) inimigo.get("divisao");

                Room room = findRoomByName(divisao, RoomList);
                if (room != null) {
                    Enemy enemy = new Enemy(nome, poder, room);
                    room.addEnemy(enemy);
                } else {
                    System.out.println("Sala não encontrada para o inimigo: " + nome);
                }

            }


            JSONArray itens = (JSONArray) jsonObject.get("itens");
            System.out.println("Itens:");
            for (Object itemObj : itens) {
                JSONObject item = (JSONObject) itemObj;

                String tipo = (String) item.get("tipo");
                String divisao = (String) item.get("divisao");
                Room room = findRoomByName(divisao, RoomList);


                if (tipo.equals("kit de vida")) {
                    int pointsRecovered = ((Long) item.get("pontos-recuperados")).intValue();
                    HealthKit healthKit = new HealthKit(room, pointsRecovered);
                    room.addItem(healthKit);
                } else {
                    int extraPoints = ((Long) item.get("pontos-extra")).intValue();
                    BulletProofVest bulletProofVest = new BulletProofVest(room, extraPoints);
                    room.addItem(bulletProofVest);
                }

                System.out.println(RoomList);
            }


            JSONObject alvo = (JSONObject) jsonObject.get("alvo");
            System.out.println("Alvo: " + alvo.get("divisao") + " (" + alvo.get("tipo") + ")");


            JSONArray ligacoes = (JSONArray) jsonObject.get("ligacoes");
            System.out.println("Ligações:");
            for (Object ligacao : ligacoes) {
                JSONArray link = (JSONArray) ligacao;
                System.out.println(" - " + link.get(0) + " -> " + link.get(1));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static Room findRoomByName(String name, UnorderedLinkedList<Room> roomList) {
        for (Room room : roomList) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

}