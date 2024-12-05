import Game.Mission;
import Game.MissionLoader;
import Game.Room;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        try {
            // Carregar o arquivo de missão
            InputStream inputStream = MissionLoader.class.getClassLoader().getResourceAsStream("mission.json");
            if (inputStream == null) throw new IllegalArgumentException("Arquivo mission.json não encontrado.");

            // Carregar a missão
            Mission mission = MissionLoader.loadMission(inputStream);

            // Exibir informações gerais da missão
            System.out.printf("Missão: %s (Versão %d)%n", mission.getCodMissao(), mission.getVersao());
            System.out.printf("Alvo: %s em %s%n",
                    mission.getAlvo().getTipo(), mission.getAlvo().getRoom().getName());

            // Exibir os quartos
            System.out.println("Quartos:");
            if (mission.getRooms().isEmpty()) {
                System.out.println("Nenhum quarto encontrado.");
            } else {
                for (Room room : mission.getRooms()) {
                    System.out.println("- " + room.getName() + room.getEnemies());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a missão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
