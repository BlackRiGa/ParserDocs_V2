package movies.spring.data.neo4j.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ParsersService {
    public String sendTextForParse(String text) {
        try {
            textInFiles(WebClient.create()
                    .post()
                    .uri("http://localhost:2000/parse")
                    .bodyValue(text)
                    .exchange()
                    .block()
                    .bodyToMono(String.class)
                    .block());
            return "200";
        } catch (Exception e) {
            System.out.println("Ошибка");
            return "404";
        }
    }

    public void textInFiles(String text) {
        try (FileWriter writer = new FileWriter("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes5.json", false)) {
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
