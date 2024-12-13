package Game.interfaces;

import Game.Connection;
import Game.Room;
import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.lists.UnorderedListADT;

public interface IBuilding {

    void generateMap();

    double calculateWeight(Room room1, Room room2);

    void updateWeights();

    void visualizeGraph();

    UnorderedListADT<Room> getRooms();

    UnorderedListADT<Connection> getConnections();

    ArrayUnorderedList<Room> getRoomsWithEntryExit();

    void addRoom(Room room);
}
