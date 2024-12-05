package Game.Item;

import Game.Room;

public class HealthKit extends Item{

    private int pontosRecuperados;

    public HealthKit(Room room, int pontosRecuperados) {
        super(room, "Health Kit");
        this.pontosRecuperados = pontosRecuperados;
    }

    public int getPontosRecuperados() {
        return pontosRecuperados;
    }

    public void setPontosRecuperados(int pontosRecuperados) {
        this.pontosRecuperados = pontosRecuperados;
    }

    @Override
    public void aplicarEfeito() {
        System.out.println("Foi recuperado " + pontosRecuperados + " pontos de vida.");
    }

    @Override
    public String toString() {
        return super.toString() + " [pontosRecuperados=" + pontosRecuperados + "]";
    }
}

