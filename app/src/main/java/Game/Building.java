package Game;

import Structures.collections.graphs.Network;
import Structures.collections.lists.ArrayUnorderedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Building {

    private Network<Room> map;
    private ArrayUnorderedList<String> edificio;
    private ArrayUnorderedList<ArrayUnorderedList<String>> conections;

    public Building() {
        this.map = new Network<Room>();
        this.edificio = new ArrayUnorderedList<String>();
        this.conections = new ArrayUnorderedList<ArrayUnorderedList<String>>();
    }


    /**
     * Carrega JSON e gera o mapa
     * @param //filePath para adicionar no paramentro no futuro
     * @return void
     */
    public void generateMapFromJson(String filePath) {
        JSONParser parser = new JSONParser();

        try {
            // Ler o JSON de um arquivo
            Object obj = parser.parse(new FileReader(filePath)); // Substitua pelo caminho do seu arquivo
            JSONObject jsonObject = (JSONObject) obj;

            // Acessar propriedades do JSON
            String codMissao = (String) jsonObject.get("cod-missao");
            long versao = (long) jsonObject.get("versao");

            // Exibir informações principais
            System.out.println("Código da Missão: " + codMissao);
            System.out.println("Versão: " + versao);

            // Manipular arrays
            JSONArray edificio = (JSONArray) jsonObject.get("edificio");
            System.out.println("Edifícios: " + edificio);

            JSONArray ligacoes = (JSONArray) jsonObject.get("ligacoes");
            System.out.println("Ligações:");
            for (Object ligacao : ligacoes) {
                JSONArray link = (JSONArray) ligacao;
                System.out.println(" - " + link.get(0) + " -> " + link.get(1));
            }

            // Manipular objetos dentro de arrays
            JSONArray inimigos = (JSONArray) jsonObject.get("inimigos");
            System.out.println("Inimigos:");
            for (Object inimigoObj : inimigos) {
                JSONObject inimigo = (JSONObject) inimigoObj;
                System.out.println(" - Nome: " + inimigo.get("nome") + ", Poder: " + inimigo.get("poder") +
                        ", Divisão: " + inimigo.get("divisao"));
            }

            // Acessar objetos diretamente
            JSONObject alvo = (JSONObject) jsonObject.get("alvo");
            System.out.println("Alvo: " + alvo.get("divisao") + " (" + alvo.get("tipo") + ")");

            // Manipular itens
            JSONArray itens = (JSONArray) jsonObject.get("itens");
            System.out.println("Itens:");
            for (Object itemObj : itens) {
                JSONObject item = (JSONObject) itemObj;
                System.out.println(" - Divisão: " + item.get("divisao") + ", Tipo: " + item.get("tipo"));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Network<Room> getMap() {
        return map;
    }


    public void setMap(Network<Room> map) {
        this.map = map;
    }

    public ArrayUnorderedList<String> getEdificio() {
        return edificio;
    }

    public void setEdificio(ArrayUnorderedList<String> edificio) {
        this.edificio = edificio;
    }

    public ArrayUnorderedList<ArrayUnorderedList<String>> getConections() {
        return conections;
    }

    public void setConections(ArrayUnorderedList<ArrayUnorderedList<String>> conections) {
        this.conections = conections;
    }
}
