package Game;

/**
 * Represents a mission in the game, including its unique code, version, target to be rescued,
 * and the building where the mission takes place.
 */
public class Mission {

    private String codMissao;
    private int version;
    private Target target;
    private Building building;

    /**
     * Constructs a new Mission with all specified details.
     *
     * @param codMissao Unique code for the mission.
     * @param version   The version of the mission.
     * @param target    The target to be rescued.
     * @param building  The building where the mission takes place.
     */
    public Mission(String codMissao, int version, Target target, Building building) {
        this.codMissao = codMissao;
        this.version = version;
        this.target = target;
        this.building = building;
    }

    /**
     * Constructs a default Mission with empty values for code, version 0, no target,
     * and an empty building.
     */
    public Mission() {
        this.codMissao = "";
        this.version = 0;
        this.target = null;
        this.building = new Building();
    }

    /**
     * Constructs a Mission with a specified building and default values for code, version, and target.
     *
     * @param building The building where the mission takes place.
     */
    public Mission(Building building) {
        this.codMissao = "";
        this.version = 0;
        this.target = null;
        this.building = building;
    }

    /**
     * Retrieves the target of the mission.
     *
     * @return The target to be rescued.
     */
    public Target getTarget() {
        return target;
    }

    /**
     * Sets the target for the mission.
     *
     * @param target The target to be assigned to the mission.
     */
    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     * Retrieves the building associated with the mission.
     *
     * @return The building where the mission occurs.
     */
    public Building getBuilding() {
        return building;
    }
}
