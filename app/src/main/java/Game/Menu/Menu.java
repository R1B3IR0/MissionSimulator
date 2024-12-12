package Game.Menu;

import Game.MissionSimulator;

import java.io.IOException;

public class Menu {

    public static void mainMenu(MissionSimulator simulator) throws IOException {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println(Display.menuInicial());
            System.out.print("Choose option: ");
            int option = Tools.getInt();

            switch (option) {
                case 0:
                    isRunning = false;
                    break;
                case 1:
                    simulator.startGameLoop();
                    break;
                case 2:
                    // Simulação Automática
                    break;
                case 3:
                    simulator.getMission().getBuilding().visualizeGraph();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
}
