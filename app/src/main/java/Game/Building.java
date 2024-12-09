package Game;

import Structures.collections.graphs.Network;
import Structures.collections.lists.UnorderedLinkedList;
import Structures.collections.lists.UnorderedListADT;
;


public class Building {

    UnorderedLinkedList<Room> rooms = MissionLoader.RoomList;
    private Network<Room> map;


    public Building() {
        this.map = new Network<Room>();
    }

    /**
     * Carrega JSON e gera o mapa
     *
     * @param //filePath para adicionar no paramentro no futuro
     * @return void
     */
    public Network<Room> getMap() {
        return map;
    }


    public void setMap(Network<Room> map) {
        this.map = map;
    }


}

