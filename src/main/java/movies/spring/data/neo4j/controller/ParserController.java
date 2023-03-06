package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.models.WordModel;
import movies.spring.data.neo4j.repository.WordParserRepository;
import movies.spring.data.neo4j.service.ParsersService;
import movies.spring.data.neo4j.util.JsonUtils;
import org.json.simple.parser.ParseException;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

import static movies.spring.data.neo4j.service.ParsersService.addJsonArray;

@RestController
public class ParserController {
    private final ParsersService parsersService;
    private final WordParserRepository wordParserRepository;
    private final Driver driver;

    @Autowired
    public ParserController(ParsersService parsersService, WordParserRepository wordParserRepository, Driver driver) {
        this.parsersService = parsersService;
        this.wordParserRepository = wordParserRepository;
        this.driver = driver;
    }


    @GetMapping(value = {"", "/all"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<WordModel> getAllWords() {
        return wordParserRepository.findAll();
    }


    //добавляет новые узлы из json
    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createModelFromJSON(@RequestBody String text1) {
        try (Session session = driver.session()) {
            System.out.println(text1);
            parsersService.sendTextForParse(text1);
            parsersService.textInFiles(ParsersService.replaceId());
            ParsersService.replaceId();
            System.out.println(ParsersService.replaceId());
            session.run(" with randomUUID() AS uui\n" +
                    " CALL apoc.load.json(\"file///notes4.json\") YIELD value\n" +
                    "    UNWIND value AS q\n" +
                    "    MERGE (p:WordModel {id: q.LocalID})\n" +
                    "    ON CREATE SET p.id = q.ID,\n" +
                    "    p.form = q.FORM,\n" +
                    "    p.lemma = q.LEMMA,\n" +
                    "    p.postag = q.POSTAG,\n" +
                    "    p.feats = q.FEATS,\n" +
                    "    p.deprel = uui,\n" +
                    "    p.head = q.HEAD\n" +
                    "    return p, q, value\n");
            session.run("  MATCH (p:WordModel),(q:WordModel)\n" +
                    "    WHERE p.id = q.head and p.deprel=q.deprel\n" +
                    "    CREATE (q)-[rel:DEPENDS]->(p)");
            parsersService.textInFiles(ParsersService.addJsonArray());
            JsonUtils.jsonChangeForPerson();
            session.run("    with randomUUID() AS uui2\n" +
                    "    CALL apoc.load.json(\"file///notes5.json\") YIELD value\n" +
                    "    UNWIND value AS q\n" +
                    "    MERGE (p:UserModel {id: q.LocalID})\n" +
                    "    ON CREATE SET p.id = q.ID,\n" +
                    "    p.form = q.FORM,\n" +
                    "    p.head = q.HEAD,\n" +
                    "    p.feats = q.FEATS,\n" +
                    "    p.deprel = uui2\n" +
                    "    return p, q, value");
            session.run("  MATCH (p:UserModel),(q:UserModel)\n" +
                    "    WHERE q.feats='Npmsny' and p.deprel=q.deprel\n" +
                    "    CREATE (p)-[rel:DEPENDS]->(q)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
