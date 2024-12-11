import Game.Building;
import Game.Mission;
import Game.MissionSimulator;
import Game.Player.Agent;
import Game.Room;
import Game.io.JsonHandler;
import Structures.collections.lists.ArrayUnorderedList;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Caminho do arquivo JSON (ajuste conforme necessário)
        String filePath = "app//src//main//resources//mission.json";

        // Carrega a missão a partir do JSON
        Building building = JsonHandler.importJson(filePath);
        Mission mission = new Mission(building);  // Criando a missão com o edifício carregado
        Agent agent = new Agent();
        MissionSimulator simulator = new MissionSimulator(mission, agent); // Simulador de missão

        // Gerar e visualizar o mapa do edifício
        building.generateMap();
        building.visualizeGraph();

        // Inicializa o agente (personagem principal)

        // Escolher a sala inicial
        ArrayUnorderedList<Room> entryExitRooms = building.getRoomsWithEntryExit();

        if (entryExitRooms.isEmpty()) {
            System.out.println("Nenhuma sala de entrada-saída encontrada!");
            return;  // Encerra o jogo caso não haja salas de entrada-saída
        }

        // Exibe as opções de salas para o agente escolher
        System.out.println("Escolha uma sala de entrada-saída para começar:");
        for (int i = 0; i < entryExitRooms.size(); i++) {
            System.out.println((i + 1) + ". " + entryExitRooms.get(i).getName());
        }

        // O agente escolhe uma sala
        Scanner scanner = new Scanner(System.in);
        int chosenRoomIndex = scanner.nextInt() - 1;  // Ajusta para índice zero
        if (chosenRoomIndex < 0 || chosenRoomIndex >= entryExitRooms.size()) {
            System.out.println("Escolha inválida. O jogo será encerrado.");
            return;
        }

        Room chosenRoom = entryExitRooms.get(chosenRoomIndex);
        agent.setCurrentRoom(chosenRoom);
        System.out.println("Agente começou na sala: " + chosenRoom.getName());

        boolean gameRunning = true;

        while (gameRunning) {
            System.out.println("\nTurno do Agente: " + agent.getName());
            System.out.println("Sala Atual: " + agent.getCurrentRoom().getName());
            System.out.println("1. Mover-se");
            System.out.println("2. Usar item de recuperação");
            System.out.println("3. Atacar inimigos");
            System.out.println("4. Interagir com o alvo");
            System.out.println("5. Sair do jogo");
            System.out.print("Escolha sua ação: ");
            int action = scanner.nextInt();


            switch (action) {
                case 1:
                    simulator.processAgentTurn(); //Isto esta mal
                    break;

                case 2:
                    simulator.processItemUse();
                    break;

                case 3:
                    Room currentRoom = agent.getCurrentRoom();
                    simulator.processCombat(currentRoom); //Verifica se consegue combater
                    break;
                case 4:
                    simulator.processTargetInteraction(); // Interage com o alvo
                    break;

                case 5:
                    System.out.println("Saindo do jogo...");
                    gameRunning = false;
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

            // Processar o turno dos inimigos após o turno do agente
            simulator.processEnemiesTurn();

            // Verificar se o jogo acabou
            if (agent.getHealth() <= 0) {
                System.out.println("O Agente foi derrotado. Fim de jogo.");
                gameRunning = false;
            }

            if (mission.getTarget().isRescued()) {
                System.out.println("Missão completada com sucesso! O alvo foi resgatado.");
                gameRunning = false;
            }
        }

        scanner.close();
    }
}
