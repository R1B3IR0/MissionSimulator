package Game;

import Structures.collections.graphs.Network;

public class Building {

    private Network<Room> roomNetwork;

    public Building() {
        this.roomNetwork = new Network<>();
    }



    public Network<Room> getRoomNetwork() {
        return roomNetwork;
    }


    @Override
    public String toString() {
        return "Building{" +
                "roomNetwork=" + roomNetwork +
                '}';
    }

}

