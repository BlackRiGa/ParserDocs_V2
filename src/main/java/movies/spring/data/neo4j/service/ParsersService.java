package movies.spring.data.neo4j.service;

import movies.spring.data.neo4j.models.WordModel;
import movies.spring.data.neo4j.repository.Iservices;
import movies.spring.data.neo4j.repository.WordParserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class ParsersService implements Iservices<WordModel> {
    private final WordParserRepository wordParserRepository;
    private final Neo4jClient neo4jClient;
    private final DatabaseSelectionProvider databaseSelectionProvider;

    @Autowired
    public ParsersService(WordParserRepository wordParserRepository, Neo4jClient neo4jClient, DatabaseSelectionProvider databaseSelectionProvider) {
        this.wordParserRepository = wordParserRepository;
        this.neo4jClient = neo4jClient;
        this.databaseSelectionProvider = databaseSelectionProvider;
    }

    public static String replaceId() throws IOException {
        FileReader reader = new FileReader("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json");
        int c;
        int min = 0;
        int max = 100;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        StringBuilder str = new StringBuilder();
        while ((c = reader.read()) != -1) {
            str.append((char) c);
        }
        String newString = str.toString().replace("{\"ID", "{\"LocalID\":\"" + randomNum + "\",\"ID");
        return newString;
    }

    public static String addJsonArray() throws IOException {
        FileReader reader = new FileReader("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json");
        StringBuilder str = new StringBuilder();
        int c;
        str.append("{\"testJson\":");
        while ((c = reader.read()) != -1) {
//            System.out.println((char) c);
            str.append((char) c);
        }
        str.append("}");
        String newString = str.toString().replace("\"PUNC\"},", "\"PUNC\"}], \"testJson3\":[");
        String newString2 = newString.replaceFirst("\"testJson3\"", "\"testJson2\"");
        System.out.println(str);
        System.out.println(newString);
        System.out.println(newString2);
        return newString2.toString();
    }

    public String sendTextForParse(String text) {
        try {
            textInFiles(WebClient.create().post().uri("http://localhost:2000/parse").bodyValue(text).exchange().block().bodyToMono(String.class).block());
            System.out.println(WebClient.create().post().uri("http://localhost:2000/parse").bodyValue(text).exchange().block().bodyToMono(String.class).block());
            return "200";
        } catch (Exception e) {
            System.out.println("Ошибка");
            return e.toString();
        }
    }

    public void textInFiles(String text) {
        try (FileWriter writer = new FileWriter("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json", false)) {
            writer.write(text);
            writer.append('\n');
            writer.flush();
            System.out.println("Записал в json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public WordModel update(WordModel wordModel) {
        return null;
    }

    @Override
    public WordModel save(WordModel wordModel) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public WordModel find(String id) {
        return null;
    }

    @Override
    public List<WordModel> findAll() {
        return null;
    }

//    MATCH (p:WordModel),(q:WordModel)
//    WHERE p.id = q.head and p.LocalID=q.LocalID
//    CREATE (q)-[rel:DEPENDS]->(p)
//    RETURN p,q,rel;
//

// with randomUUID() AS uui
// CALL apoc.load.json("file///notes4.json") YIELD value
//    UNWIND value AS q
//    MERGE (p:WordModel {id: q.LocalID})
//    ON CREATE SET p.form = q.FORM,
//    p.lemma = q.LEMMA,
//    p.postag = q.POSTAG,
//    p.feats = q.FEATS,
//    p.deprel = uui,
//    p.head = q.HEAD
//    return p, q, value

//    MATCH (n)
//    OPTIONAL MATCH (n)-[r]-()
//    DELETE n,r

//    with randomUUID() AS uui
//    CALL apoc.load.json("file///notes5.json") YIELD value
//    UNWIND value AS q
//    MERGE (p:User {id: q.FORM})
//    ON CREATE SET p.feats = q.FEATS,
//    p.head = q.HEAD,
//    p.tag = q.uui
//    return p, q, value
}
