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
    private boolean enemiesMovedThisTurn;
    private boolean isPlayerTurn; // Flag para controlar o turno do jogador

    public MissionSimulator(Mission mission, Agent agent) {
        this.mission = mission;
        this.agent = agent;
        this.target = mission.getTarget();
        this.enemiesMovedThisTurn = false;
        this.isPlayerTurn = true;

    }

    /**
     * Lógica dos turnos do jogo
     */
    public void startGameLoop() {
        boolean gameOver = false;

        while (!gameOver) {
            if (isPlayerTurn) {  // Se for o turno do jogador é True
                processAgentTurn();
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

    public void processAgentTurn() {
        Room currentRoom = agent.getCurrentRoom();

        System.out.println("Choose an action: (1) Move, (2) Stay, (3) Use Health Kit");
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

            if (enemy.getHeatlh() <= 0) {
                System.out.println(enemy.getName() + " was defeated!");
                iterator.remove(); // Remove com segurança

            }
        }

        // Verificação: Se todos os inimigos foram derrotados, encerra o combate
        if (room.getEnemies().isEmpty()) {
            System.out.println("All enemies in the room have been defeated. Combat ends.");

        }else {
            System.out.println("Enemies remains in the room. Prepare for their retaliation!");
            isPlayerTurn = false; // Muda para o turno dos inimigos
        }
    }

    public void handleEnemyAttack() {
        System.out.println("Enemies are attacking!");
        for (Enemy enemy : agent.getCurrentRoom().getEnemies()) {
            agent.takeDamage(enemy.getPower());
            System.out.println("Agent took damage from " + enemy.getName() + ". Current health: " + agent.getHealth());

            if (agent.getHealth() <= 0) {
                System.out.println("Agent has been defeated! Game Over.");
                break;
            }
        }
    }

    public void moveRandomlyEnemies() {
        for (Room room : mission.getBuilding().getRooms()) {
            if (!room.equals(agent.getCurrentRoom())) {
                Iterator<Enemy> enemyIterator = room.getEnemies().iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    enemy.moveRandomly(mission.getBuilding().getMap(), mission.getBuilding());
                    System.out.println("Enemies moved to a new room.");
                }
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

    public void processItemUse() {
        if (!agent.getInventory().isEmpty()) {
            HealthKit kit = agent.getInventory().pop();
            int healingPoints = kit.getPointsRecovered();
            System.out.println("Agent used a HealthKit and recovered " + healingPoints + " health points.");
            System.out.println("Current health: " + agent.getHealth());
        } else if (agent.getHealth() >= 100) {
            System.out.println("Life is full, I can't heal!");
        } else if (agent.getInventory().isEmpty()) {
            System.out.println("No healing items available!");
        }
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
     * @return
     */
    public Room chooseRoomToMove() {
        Room currentRoom = agent.getCurrentRoom();
        Network<Room> buildingNetwork = mission.getBuilding().getMap();
        ArrayUnorderedList<Room> adjacentRooms = new ArrayUnorderedList<>();

        // Obtém as salas adjacentes
        Iterator<Room> iterator = buildingNetwork.vertexIterator();
        while (iterator.hasNext()) {
            Room adjacentRoom = iterator.next();
            if (buildingNetwork.getWeight(currentRoom, adjacentRoom) != Double.POSITIVE_INFINITY) {
                adjacentRooms.addToRear(adjacentRoom);
            }
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

    public void processExit() {
        Room currentRoom = agent.getCurrentRoom();


        if (!currentRoom.isEntryExit()) {
            System.out.println("You need to be in a room classified as 'entrada-saida' to leave the building.");
            return;
        }


        if (!mission.getTarget().isRescued()) {
            System.out.println("You haven't rescued the target yet. Complete the mission before leaving.");
            return;
        }

        System.out.println("Congratulations! You have successfully completed the mission!");
        System.exit(0);
    }


    public void endTurn() {
        enemiesMovedThisTurn = false;
    }

}

