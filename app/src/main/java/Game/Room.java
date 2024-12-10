package Game;

import Game.Player.Enemy;
import Game.Item.Item;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.lists.UnorderedListADT;

import java.util.Iterator;

public class Room {
    private String name;
    private UnorderedListADT<Enemy> enemies;
    private UnorderedListADT<Item> items;

    public Room(String name) {
        this.name = name;
        this.enemies = new ArrayUnorderedList<>();
        this.items = new ArrayUnorderedList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Adiciona um inimigo à sala
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
        enemies.addToRear(enemy);
    }

    /**
     * Adiciona um item à sala
     * @param item
     */
    public void addItem(Item item) {
        items.addToRear(item);
    }

    public UnorderedListADT<Enemy> getEnemies() {
        return enemies;
    }

    public UnorderedListADT<Item> getItems() {
        return items;
    }

    public void setEnemies(UnorderedListADT<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setItems(UnorderedListADT<Item> items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }
    public void removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println(item.getType() + " has been removed from the room.");
        } else {
            System.out.println("Item not found in the room.");
        }
    }

/*
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();

        text.append("Divisão: ").append(name).append("\n");
        text.append("Inimigos: ");
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            text.append(enemyIterator.next().toString()).append(", ");
        }
        text.append("\nItens: ");
        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            text.append(itemIterator.next().toString()).append(", ");
        }

        return text.toString();
    }
 */
}

