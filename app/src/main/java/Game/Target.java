package Game;

public class Target {
    private String tipo;
    private Room room;
    private boolean rescued;

    public Target(String tipo, Room room) {
        this.tipo = tipo;
        this.room = room;
    }

    public boolean isRescued() {
        return rescued;
    }

    public void rescue() {
        this.rescued = true;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        String text = "";

        text += "\n   Tipo: " + tipo + "\n";
        text += "   Divisao: " + room.getName() + "\n";

        return text;
    }
}
