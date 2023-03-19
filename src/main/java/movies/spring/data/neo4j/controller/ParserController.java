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
import java.util.ArrayList;
import java.util.List;

@RestController
public class ParserController {
    private final ParsersService parsersService;
    private final JsonUtils jsonUtils;
    private final WordParserRepository wordParserRepository;
    private final Driver driver;

    @Autowired
    public ParserController(ParsersService parsersService, JsonUtils jsonUtils, WordParserRepository wordParserRepository, Driver driver) {
        this.parsersService = parsersService;
        this.jsonUtils = jsonUtils;
        this.wordParserRepository = wordParserRepository;
        this.driver = driver;
    }


    @GetMapping(value = {"", "/all"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<WordModel> getAllWords() {
        return wordParserRepository.findAll();
    }


    //добавляет новые узлы из json
    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createModelFromJSON(@RequestBody String text1) throws IOException, ParseException {
        System.out.println(text1);//TEXT
        parsersService.sendTextForParse(text1);
        parsersService.textInFiles(ParsersService.replaceId());
        ParsersService.replaceId();
        System.out.println(ParsersService.replaceId());//TEXT
        parsersService.textInFiles(ParsersService.addJsonArray());
        List<String> jsonString = new ArrayList<>();
        List<String> listJson = jsonUtils.separetJSON();
        JsonUtils.separetJSON();
            Session session = driver.session();
//            session.run(" with randomUUID() AS uui\n" +
//                    " CALL apoc.load.json(\"file///notes" + j + ".json\") YIELD value\n" +
//                    "    UNWIND value AS q\n" +
//                    "    MERGE (p:WordModel {id: q.LocalID})\n" +
//                    "    ON CREATE SET p.id = q.ID,\n" +
//                    "    p.form = q.FORM,\n" +
//                    "    p.lemma = q.LEMMA,\n" +
//                    "    p.postag = q.POSTAG,\n" +
//                    "    p.feats = q.FEATS,\n" +
//                    "    p.deprel = uui,\n" +
//                    "    p.head = q.HEAD\n" +
//                    "    return p, q, value\n");
////                for (int i = 0; i < listJson.size(); i++) {
//            session.run("  MATCH (p:WordModel),(q:WordModel)\n" +
//                    "    WHERE p.id = q.head and p.deprel=q.deprel\n" +
//                    "    CREATE (q)-[rel:DEPENDS]->(p)");
            JsonUtils.jsonChangeForPerson();
            session.run(" with randomUUID() AS uui2\n" +
                    "    CALL apoc.load.json(\"file///notes" + 1 + ".json\") YIELD value\n" +
                    "    UNWIND value AS q\n" +
                    "    MERGE (p:UserModel {id: q.LocalID})\n" +
                    "    ON CREATE SET p.id = q.ID,\n" +
                    "    p.form = q.FORM,\n" +
                    "    p.head = q.HEAD,\n" +
                    "    p.feats = q.FEATS,\n" +
                    "    p.deprel = uui2\n" +
                    "    return p, q, value");
            session.run(" with randomUUID() AS uui2\n" +
                    "    CALL apoc.load.json(\"file///notes" + 2 + ".json\") YIELD value\n" +
                    "    UNWIND value AS q\n" +
                    "    MERGE (p:UserModel {id: q.LocalID})\n" +
                    "    ON CREATE SET p.id = q.ID,\n" +
                    "    p.form = q.FORM,\n" +
                    "    p.head = q.HEAD,\n" +
                    "    p.feats = q.FEATS,\n" +
                    "    p.deprel = uui2\n" +
                    "    return p, q, value");
            session.run(" with randomUUID() AS uui2\n" +
                    "    CALL apoc.load.json(\"file///notes" + 3 + ".json\") YIELD value\n" +
                    "    UNWIND value AS q\n" +
                    "    MERGE (p:UserModel {id: q.LocalID})\n" +
                    "    ON CREATE SET p.id = q.ID,\n" +
                    "    p.form = q.FORM,\n" +
                    "    p.head = q.HEAD,\n" +
                    "    p.feats = q.FEATS,\n" +
                    "    p.deprel = uui2\n" +
                    "    return p, q, value");
            Session session2 = driver.session();
            session2.run("  MATCH (p:UserModel),(q:UserModel)\n" +
                    "    WHERE q.feats='Npmsny' and p.deprel=q.deprel and p.feats<>'Npmsny'\n" +
                    "    CREATE (p)-[rel:DEPENDS]->(q)");
        }
}
