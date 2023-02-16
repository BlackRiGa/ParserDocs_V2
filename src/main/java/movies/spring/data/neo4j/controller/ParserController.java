package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.models.WordModel;
import movies.spring.data.neo4j.repository.WordParserRepository;
import movies.spring.data.neo4j.service.ParsersService;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

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

    @PostMapping
    @RequestMapping("/parse")
    public String parseWithMp4(@RequestBody String text1) {
        System.out.println(text1);
        return parsersService.sendTextForParse(text1);
    }

//    @GetMapping("/search/{id}")
//    public WordModel getWordModel(@PathVariable String id) {
//        return parsersService.find(id);
//    }
    @GetMapping(value = { "", "/all" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<WordModel> getMovies() {
        return wordParserRepository.findAll();
    }

    @PostMapping("/new")
    public WordModel voidCreateUser(@RequestBody WordModel wordModel) {
        return parsersService.save(wordModel);
    }

    //добавляет новые узлы из json
    @GetMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> createModelFromJSON() {
        try (Session session = driver.session()) {
            parsersService.textInFiles(ParsersService.replaceId());
            return session.run(" with randomUUID() AS uui\n" +
                            " CALL apoc.load.json(\"file///notes4.json\") YIELD value\n" +
                            "    UNWIND value AS q\n" +
                            "    MERGE (p:WordModel {: q.localID})\n" +
                            "    ON CREATE SET p.form = q.FORM,\n" +
                            "    p.lemma = q.LEMMA,\n" +
                            "    p.postag = q.POSTAG,\n" +
                            "    p.feats = q.FEATS,\n" +
                            "    p.deprel = uui,\n" +
                            "    p.head = q.HEAD\n" +
                            "    return p, q, value\n")
                    .list(r -> r.get("p").asNode().id());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
