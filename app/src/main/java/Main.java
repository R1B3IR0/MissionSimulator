import Game.*;
import Game.Player.Agent;
import Game.Player.Enemy;
import Game.io.JsonHandler;
import Structures.collections.graphs.Network;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Criar salas
        Room room1 = new Room("Sala 1");
        Room room2 = new Room("Sala 2");
        Room room3 = new Room("Sala 3");
        Room room4 = new Room("Sala 4");
        Room room5 = new Room("Sala 5");
        Room room6 = new Room("Sala 6");
        Room room7 = new Room("Sala 7");

        // Criar a rede de salas (Network)
        Network<Room> network = new Network<>();

        // Adicionar salas à rede
        network.addVertex(room1);
        network.addVertex(room2);
        network.addVertex(room3);
        network.addVertex(room4);
        network.addVertex(room5);
        network.addVertex(room6);
        network.addVertex(room7);

        // Adicionar arestas entre as salas (conexões)
        network.addEdge(room1, room2, 1);  // Sala 1 <-> Sala 2
        network.addEdge(room1, room3, 1);  // Sala 1 <-> Sala 3
        network.addEdge(room2, room4, 1);  // Sala 2 <-> Sala 4
        network.addEdge(room2, room5, 1);  // Sala 2 <-> Sala 5
        network.addEdge(room3, room6, 1);  // Sala 3 <-> Sala 6
        network.addEdge(room4, room7, 1);  // Sala 4 <-> Sala 7
        network.addEdge(room5, room7, 1);  // Sala 5 <-> Sala 7
        network.addEdge(room6, room7, 1);  // Sala 6 <-> Sala 7

        // Criar o inimigo e definir a sala inicial
        Enemy enemy = new Enemy("Inimigo 1", 10, room1);  // Começa na Sala 1

        System.out.println("Inimigo inicializado na sala: " + enemy.getRoom().getName());

        // Testar o movimento aleatório do inimigo
        enemy.moveRandomly(network);

        // Exibir a nova sala do inimigo após o movimento
        System.out.println("Inimigo se moveu para a sala: " + enemy.getRoom().getName());

        // Testar o movimento aleatório mais uma vez
        enemy.moveRandomly(network);
        System.out.println("Inimigo se moveu para a sala: " + enemy.getRoom().getName());
    }
}
