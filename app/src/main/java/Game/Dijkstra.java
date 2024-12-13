package Game;

import Structures.collections.graphs.Network;
import Structures.collections.queues.LinkedQueue;
import Structures.exceptions.EmptyCollectionException;

import java.io.IOException;

public class Dijkstra {
    private Network network;

    public void bestWay(Room source, Room dest) throws EmptyCollectionException, IOException {
        resetDadosVertices();
        LinkedQueue<Room> roomLinkedQueue = new LinkedQueue<>();
        int count = 0;
        roomLinkedQueue.enqueue(source);

        while (!roomLinkedQueue.isEmpty()) {

            Room actualRoom = roomLinkedQueue.dequeue();
            // Visit each edge exiting actualRoom
            for (DataBestPath e : getAdjacenciaByLoja(actualRoom)) {
                if (e != null) {

                    if (count == 0) {
                        //Inicialmente inserimos na nossa LinkedQueue<Loja> (lojaQueue) todas as lojas a que a loja Source(enviada por argumento) tem ligação
                        Room roomAdj = e.getLojaDestino();
                        double custoAux = e.getCusto();
                        double custo = actualRoom.getMinCusto() + custoAux;

                        roomAdj.setMinCusto(custo);
                        roomAdj.setPrevious(actualRoom);
                        //se a loja source tiver uma ligacao para a loja destino final, a loja destino final nao é inserida na LinkedQueue<Loja> lojaQueue
                        if (roomAdj.compareTo(dest) != 0) {
                            lojaQueue.enqueue(roomAdj);
                        }

                    } else {
                        //Como ja alteramos algumas getMinDistance colocamos < para encontrar o caminho mais curto
                        Room roomAdj = e.getLojaDestino();
                        double custoAux = e.getCusto();
                        double custo = actualRoom.getMinCusto() + custoAux;
                        //Se a loja destino tiver getMinDistance == 0 quer dizer que essa loja ainda nao foi inserida
                        //na nossa LinkedQueue<Loja> lojaQueue ...
                        if (roomAdj.getMinCusto() != 0 && custo < (roomAdj.getMinCusto())) {

                            roomAdj.setMinCusto(custo);
                            roomAdj.setPrevious(actualRoom);
                            if (roomAdj.compareTo(dest) != 0) {
                                lojaQueue.enqueue(roomAdj);
                            }
                            //Assim sendo temos que inserir essa mesma loja destino, pq podera ser por essa loja o caminho mais curto
                        } else if (roomAdj.getMinCusto() == 0) {
                            roomAdj.setMinCusto(custo);
                            roomAdj.setPrevious(actualRoom);
                            if (roomAdj.compareTo(dest) != 0) {
                                lojaQueue.enqueue(roomAdj);
                            }
                        }
                    }
                }
            }
            count++;
        }
    }


    public DataBestPath[] getAdjacenciaByLoja(Room room) throws EmptyCollectionException {
        DataBestPath[] dados = new DataBestPath[network.getVertex().length];
        for (int i = 0; i < network.getVertices().length; i++) {
            if (network.getAdjMatrix()[network.getIndex(loja)][i]) {
                DataBestPath dT = (DataBestPath) network.ajdListWeight[network.getIndex(loja)][i].findMin();

                dados[i] = dT;

            }
        }
        return dados;
    }

    private void resetDadosVertices() {
        for (int i = 0; i < network.getVertices().length; i++) {
            if (network.getVertices()[i] != null) {
                ((Room) network.getVertex([i])).resetDadosMelhorCaminho();
            }
        }
    }

    public class DataBestPath<T> implements Comparable<T> {

        private double weight;
        private Room roomDestiny;

        public DataBestPath(double weight, Room roomDestiny) {
            this.weight = weight;
            this.roomDestiny = roomDestiny;
        }

        public DataBestPath() {

        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(T t) {
            DataBestPath data = (DataBestPath) t;
            if (data.weight < weight) {
                return 1;
            } else if (data.weight > weight) {
                return -1;
            }
            return 0;
        }
    }
}
