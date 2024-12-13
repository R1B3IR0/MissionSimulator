package Game;

import Game.Item.BulletProofVest;
import Game.Item.HealthKit;
import Game.Item.Item;
import Game.Player.Agent;
import Game.Player.Enemy;
import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayUnorderedList;

import java.util.Iterator;
import java.util.Scanner;

public class MissionSimulator {
    private Mission mission;
    private Agent agent;
    private Target target;
    private Room initialRoom;
    private boolean enemiesMovedThisTurn;
    private boolean isPlayerTurn; // Flag para controlar o turno do jogador

    public MissionSimulator(Mission mission, Agent agent) {
        this.mission = mission;
        this.agent = agent;
        this.initialRoom = agent.getCurrentRoom();
        this.target = mission.getTarget();
        this.enemiesMovedThisTurn = false;
        this.isPlayerTurn = true;

    }

    /**
     * Lógica dos turnos do jogo
     */
    public void startGameLoop(boolean automatic) {
        boolean gameOver = false;

        while (!gameOver) {
            if (isPlayerTurn) {  // Se for o turno do jogador é True
                processAgentTurn(automatic);
            } else { // False é o turno dos inimigos
                processEnemiesTurn();
            }
            endTurn();

            if (agent.getHealth() <= 0) {
                System.out.println("Agent has been defeated! Game Over.");
                gameOver = true;
            } else if (mission.getTarget().isRescued() && agent.getCurrentRoom().isEntryExit()) {
                System.out.println("Congratulations! You have successfully completed the mission!");
                gameOver = true;
            }
            // Encerra o programa na consola
            //System.exit(0);
        }
    }

    /**
     * O agente move-se para uma nova sala.
     *
     * @param newRoom
     */
    public void moveAgentToRoom(Room newRoom) {
        if (mission.getBuilding().getRooms().contains(newRoom)) {
            agent.setCurrentRoom(newRoom);
            System.out.println("Agent moved to room: " + newRoom.getName());

            // Verifica se a sala tem itens
            verifyRoomItems(newRoom);
        } else {
            System.out.println("Invalid room. The agent cannot move to this room.");
        }
    }

    /**
     * O agente decide ficar na sala atual.
     */
    public void stayInCurrentRoom() {
        System.out.println("Agent decided to stay in the current room: " + agent.getCurrentRoom().getName());
    }

    public void processAgentTurn(boolean automatic) {
        Room currentRoom = agent.getCurrentRoom();

        if (automatic) {
            if (!currentRoom.getEnemies().isEmpty()) {
                System.out.println("You cannot move while enemies are present in the room.");
            } else {
                // Encontrar o caminho mais curto até o alvo
                Room targetRoom = mission.getTarget().getRoom();
                if (mission.getTarget().isRescued()) {
                    {
                        targetRoom = this.initialRoom;
                        ArrayUnorderedList<Room> targetRoomsExit = this.mission.getBuilding().getRoomsWithEntryExit();

                        //Caminho que foi escolhido
                        ArrayUnorderedList<PathWithWeight<Room>> bestPathToTarget = null;
                        double bestPathToTargetWeight = Double.MAX_VALUE;
                        int caminhoAlternativas = 1;
                        int caminhoEscolhido = 0;
                        for (Room room : targetRoomsExit) {
                            ArrayUnorderedList<PathWithWeight<Room>> pathToTarget = findBestPathToTarget(currentRoom, room);
                            double totalWeight = 0;
                            for (PathWithWeight pathWithWeight : pathToTarget) {
                                totalWeight += pathWithWeight.getWeight();
                            }
                            System.out.println("CAMINHO " + caminhoAlternativas + " | Total peso: " + totalWeight);
                            //Quando sabemos o total de peso do caminho, comparamos com a melhor opçao
                            if (totalWeight < bestPathToTargetWeight) {
                                bestPathToTarget = pathToTarget;
                                bestPathToTargetWeight = totalWeight;
                                targetRoom = room;
                                caminhoEscolhido = caminhoAlternativas;
                            }

                            caminhoAlternativas++;
                        }
                        System.out.println("ESCOLHI O CAMINHO " + caminhoEscolhido);
                    }
                }
                ArrayUnorderedList<PathWithWeight<Room>> pathToTarget = findBestPathToTarget(currentRoom, targetRoom);
                if (pathToTarget != null) {
                    Iterator<PathWithWeight<Room>> iteratorPathToTarget = pathToTarget.iterator();
                    if (iteratorPathToTarget.hasNext()) {
                        moveAgentToRoom(iteratorPathToTarget.next().getRoom());
                    }
                }
            }

            //Dijktra
            //TODO - Implementar

        } else {
            //System.out.println("Choose an action: (1) Move, (2) Stay, (3) Use Health Kit");
            int action = getPlayerAction(); // Implement a method to capture player action input.

            switch (action) {
                case 1: // Move
                    if (!currentRoom.getEnemies().isEmpty()) {
                        System.out.println("You cannot move while enemies are present in the room.");
                    } else {
                        Room newRoom = chooseRoomToMove(); // Implement method to let player choose a room.
                        moveAgentToRoom(newRoom);
                    }
                    break;
                case 2: // Stay
                    stayInCurrentRoom();
                    break;
                case 3: // Use Health Kit
                    useHealthKit();
                    System.out.println("You used your turn to recover health.");
                    return; // Skip enemy turn as the player used their phase.
                default:
                    System.out.println("Invalid action. Turn skipped.");
            }
        }

        if (!currentRoom.getEnemies().isEmpty()) {
            processCombat(currentRoom);
        } else if (currentRoom.equals(target.getRoom())) {
            if (currentRoom.getEnemies().isEmpty()) {
                processTargetInteraction();
            } else {
                System.out.println("Target is in the room, but enemies must be dealt with first.");
            }
        } else {
            System.out.println("No enemies in the room. Enemies will now move.");
            moveRandomlyEnemies();
            isPlayerTurn = false; // Switch to enemy turn
        }
    }

    public void processCombat(Room room) {
        System.out.println("Combat initiated in division: " + room.getName());
        Iterator<Enemy> iterator = room.getEnemies().iterator(); // Obtém um iterador para os inimigos na sala

        // Cenário 1: Fase do jogador (Tó Cruz ataca os inimigos que estão na sala)
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.takeDamage(agent.getPower()); // Aplica dano a todos os inimigos na sala
            System.out.println("Agent attacked " + enemy.getName() + ". Enemy current health: " + enemy.getHeatlh());

            if (enemy.getHeatlh() <= 0) {
                System.out.println(enemy.getName() + " was defeated!");
                iterator.remove(); // Remove com segurança

            }
        }

        // Verificação: Se todos os inimigos foram derrotados, encerra o combate
        if (room.getEnemies().isEmpty()) {
            System.out.println("All enemies in the room have been defeated. Combat ends.");

        } else {
            System.out.println("Enemies remains in the room. Prepare for their retaliation!");
            isPlayerTurn = false; // Muda para o turno dos inimigos
        }
    }

    public void handleEnemyAttack() {
        System.out.println("Enemies are attacking!");
        for (Enemy enemy : agent.getCurrentRoom().getEnemies()) {
            agent.takeDamage(enemy.getPower());
            System.out.println("Agent took damage from " + enemy.getName() + ". Agent current health: " + agent.getHealth());

            if (agent.getHealth() <= 0) {
                System.out.println("Agent has been defeated! Game Over.");
                break;
            }
        }
    }

    public void moveRandomlyEnemies() {
        Room agentRoom = agent.getCurrentRoom();
        for (Room room : mission.getBuilding().getRooms()) {
            if (room.equals(agentRoom)) {
                continue; // Ignora a sala onde o agente está
            }
            Iterator<Enemy> enemyIterator = room.getEnemies().iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                enemy.moveRandomly(mission.getBuilding().getMap(), mission.getBuilding());
                System.out.println("Enemies moved to new room");
            }
        }
    }

    public void processEnemiesTurn() {
        Room currentRoom = agent.getCurrentRoom();

        if (currentRoom.getEnemies().isEmpty()) {
            System.out.println("No enemies in the room. Switching to player turn.");
            isPlayerTurn = true; // Switch to player turn
        } else {
            handleEnemyAttack();
            isPlayerTurn = true;
        }

        moveRandomlyEnemies();
    }

    public void processTargetInteraction() {
        // Cenário 5: Alvo com inimigos na sala
        if (!agent.getCurrentRoom().getEnemies().isEmpty()) {
            System.out.println("Enemies must be defeated before interacting with the target!");
        } else {
            // Cenário 6: Alvo sem inimigos na sala
            System.out.println("Interacting with target.");
            mission.getTarget().rescue();
            System.out.println("Target has been rescued! Exit the building to complete the mission.");
        }
    }

    public void useHealthKit() {
        if (!agent.getInventory().isEmpty()) {
            HealthKit kit = agent.getInventory().pop();
            kit.applyKit(agent);
            System.out.println("HealthKit used. Current health: " + agent.getHealth());
        } else {
            System.out.println("No HealthKits available.");
        }
    }

    public void verifyRoomItems(Room newRoom) {
        Room currentRoom = agent.getCurrentRoom();

        // Verificar os itens na sala
        Iterator<Item> iterator = currentRoom.getItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item instanceof BulletProofVest) {
                BulletProofVest vest = (BulletProofVest) item;
                vest.applyKit(agent); // Aplica o colete no agente
                iterator.remove(); // Remove com segurança o colete da sala
                System.out.println("BulletProofVest used and applied to agent.");
                break; // O colete foi usado, não precisa continuar iterando
            } else if (item instanceof HealthKit) {
                if (agent.getInventory().size() < 2) {
                    agent.getInventory().push((HealthKit) item); // Adiciona o HealthKit ao inventário
                    iterator.remove(); // Remove o HealthKit com segurança
                    System.out.println("HealthKit stored in inventory.");
                } else {
                    System.out.println("Backpack is full!");
                }
            }
        }
    }

    /**
     * Get user input for choosing an action.
     *
     * @return
     */
    private int getPlayerAction() {
        Scanner scanner = new Scanner(System.in);
        int action = -1;

        while (action < 1 || action > 3) {
            System.out.print("Choose an action: (1) Move, (2) Stay, (3) Use Health Kit: ");
            if (scanner.hasNextInt()) {
                action = scanner.nextInt();
            } else {
                scanner.next(); // Limpa a entrada inválida
            }

            if (action < 1 || action > 3) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return action;
    }

    /**
     * Player chooses a room to move to.
     *
     * @return
     */
    public Room chooseRoomToMove() {
        Room currentRoom = agent.getCurrentRoom();
        Network<Room> buildingNetwork = mission.getBuilding().getMap();
        ArrayUnorderedList<Room> adjacentRooms = new ArrayUnorderedList<>();

        // Obtém as salas adjacentes
        Iterator<Room> iterator = buildingNetwork.findNeighbors(currentRoom);
        while (iterator.hasNext()) {
            Room adjacentRoom = iterator.next();
            adjacentRooms.addToRear(adjacentRoom);
        }

        // Exibe as salas adjacentes para o jogador escolher
        System.out.println("Escolha uma sala para se mover:");
        for (int i = 0; i < adjacentRooms.size(); i++) {
            System.out.println((i + 1) + ". " + adjacentRooms.get(i).getName());
        }

        // Captura a escolha do jogador
        Scanner scanner = new Scanner(System.in);
        int chosenRoomIndex = scanner.nextInt() - 1; // Ajusta para índice zero
        if (chosenRoomIndex < 0 || chosenRoomIndex >= adjacentRooms.size()) {
            System.out.println("Escolha inválida. Permanecendo na sala atual.");
            return currentRoom;
        }

        return adjacentRooms.get(chosenRoomIndex);
    }

    public void checkAndMoveToExit() {
        Room currentRoom = agent.getCurrentRoom();


        if (currentRoom.isEntryExit()) {
            System.out.println("Você está em uma sala de entrada-saída. Parabéns, você saiu do Edificio!");
            System.exit(0); // Encerra o jogo
        } else {
            System.out.println("Você não está em uma sala de entrada-saída. A procurar a sala mais próxima...");

            // Encontra a sala de entrada-saída mais próxima
            Room nearestExit = findNearestExit(currentRoom);

            if (nearestExit != null) {
                System.out.println("A caminho para a sala de entrada-saída mais próxima: " + nearestExit.getName());
                moveAgentToRoom(nearestExit);
            } else {
                System.out.println("Nenhuma sala de entrada-saída encontrada.");
            }
        }
    }

    /**
     * Encontra a saída mais próxima para o agente sair do edifício.
     *
     * @param currentRoom
     * @return
     */
    public Room findNearestExit(Room currentRoom) {
        Network<Room> buildingNetwork = mission.getBuilding().getMap();
        ArrayUnorderedList<Room> entryExitRooms = mission.getBuilding().getRoomsWithEntryExit();

        Room nearestExit = null;
        int shortestPathLength = Integer.MAX_VALUE;

        for (Room exitRoom : entryExitRooms) {
            int pathLength = (int) buildingNetwork.shortestPathWeight(currentRoom, exitRoom);
            if (pathLength < shortestPathLength) {
                shortestPathLength = pathLength;
                nearestExit = exitRoom;
            }
        }

        return nearestExit;
    }


    public void endTurn() {
        enemiesMovedThisTurn = false;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Room getinitialRoom() {
        return initialRoom;
    }

    public void setinitialRoom(Room initialRoom) {
        this.initialRoom = initialRoom;
    }


    public boolean isEnemiesMovedThisTurn() {
        return enemiesMovedThisTurn;
    }

    public void setEnemiesMovedThisTurn(boolean enemiesMovedThisTurn) {
        this.enemiesMovedThisTurn = enemiesMovedThisTurn;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }

    public ArrayUnorderedList<PathWithWeight<Room>> findBestPathToTarget(Room startRoom, Room targetRoom) {
        Network<Room> buildingNetwork = mission.getBuilding().getMap();

        Iterator<PathWithWeight<Room>> pathIterator = buildingNetwork.findShortestPathWithWeights(startRoom, targetRoom);

        ArrayUnorderedList<PathWithWeight<Room>> path = new ArrayUnorderedList<>();

        while (pathIterator.hasNext()) {
            PathWithWeight<Room> pathWithWeight = pathIterator.next();
            if (pathWithWeight.getRoom().equals(startRoom) == false) {
                path.addToFront(pathWithWeight);
            }
        }

        return path;
    }


//
//    public void automaticSimulation() {
//        Room startRoom = agent.getCurrentRoom();
//        Room targetRoom = target.getRoom();
//        Room exitRoom = findNearestExit(startRoom); // Encontrar a sala de entrada-saída mais próxima
//
//        // Enquanto o agente não alcançar o alvo e sair
//        while (!agent.getCurrentRoom().equals(targetRoom)) {
//
//            // Encontrar o caminho mais curto até o alvo
//            ArrayUnorderedList<Room> pathToTarget = findBestPathToTarget(startRoom, targetRoom);
//
//            // Verifica se o caminho está vazio (não é possível encontrar um caminho)
//            if (pathToTarget.isEmpty()) {
//                System.out.println("Não é possível encontrar um caminho até o alvo devido à movimentação dos inimigos.");
//                break; // Interrompe a simulação se não houver caminho
//            }
//
//            // O agente segue automaticamente o caminho até o alvo
//            for (Room room : pathToTarget) {
//
//                moveAgentToRoom(room); // Move o agente para a próxima sala
//                System.out.println("Agente movido para a sala: " + room.getName());
//
//                // Se houver inimigos na sala, o agente entra em combate automaticamente
//                if (!room.getEnemies().isEmpty()) {
//                    System.out.println("Inimigos detectados na sala: " + room.getName());
//                    processCombat(room);
//                    if (room.getEnemies().isEmpty()) {
//                        System.out.println("Todos os inimigos foram derrotados na sala.");
//                    } else {
//                        System.out.println("Inimigos ainda estão vivos e contra-atacarão!");
//                        handleEnemyAttack();
//                    }
//                }
//
//                // Verifica se o agente chegou ao alvo
//                if (room.equals(targetRoom)) {
//                    System.out.println("Alvo alcançado! Agora, vamos sair.");
//                    break; // Saindo do loop assim que o alvo é resgatado
//                }
//            }
//
//            // Depois de mover o agente, os inimigos se movem
//            moveRandomlyEnemies(); // Atualiza a posição dos inimigos, o que pode afetar o caminho do agente
//        }
//
//        // Agora que o agente resgatou o alvo, é hora de ir para a sala de entrada-saída
//        if (exitRoom != null) {
//            System.out.println("Agora, o agente vai para a sala de entrada-saída: " + exitRoom.getName());
//            moveAgentToRoom(exitRoom);
//            System.out.println("Agente chegou à sala de entrada-saída! Parabéns, missão concluída!");
//        } else {
//            System.out.println("Não foi possível encontrar uma sala de entrada-saída.");
//        }
//    }

}

