package game;

public class Target {
    private String tipo;
    private Room room;


    public Target(String tipo) {
        this.tipo = tipo;
        this.room = room;
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
        return "Target [tipo=" + tipo + ", room=" + room + "]";
    }
}
