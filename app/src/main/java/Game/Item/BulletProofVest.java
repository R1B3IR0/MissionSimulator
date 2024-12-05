package Game.Item;


import Game.Room;

public class BulletProofVest extends Item {
    private int pontosExtra;

    public BulletProofVest(Room room, int pontosExtra) {
        super(room, "colete");
        this.pontosExtra = pontosExtra;
    }

    public int getPontosExtra() {
        return pontosExtra;
    }

    public void setPontosExtra(int pontosExtra) {
        this.pontosExtra = pontosExtra;
    }

    @Override
    public void aplicarEfeito() {
        System.out.println("Adicionado " + pontosExtra + " pontos de defesa extra.");
    }

    @Override
    public String toString() {
        return super.toString() + " [pontosExtra=" + pontosExtra + "]";
    }
}

