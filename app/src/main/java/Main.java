import Game.Mission;
import Game.MissionLoader;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        try {
            InputStream inputStream = MissionLoader.class.getClassLoader().getResourceAsStream("mission.json");
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo mission.json não encontrado no classpath.");
            }

            // Carregar a missão
            Mission mission = MissionLoader.loadMission(inputStream);

            // Exibir missão
            System.out.println(mission);


        } catch (Exception e) {
            System.err.println("Erro ao carregar a missão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
