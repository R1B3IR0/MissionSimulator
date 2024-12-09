package Game;

public class Mission {

    private String codMissao;
    private Version versao;     // Auto-incremental?
    private Target alvo;


    public Mission(String codMissao, Version versao, Target alvo) {
        this.codMissao = codMissao;
        this.versao = versao;
        this.alvo = alvo;
    }

    public Mission() {
        this.codMissao = "";
        this.versao = null;
        this.alvo = null;

    }


    public String getCodMissao() {
        return codMissao;
    }

    public void setCodMissao(String codMissao) {
        this.codMissao = codMissao;
    }

    public Version getVersao() {
        return versao;
    }

    public void setVersao(Version versao) {
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
        String text = "";

        text += "Código da Missão: " + codMissao + "\n";
        text += "Versão: " + versao.toString() + "\n";
        text += "Alvo: " + alvo.toString() + "\n";

        return text;
    }

}
