package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.service.ParsersService;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParserController {
    private final ParsersService parsersService;
    private final Driver driver;

    @Autowired
    public ParserController(ParsersService parsersService, Driver driver) {
        this.parsersService = parsersService;
        this.driver = driver;
    }

    @PostMapping
    @RequestMapping("/parse")
    public String parseWithMp4(@RequestBody String text1) {
        System.out.println(text1);
        return parsersService.sendTextForParse(text1);
    }

    @GetMapping(path = "/words", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> createModelFromJSON() {
        try (Session session = driver.session()) {
            return session.run("CALL apoc.load.json(\"file//notes6.json\")\n" +//TODO Сделать меняющийся файл
                            "YIELD value\n" +
                            "MERGE (p:WordModel {id: value.ID})\n" +
                            "SET p.form = value.FORM\n" +
                            "set p.lemma = value.LEMMA\n" +
                            "SET p.postag = value.POSTAG\n" +
                            "set p.feats = value.FEATS\n" +
                            "SET p.deprel = value.DEPREL\n" +
                            "set p.head = value.HEAD\n" +
                            "return p, value")
                    .list(r -> r.get("p").asNode().id());
        }
    }

}
