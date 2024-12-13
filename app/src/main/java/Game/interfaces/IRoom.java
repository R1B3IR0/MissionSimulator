package Game.interfaces;

import Game.Item.Item;
import Game.Player.Enemy;

public interface IRoom {

    void addEnemy(Enemy enemy);

    void addItem(Item item);

    void removeEnemy(Enemy enemy);

    void removeItem(Item item);

    boolean isEntryExit();

    boolean isNormalRoom();
}
