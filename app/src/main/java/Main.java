
import Game.MissionLoader;
import Game.Room;
import Structures.collections.lists.UnorderedLinkedList;


public class Main {
    public static void main(String[] args) {
        String filePath = "app//src//main//resources//mission.json";
        MissionLoader.readFromJson(filePath);
        UnorderedLinkedList<Room> rooms = MissionLoader.RoomList;
        System.out.println(rooms);


    }
}
