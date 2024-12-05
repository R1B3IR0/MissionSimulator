package Game;

public class Mission {

    private String codMissao;
    private int versao;
    private Target alvo;


    public Mission(String codMissao, int versao, Target alvo) {
        this.codMissao = codMissao;
        this.versao = versao;
        this.alvo = alvo;

    }

    public Mission() {
        this.codMissao = "";
        this.versao = 0;
        this.alvo = null;

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


    @Override
    public String toString() {
        return "Missão [codMissao=" + codMissao + ", versao=" + versao + ", alvo=" + alvo + "]";
    }
}
