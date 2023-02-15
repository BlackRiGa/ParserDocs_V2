package movies.spring.data.neo4j.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;
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
        try (FileWriter writer = new FileWriter("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes6.json", false)) {
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    public List<Long> createModelFromJSON() {
//        try (Session session = driver.session()) {
//            return session.run("CALL apoc.load.json(\"file//notes5.json\")\n" +
//                            "YIELD value\n" +
//                            "MERGE (p:WordModel {id: value.ID})\n" +
//                            "SET p.form = value.FORM\n" +
//                            "set p.lemma = value.LEMMA\n" +
//                            "SET p.postag = value.POSTAG\n" +
//                            "set p.feats = value.FEATS\n" +
//                            "SET p.deprel = value.DEPREL\n" +
//                            "set p.head = value.HEAD\n" +
//                            "return p, value")
//                    .list(r -> r.get("p").asNode().id());
//        }
//    }
//    MATCH (p:WordModel),(q:WordModel)
//    WHERE p.id = q.head and p.form<>"."
//    CREATE (p)-[rel:DEPENDS]->(c)
//    RETURN p,c,rel;
}
