package Game;

import Game.Player.Enemy;
import Game.Item.Item;
import Structures.collections.lists.ArrayUnorderedList;

public class Mission {

    private String codMissao;
    private int versao;
    private Target alvo;

    // Adicionando listas para itens e inimigos
    private ArrayUnorderedList<Room> rooms; // Lista de quartos
    private ArrayUnorderedList<Item> items; // Lista de itens
    private ArrayUnorderedList<Enemy> enemies; // Lista de inimigos

    public Mission(String codMissao, int versao, Target alvo) {
        this.codMissao = codMissao;
        this.versao = versao;
        this.alvo = alvo;
        this.rooms = new ArrayUnorderedList<>();
        this.items = new ArrayUnorderedList<>();
        this.enemies = new ArrayUnorderedList<>();
    }

    public Mission() {
        this.codMissao = "";
        this.versao = 0;
        this.alvo = null;
        this.rooms = new ArrayUnorderedList<>();
        this.items = new ArrayUnorderedList<>();
        this.enemies = new ArrayUnorderedList<>();
    }

    // Métodos getter e setter para codMissao, versao e alvo
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

    // Métodos para adicionar e obter itens e inimigos
    public void addItem(Item item) {
        items.addToRear(item);
    }

    public ArrayUnorderedList<Item> getItems() {
        return items;
    }

    public void addEnemy(Enemy enemy) {
        enemies.addToRear(enemy);
    }

    public ArrayUnorderedList<Enemy> getEnemies() {
        return enemies;
    }

    // Métodos para adicionar e obter quartos
    public void addRoom(Room room) {
        rooms.addToRear(room);
    }

    public ArrayUnorderedList<Room> getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        return "Missão [codMissao=" + codMissao + ", versao=" + versao + ", alvo=" + alvo
                + ", rooms=" + rooms + ", items=" + items + ", enemies=" + enemies + "]";
    }
}
