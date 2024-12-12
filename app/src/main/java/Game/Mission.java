package Game;

public class Mission {

    private String codMissao;
    private int version;
    private Target target;
    private Building building;



    public Mission(String codMissao, int version, Target target, Building building) {
        this.codMissao = codMissao;
        this.version = version;
        this.target = target;
        this.building = building;
    }

    public Mission() {
        this.codMissao = "";
        this.version = 0;
        this.target = null;
        this.building = new Building();

    }

    public Mission(Building building) {
        this.codMissao = "";
        this.version = 0;
        this.target = null;
        this.building = building;
    }


    public String getCodMissao() {
        return codMissao;
    }

    public void setCodMissao(String codMissao) {
        this.codMissao = codMissao;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

 /*   @Override
    public String toString() {
        String text = "";

        text += "Codigo da Missao: " + codMissao + "\n";
        text += "Versao: " + version + "\n";
        text += "Alvo: " + target.toString() + "\n";
        text += "Edificio: " + building.toString() + "\n";

        return text;
    }
*/
}
