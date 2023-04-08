package movies.spring.data.neo4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import movies.spring.data.neo4j.models.Neo4jModel.ActionModel;
import movies.spring.data.neo4j.models.Neo4jModel.ObjectModel;
import movies.spring.data.neo4j.models.Neo4jModel.UserModel;
import movies.spring.data.neo4j.models.PostgreModel.UserModelForPostgres;
import movies.spring.data.neo4j.repositories.repositoryForNeo4j.ActionRepository;
import movies.spring.data.neo4j.repositories.repositoryForNeo4j.ObjectRepository;
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
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class ParserController {
    private final ParsersService parsersService;
    private final JsonUtils jsonUtils;
    private final UserRepositoryNeo4j userRepositoryNeo4j;

    private final ActionRepository actionRepository;
    private final ObjectRepository objectRepository;
    public final UserRepositoryPostgres userRepositoryPostgres;
    private final UserService userService;
    private final Driver driver;
    public static final String JSON_URL = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json";
    public static final String NOTES1 = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes1.json";
    public static final String NOTES2 = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes2.json";
    public static final String NOTES3 = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes3.json";


    @Autowired
    public ParserController(ParsersService parsersService, JsonUtils jsonUtils, UserRepositoryNeo4j userRepositoryNeo4j, ActionRepository actionRepository, ObjectRepository objectRepository, UserRepositoryPostgres userRepository, UserService userService, Driver driver) {
        this.parsersService = parsersService;
        this.jsonUtils = jsonUtils;
        this.userRepositoryNeo4j = userRepositoryNeo4j;
        this.actionRepository = actionRepository;
        this.objectRepository = objectRepository;
        this.userRepositoryPostgres = userRepository;
        this.userService = userService;
        this.driver = driver;
    }

    @GetMapping(value = {"", "/"})
    Flux<UserModel> getMovies() {
        return userRepositoryNeo4j.findAll();
    }

    @PutMapping(value = {"add_user", "/"})
    Mono<UserModel> createOrUpdateMovie(@RequestBody UserModel newUser) {
        return userRepositoryNeo4j.save(newUser);
    }

    @PutMapping(value = {"add_action", "/"})
    Mono<ActionModel> createOrUpdateMovie(@RequestBody ActionModel newAction) {
        return actionRepository.save(newAction);
    }

    @PutMapping(value = {"add_object", "/"})
    Mono<ObjectModel> createOrUpdateMovie(@RequestBody ObjectModel newobjectModel) {
        return objectRepository.save(newobjectModel);
    }

    //добавляет новые узлы из json
    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createModelFromJSON(@RequestBody String text1) throws IOException, ParseException {
        System.out.println(text1);//TEXT
        parsersService.sendTextForParse(text1);
        parsersService.textInFiles(ParsersService.replaceId(), JSON_URL);
        ParsersService.replaceId();
        System.out.println(ParsersService.replaceId());//TEXT
        parsersService.textInFiles(ParsersService.addJsonArray(), JSON_URL);
        JsonUtils.separetJSON();
        JsonUtils.jsonChangeForPerson();

        //Notes1 Save In Postgres
        String result = new String(Files.readAllBytes(Paths.get(NOTES1)));
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

        //Notes2 Save In Postgres
        String result2 = new String(Files.readAllBytes(Paths.get(NOTES2)));
        ObjectMapper mapper2 = new ObjectMapper();
        List<HashMap<Integer, String>> people2 = (List<HashMap<Integer, String>>) mapper2.readValue(result2, List.class);
        for (int i = 0; i < people2.size(); i++) {
            HashMap<Integer, String> linkedHashMap = people2.get(i);
            UserModelForPostgres userModel2 = new UserModelForPostgres();
            userModel2.setUUID(linkedHashMap.get("UUID"));
            userModel2.setLocalID(Integer.valueOf(linkedHashMap.get("LocalID")));
            userModel2.setId(Integer.valueOf((linkedHashMap.get("ID"))));
            userModel2.setForm(linkedHashMap.get("FORM"));
            userModel2.setFeats(linkedHashMap.get("FEATS"));
            userModel2.setHead(linkedHashMap.get("HEAD"));
            userModel2.setDeprel(linkedHashMap.get("DEPREL"));
            userRepositoryPostgres.save(userModel2);
            System.out.println(people2.get(i));
        }
        //Notes3 Save In Postgres
        String result3 = new String(Files.readAllBytes(Paths.get(NOTES3)));
        ObjectMapper mapper3 = new ObjectMapper();
        List<HashMap<Integer, String>> people3 = (List<HashMap<Integer, String>>) mapper3.readValue(result3, List.class);
        for (int i = 0; i < people3.size(); i++) {
            HashMap<Integer, String> linkedHashMap = people3.get(i);
            UserModelForPostgres userModel3 = new UserModelForPostgres();
            userModel3.setUUID(linkedHashMap.get("UUID"));
            userModel3.setLocalID(Integer.valueOf(linkedHashMap.get("LocalID")));
            userModel3.setId(Integer.valueOf((linkedHashMap.get("ID"))));
            userModel3.setForm(linkedHashMap.get("FORM"));
            userModel3.setFeats(linkedHashMap.get("FEATS"));
            userModel3.setHead(linkedHashMap.get("HEAD"));
            userModel3.setDeprel(linkedHashMap.get("DEPREL"));
            userRepositoryPostgres.save(userModel3);
            System.out.println(people3.get(i));
        }

        parsersService.saveInNeo4j(NOTES1);
        parsersService.saveInNeo4j(NOTES2);
        parsersService.saveInNeo4j(NOTES3);

//        System.out.println("___________________");
//        List<String> list2ForNeo4j = parsersService.testArrayJsonParseAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes2.json");
//        System.out.println("___________________");
//        List<String> list3ForNeo4j = parsersService.testArrayJsonParseAfterPostgres("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes3.json");

    }
}
