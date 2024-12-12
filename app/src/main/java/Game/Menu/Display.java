package Game.Menu;


public class Display {

    public static String manualMenu() {
        return " ____________________________________________________________________\n" +
                "|                  IMF MISSION SIMULATOR                            |\n" +
                "|___________________________________________________________________|\n" +
                "| Sala Atual :                                                      |\n" +
                "| 1. Mover para outra sala                                          |\n" +
                "| 2. Ver Inventário                                                 |\n" +
                "| 3. Atacar os inimigos na sala                                     |\n" +
                "| 4. Interagir com o Alvo                                           |\n" +
                "| 5. Voltar ao menu anterior                                        |\n" +
                "| 0. Sair do Jogo                                                   |\n" +
                "|___________________________________________________________________|";
    }


    public static String menuAutomatico() {
        return " ____________________________________________________________________\n" +
                "|                         IMF MISSION SIMULATOR                     |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. Começar Modo Automático                                        |\n" +
                "| 2. Voltar ao menu anterior                                        |\n" +
                "| 0. Sair do Jogo                                                   |\n" +
                "|___________________________________________________________________|\n";

    }

    public static String menuInicial() {
        return " ____________________________________________________________________\n" +
                "|                     IMF MISSION SIMULATOR                         |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. Modo Manual                                                    |\n" +
                "| 2. Modo Automático                                                |\n" +
                "| 0. Sair do Jogo                                                   |\n" +
                "|___________________________________________________________________|\n";
    }


}