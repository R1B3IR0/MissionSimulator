package Game.interfaces;

import Game.PathWithWeight;
import Game.Room;
import Structures.collections.lists.ArrayUnorderedList;

public interface IMissionSimulator {

    void startGameLoop(boolean automatic);

    void moveAgentToRoom(Room newRoom);

    void stayInCurrentRoom();

    void processAgentTurn(boolean automatic);

    void processCombat(Room room);

    void handleEnemyAttack();

    void moveRandomlyEnemies();

    void processEnemiesTurn();

    void processTargetInteraction();

    void useHealthKit();

    void verifyRoomItems(Room newRoom);

    int getPlayerAction();

    Room chooseRoomToMove();

    void checkAndMoveToExit();

    Room findNearestExit(Room currentRoom);

    ArrayUnorderedList<PathWithWeight<Room>> findBestPathToTarget(Room startRoom, Room targetRoom);


}
