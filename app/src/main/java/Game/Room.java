package Game;

import Game.Player.Enemy;
import Game.Item.Item;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.lists.UnorderedListADT;

import java.util.Iterator;

/**
 * Represents a room within a building in the mission simulation.
 * A room can contain enemies, items, and has a specific classification such as "normal" or "entrada-saida".
 */
public class Room {

    private String name; // Name of the room
    private String classification; // Classification of the room (e.g., "normal", "entrada-saida")
    private UnorderedListADT<Enemy> enemies; // List of enemies present in the room
    private UnorderedListADT<Item> items; // List of items present in the room

    /**
     * Constructs a new room with the given name.
     * The default classification is "normal", and the room initially contains no enemies or items.
     *
     * @param name The name of the room.
     */
    public Room(String name) {
        this.name = name;
        this.classification = "normal"; // Default classification
        this.enemies = new ArrayUnorderedList<>();
        this.items = new ArrayUnorderedList<>();
    }

    /**
     * Retrieves the classification of the room.
     *
     * @return The classification of the room.
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Sets the classification of the room.
     *
     * @param classification The new classification for the room (e.g., "normal", "entrada-saida").
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * Retrieves the name of the room.
     *
     * @return The name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the room.
     *
     * @param name The new name for the room.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds an enemy to the room.
     *
     * @param enemy The enemy to be added to the room.
     */
    public void addEnemy(Enemy enemy) {
        enemies.addToRear(enemy);
    }

    /**
     * Adds an item to the room.
     *
     * @param item The item to be added to the room.
     */
    public void addItem(Item item) {
        items.addToRear(item);
    }

    /**
     * Retrieves the list of enemies present in the room.
     *
     * @return An unordered list of enemies in the room.
     */
    public UnorderedListADT<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Sets the list of enemies present in the room.
     *
     * @param enemies The new list of enemies for the room.
     */
    public void setEnemies(UnorderedListADT<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Retrieves the list of items present in the room.
     *
     * @return An unordered list of items in the room.
     */
    public UnorderedListADT<Item> getItems() {
        return items;
    }

    /**
     * Sets the list of items present in the room.
     *
     * @param items The new list of items for the room.
     */
    public void setItems(UnorderedListADT<Item> items) {
        this.items = items;
    }

    /**
     * Removes an enemy from the room.
     *
     * @param enemy The enemy to be removed from the room.
     */
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    /**
     * Removes an item from the room.
     * If the item is not found, a message will be printed to the console.
     *
     * @param item The item to be removed from the room.
     */
    public void removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println(item.getType() + " has been removed from the room.");
        } else {
            System.out.println("Item not found in the room.");
        }
    }

    /**
     * Checks whether the room is classified as an entry/exit room.
     *
     * @return {@code true} if the room is an entry/exit room; {@code false} otherwise.
     */
    public boolean isEntryExit() {
        return "entrada-saida".equalsIgnoreCase(classification);
    }

    /**
     * Checks whether the room is classified as a normal room.
     *
     * @return {@code true} if the room is a normal room; {@code false} otherwise.
     */
    public boolean isNormalRoom() {
        return "normal".equalsIgnoreCase(classification);
    }


}
