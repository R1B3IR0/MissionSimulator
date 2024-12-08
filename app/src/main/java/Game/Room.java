package Game;

import Game.Player.Enemy;
import Game.Item.Item;
import Structures.collections.lists.ArrayUnorderedList;

public class Room {
    private String name;
    private ArrayUnorderedList<Enemy> enemies;
    private ArrayUnorderedList<Item> items;

    public Room(String name) {
        this.name = name;
        this.enemies = new ArrayUnorderedList<>();
        this.items = new ArrayUnorderedList<>();
    }

    public String getName() {
        return name;
    }


    public void addEnemy(Enemy enemy) {
        enemies.addToRear(enemy);
    }


    public void addItem(Item item) {
        items.addToRear(item);
    }


    public ArrayUnorderedList<Enemy> getEnemies() {
        return enemies;
    }


    public ArrayUnorderedList<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        String text = "";

        text += "Nome: " + name + "\n";
        text += "Inimigos: " + enemies.toString() + "\n";
        text += "Itens: " + items.toString() + "\n";

        return text;
    }
}
