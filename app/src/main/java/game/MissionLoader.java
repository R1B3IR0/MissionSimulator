package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MissionLoader {

    /**
     * Carrega uma missão do arquivo JSON e mapeia para o objeto Mission.
     */
    public static Mission loadMission(InputStream inputStream) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Carregar o JSON como um nó genérico
        JsonNode rootNode = objectMapper.readTree(inputStream);

        // Mapear o JSON para a classe Mission
        Mission mission = new Mission();
        mission.setCodMissao(rootNode.get("cod-missao").asText());
        mission.setVersao(rootNode.get("versao").asInt());

        // Mapeia o alvo
        JsonNode alvoNode = rootNode.get("alvo");
        Target target = new Target(alvoNode.get("tipo").asText());
        Room targetRoom = new Room(alvoNode.get("divisao").asText(), false);
        target.setRoom(targetRoom);
        mission.setAlvo(target);


        List<String> edificio = new ArrayList<>();
        Iterator<JsonNode> edificioIterator = rootNode.get("edificio").elements();
        while (edificioIterator.hasNext()) {
            edificio.add(edificioIterator.next().asText());
        }

        // "ligacoes" também pode ser mapeada como uma lista de listas de strings
        List<List<String>> ligacoes = new ArrayList<>();
        Iterator<JsonNode> ligacoesIterator = rootNode.get("ligacoes").elements();
        while (ligacoesIterator.hasNext()) {
            JsonNode linkNode = ligacoesIterator.next();
            List<String> link = new ArrayList<>();
            link.add(linkNode.get(0).asText());
            link.add(linkNode.get(1).asText());
            ligacoes.add(link);
        }

        // "inimigos" não mapeados diretamente nas classes, mas podemos armazenar em uma lista genérica
        List<JsonNode> inimigos = new ArrayList<>();
        Iterator<JsonNode> inimigosIterator = rootNode.get("inimigos").elements();
        while (inimigosIterator.hasNext()) {
            inimigos.add(inimigosIterator.next());
        }

        // "entradas-saidas" não mapeados diretamente nas classes, mas podemos armazenar em uma lista
        List<String> entradasSaidas = new ArrayList<>();
        Iterator<JsonNode> entradasSaidasIterator = rootNode.get("entradas-saidas").elements();
        while (entradasSaidasIterator.hasNext()) {
            entradasSaidas.add(entradasSaidasIterator.next().asText());
        }

        // "itens" não mapeados diretamente nas classes, mas podemos armazenar em uma lista
        List<JsonNode> itens = new ArrayList<>();
        Iterator<JsonNode> itensIterator = rootNode.get("itens").elements();
        while (itensIterator.hasNext()) {
            itens.add(itensIterator.next());
        }

        // Adiciona todas as listas de informações extras ao Mission (por enquanto)
        // Estes dados podem ser usados posteriormente se você quiser.
        System.out.println("Edifício: " + edificio);
        System.out.println("Ligações: " + ligacoes);
        System.out.println("Inimigos: " + inimigos);
        System.out.println("Entradas e Saídas: " + entradasSaidas);
        System.out.println("Itens: " + itens);

        return mission;
    }

    public static void main(String[] args) throws Exception {
        // Carregar o JSON a partir do classpath
        InputStream inputStream = MissionLoader.class.getClassLoader().getResourceAsStream("mission.json");

        // Verificar se o arquivo foi encontrado
        if (inputStream == null) {
            throw new IllegalArgumentException("Arquivo mission.json não encontrado no classpath.");
        }

        // Carregar a missão
        Mission mission = loadMission(inputStream);

        // Exibe a missão carregada
        System.out.println(mission);
    }
}

