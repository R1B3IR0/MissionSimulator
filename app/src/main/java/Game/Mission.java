package Game;

import Game.Player.Enemy;
import Structures.collections.lists.ArrayUnorderedList;

public class Mission {

    private String codMissao;
    private int versao;
    private Target alvo;
    private ArrayUnorderedList<Enemy> inimigos;



    public Mission(String codMissao, int versao, Target alvo) {
        this.codMissao = codMissao;
        this.versao = versao;
        this.alvo = alvo;
        this.inimigos = new ArrayUnorderedList<>();

    }


    public Mission() {
        this.codMissao = "";
        this.versao = 0;
        this.alvo = null;
        this.inimigos = new ArrayUnorderedList<>();

    }

    public String getCodMissao() {
        return codMissao;
    }

    public void setCodMissao(String codMissao) {
        this.codMissao = codMissao;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public Target getAlvo() {
        return alvo;
    }

    public void setAlvo(Target alvo) {
        this.alvo = alvo;
    }

    public ArrayUnorderedList<Enemy> getInimigos() {
        return inimigos;
    }

    public void addInimigo(Enemy inimigo) {
        inimigos.addToRear(inimigo);
    }

    @Override
    public String toString() {
        return "Missão [codMissao=" + codMissao + ", versao=" + versao + ", alvo=" + alvo + "]";
    }
}
