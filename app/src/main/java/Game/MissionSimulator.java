package Game;

import Game.Item.BulletProofVest;
import Game.Item.HealthKit;
import Game.Item.Item;
import Game.Player.Agent;
import Game.Player.Enemy;
import Structures.collections.graphs.Network;

import java.util.Iterator;

public class MissionSimulator {
    private Mission mission;
    private Agent agent;
    private Target target;
    private boolean enemiesMovedThisTurn;

    public MissionSimulator(Mission mission, Agent agent) {
        this.mission = mission;
        this.agent = agent;
        this.target = mission.getTarget();
        this.enemiesMovedThisTurn = false;

    }

    //Atualiza pesos das arestas (calculateWeight());

    public void processAgentTurn() {
        Room currentRoom = agent.getCurrentRoom(); // O Tó Cruz está na sala atual

        if (!currentRoom.getEnemies().isEmpty()) {
            processCombat(currentRoom); // Scenario 1: Tó Cruz entra na sala e encontra inimigos
        } else if (mission.getTarget().getRoom().equals(currentRoom) && currentRoom.getEnemies().isEmpty()) {
            processTargetInteraction(); // Scenario 6: Tó Cruz encontra o alvo sem inimigos
        } else {
            System.out.println("No enemies in the room. Choose your next action.");
        }

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


    public void processCombat(Room room) {
        Network<Room> buildingNetwork = mission.getBuilding().getMap();
        System.out.println("Combat initiated in division: " + room.getName());
        Iterator<Enemy> iterator = room.getEnemies().iterator();


        // Cenário 1: Fase do jogador (Tó Cruz ataca os inimigos que estão na sala)
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.takeDamage(agent.getPower());

            if (enemy.getHeatlh() <= 0) {
                iterator.remove(); // Remove com segurança
                System.out.println(enemy.getName() + " was defeated!");
                mission.getBuilding().updateWeights();
            }
        }

        // Verificação: Se todos os inimigos foram derrotados, encerra o combate
        if (room.getEnemies().isEmpty()) {
            System.out.println("All enemies in the room were defeated. Combat ends.");
            mission.getBuilding().updateWeights();  // Atualiza os pesos das arestas
            return;
        }

        Iterator<Enemy> enemyIterator = room.getEnemies().iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            agent.takeDamage(enemy.getPower());
            System.out.println("Agent took damage. Current health: " + agent.getHealth());

            if (agent.getHealth() <= 0) {
                System.out.println("Agent has been defeated! Game Over.");
                return;
            }
        }
        if (!enemiesMovedThisTurn) {
            for (Room r : mission.getBuilding().getRooms()) {
                if (!r.equals(agent.getCurrentRoom())) {
                    Iterator<Enemy> moveIterator = r.getEnemies().iterator();
                    while (moveIterator.hasNext()) {
                        Enemy enemy = moveIterator.next();
                        enemy.moveRandomly(buildingNetwork, mission.getBuilding());
                    }
                }
            }
            enemiesMovedThisTurn = true;  // Marca que os inimigos se moveram nesta ronda
        }
    }


    public void processEnemiesTurn() {
        Network<Room> buildingNetwork = mission.getBuilding().getMap();

        // Movimentação dos inimigos (Cenário 2: Sala sem inimigos, inimigos se movem aleatoriamente)
        if (!enemiesMovedThisTurn) {
            for (Room room : mission.getBuilding().getRooms()) {
                if (!room.equals(agent.getCurrentRoom())) {
                    Iterator<Enemy> enemyIterator = room.getEnemies().iterator();
                    while (enemyIterator.hasNext()) {
                        Enemy enemy = enemyIterator.next();
                        enemy.moveRandomly(buildingNetwork, mission.getBuilding()); // Movimenta apenas inimigos fora da sala do agente
                    }
                }
            }

            enemiesMovedThisTurn = true;
        }

        // Cenário 3: Inimigos entram na sala onde o agente está
        if (!agent.getCurrentRoom().getEnemies().isEmpty()) {
            System.out.println("Enemies entered the agent's room!");

            System.out.println("Enemies attack first!");
            Iterator<Enemy> enemyIterator = agent.getCurrentRoom().getEnemies().iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                agent.takeDamage(enemy.getPower());
                System.out.println("Agent took damage from " + enemy.getName() + ". Current health: " + agent.getHealth());

                if (agent.getHealth() <= 0) {
                    System.out.println("Agent has been defeated! Game Over.");
                    return;
                }
            }
            processCombat(agent.getCurrentRoom());
        }
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
            return;
        }
        // Cenário 6: Tó Cruz encontra o alvo sem inimigos
        System.out.println("Agent found the target!");
        mission.getTarget().rescue();
        System.out.println("Target rescued! Exit the building to complete the mission.");
    }

    public void processExit() {
        Room currentRoom = agent.getCurrentRoom();


        if (!currentRoom.isEntryExit()) {
            System.out.println("You need to be in a room classified as 'entry-exit' to leave the building.");
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

