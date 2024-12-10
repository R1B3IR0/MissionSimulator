package Game;

import Game.Item.BulletProofVest;
import Game.Item.HealthKit;
import Game.Item.Item;
import Game.Player.Agent;
import Game.Player.Enemy;

public class MissionSimulator {
    private Mission mission;
    private Agent agent;
    private Target target;

    public MissionSimulator(Mission mission) {
        this.mission = mission;
        this.agent = new Agent();
        this.target = mission.getTarget();

    }

    //Atualiza pesos das arestas (calculateWeight());

    private void processAgentTurn() {
        Room currentRoom = agent.getCurrentRoom();

        if (!currentRoom.getEnemies().isEmpty()) {
            processCombat(currentRoom); // Scenario 1: Tó Cruz entra na sala e encontra inimigos
        } else if (mission.getTarget().getRoom().equals(currentRoom) && currentRoom.getEnemies().isEmpty()) {
            processTargetInteraction(); // Scenario 6: Tó Cruz encontra o alvo sem inimigos
        } else {
            System.out.println("No enemies in the room. Choose your next action.");
        }

        // Verificar os itens na sala
        for (Item item : currentRoom.getItems()) {
            if (item instanceof BulletProofVest) {
                // Se o agente encontrar um colete, ele usa automaticamente
                BulletProofVest vest = (BulletProofVest) item;
                vest.applyKit(agent);  // Aplica os pontos de vida do colete diretamente no agente
                currentRoom.removeItem(item);  // Remove o colete da sala após o uso
                System.out.println("BulletProofVest used and applied to agent.");
                break;  // O colete é usado uma vez e removido
            } else if (item instanceof HealthKit) {
                if (agent.getInventory().size() < 2) {
                    agent.getInventory().push((HealthKit) item);  // Adiciona o HealthKit ao inventário
                    currentRoom.removeItem(item);  // Remove o HealthKit da sala após armazenar
                    System.out.println("HealthKit stored in inventory.");
                } else {
                    System.out.println("Backpack is full !");
                }
            }
        }
    }


    private void processCombat(Room room) {
        System.out.println("Combat initiated in division: " + room.getName());

        // Cenário 1: Fase do jogador (Tó Cruz ataca os inimigos que estão na sala)
        for (Enemy enemy : room.getEnemies()) {
            enemy.takeDamage(agent.getPower());
            if (enemy.getHeatlh() <= 0) {
                room.removeEnemy(enemy);
                System.out.println(enemy.getName() + " was defeated!");
            }
        }

        // Verificação: Se todos os inimigos foram derrotados, encerra o combate
        if (room.getEnemies().isEmpty()) {
            System.out.println("All enemies in the room were defeated. Combat ends.");
            return;
        }

        // Cenário 1: Fase dos inimigos (contra-ataque)
        System.out.println("Remaining enemies counterattack!");
        for (Enemy enemy : room.getEnemies()) {
            agent.takeDamage(enemy.getPower());
            System.out.println("Agent took damage. Current health: " + agent.getHealth());
            enemy.moveRandomly(mission.getBuilding());

            if (agent.getHealth() <= 0) {
                System.out.println("Agent has been defeated! Game Over.");
                return;
            }
        }
    }


    private void processEnemiesTurn() {
        // Movimentação dos inimigos (Cenário 2: Sala sem inimigos, inimigos se movem aleatoriamente)
        for (Room room : mission.getBuilding().getRooms()) {
            for (Enemy enemy : room.getEnemies()) {
                if (!room.equals(agent.getCurrentRoom())) {
                    enemy.moveRandomly(mission.getBuilding()); // Movimenta apenas inimigos fora da sala do agente
                }
            }
        }

        // Cenário 3: Inimigos entram na sala onde o agente está
        if (!agent.getCurrentRoom().getEnemies().isEmpty()) {
            System.out.println("Enemies entered the agent's room!");

            // Fase dos inimigos: Ataque dos inimigos
            System.out.println("Enemies attack first!");
            for (Enemy enemy : agent.getCurrentRoom().getEnemies()) {
                agent.takeDamage(enemy.getPower());
                System.out.println("Agent took damage from " + enemy.getName() + ". Current health: " + agent.getHealth());

                // Verificação se o agente morreu
                if (agent.getHealth() <= 0) {
                    System.out.println("Agent has been defeated! Game Over.");
                    return;
                }
            }

            processCombat(agent.getCurrentRoom());
        }
    }


    private void processItemUse() {
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


    private void processTargetInteraction() {
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
}

