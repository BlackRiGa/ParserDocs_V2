package movies.spring.data.neo4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import movies.spring.data.neo4j.models.Neo4jModel.UserModel;
import movies.spring.data.neo4j.models.PostgreModel.UserModelForPostgres;
import movies.spring.data.neo4j.repositories.repositoryForNeo4j.UserRepositoryNeo4j;
import movies.spring.data.neo4j.repositories.repositoryForPostgre.UserRepositoryPostgres;
import movies.spring.data.neo4j.service.ParsersService;
import movies.spring.data.neo4j.service.UserService;
import movies.spring.data.neo4j.util.JsonUtils;
import org.json.simple.parser.ParseException;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class ParserController {
    private final ParsersService parsersService;
    private final JsonUtils jsonUtils;
    private final UserRepositoryNeo4j wordParserRepository;
    private final UserRepositoryPostgres userRepositoryPostgres;
    private final UserService userService;
    private final Driver driver;
    public static final String JSON_URL = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json";
    public static final String NOTES1 = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes1.json";
    public static final String NOTES2 = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes2.json";
    public static final String NOTES3 = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes3.json";


    @Autowired
    public ParserController(ParsersService parsersService, JsonUtils jsonUtils, UserRepositoryNeo4j wordParserRepository, UserRepositoryPostgres userRepository, UserService userService, Driver driver) {
        this.parsersService = parsersService;
        this.jsonUtils = jsonUtils;
        this.wordParserRepository = wordParserRepository;
        this.userRepositoryPostgres = userRepository;
        this.userService = userService;
        this.driver = driver;
    }


    @GetMapping(value = {"", "/"})
    Flux<UserModel> getMovies() {
        return wordParserRepository.findAll();
    }

    @PutMapping
    Mono<UserModel> createOrUpdateMovie(@RequestBody UserModel newUser) {
        return wordParserRepository.save(newUser);
    }

    //добавляет новые узлы из json
    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createModelFromJSON(@RequestBody String text1) throws IOException, ParseException {
        System.out.println(text1);//TEXT
        parsersService.sendTextForParse(text1);
        parsersService.textInFiles(ParsersService.replaceId(),JSON_URL);
        ParsersService.replaceId();
        System.out.println(ParsersService.replaceId());//TEXT
        parsersService.textInFiles(ParsersService.addJsonArray(), JSON_URL);
        List<String> jsonString = new ArrayList<>();
        List<String> listJson = jsonUtils.separetJSON();
        JsonUtils.separetJSON();
//        Session session = driver.session();
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
        String result = new String(Files.readAllBytes(Paths.get("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes1.json")));
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<Integer, String>> people = (List<HashMap<Integer, String>>) mapper.readValue(result, List.class);

        for (int i = 0; i < people.size(); i++) {
            HashMap<Integer, String> linkedHashMap = people.get(i);
            UserModelForPostgres userModel = new UserModelForPostgres();
            userModel.setUUID(linkedHashMap.get("UUID"));
            userModel.setLocalID(Integer.valueOf(linkedHashMap.get("LocalID")));
            userModel.setId(Integer.valueOf((linkedHashMap.get("ID"))));
            userModel.setForm(linkedHashMap.get("FORM"));
            userModel.setFeats(linkedHashMap.get("FEATS"));
            userModel.setHead(linkedHashMap.get("HEAD"));
            userModel.setDeprel(linkedHashMap.get("DEPREL"));
            userRepositoryPostgres.save(userModel);
            System.out.println(people.get(i));
        }
        String list1 = parsersService.addJsonArrayAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes1.json");
        parsersService.textInFiles(list1,"C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes1.json");
        String list2 = parsersService.addJsonArrayAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes2.json");
        parsersService.textInFiles(list2, "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes2.json");
        String list3 = parsersService.addJsonArrayAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes3.json");
        parsersService.textInFiles(list3, "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes3.json");
        System.out.println("___________________");
        parsersService.testArrayJsonParseAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes1.json");
        System.out.println("___________________");
        parsersService.testArrayJsonParseAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes2.json");
        System.out.println("___________________");
        parsersService.testArrayJsonParseAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes3.json");

    }

//        session.run(" with randomUUID() AS uui2\n" +
//                "    CALL apoc.load.json(\"file///notes" + 1 + ".json\") YIELD value\n" +
//                "    UNWIND value AS q\n" +
//                "    MERGE (p:UserModel {id: q.LocalID})\n" +
//                "    ON CREATE SET p.id = q.ID,\n" +
//                "    p.form = q.FORM,\n" +
//                "    p.head = q.HEAD,\n" +
//                "    p.feats = q.FEATS,\n" +
//                "    p.deprel = uui2\n" +
//                "    return p, q, value");
//        session.run(" with randomUUID() AS uui2\n" +
//                "    CALL apoc.load.json(\"file///notes" + 2 + ".json\") YIELD value\n" +
//                "    UNWIND value AS q\n" +
//                "    MERGE (p:UserModel {id: q.LocalID})\n" +
//                "    ON CREATE SET p.id = q.ID,\n" +
//                "    p.form = q.FORM,\n" +
//                "    p.head = q.HEAD,\n" +
//                "    p.feats = q.FEATS,\n" +
//                "    p.deprel = uui2\n" +
//                "    return p, q, value");
//        session.run(" with randomUUID() AS uui2\n" +
//                "    CALL apoc.load.json(\"file///notes" + 3 + ".json\") YIELD value\n" +
//                "    UNWIND value AS q\n" +
//                "    MERGE (p:UserModel {id: q.LocalID})\n" +
//                "    ON CREATE SET p.id = q.ID,\n" +
//                "    p.form = q.FORM,\n" +
//                "    p.head = q.HEAD,\n" +
//                "    p.feats = q.FEATS,\n" +
//                "    p.deprel = uui2\n" +
//                "    return p, q, value");
//        Session session2 = driver.session();
//        session2.run("  MATCH (p:UserModel),(q:UserModel)\n" +
//                "    WHERE q.feats='Npmsny' and p.deprel=q.deprel and p.feats<>'Npmsny'\n" +
//                "    CREATE (q)-[rel:DEPENDS]->(p)");
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
