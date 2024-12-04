import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("mission.json");

        if (inputStream == null) {
            System.out.println("Arquivo não encontrado.");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            System.out.println(rootNode.toPrettyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
