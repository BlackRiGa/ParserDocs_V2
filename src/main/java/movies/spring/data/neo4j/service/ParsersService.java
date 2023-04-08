package movies.spring.data.neo4j.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import movies.spring.data.neo4j.models.Neo4jModel.ActionModel;
import movies.spring.data.neo4j.models.Neo4jModel.ObjectModel;
import movies.spring.data.neo4j.models.Neo4jModel.UserModel;
import movies.spring.data.neo4j.repositories.repositoryForNeo4j.UserRepositoryNeo4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class ParsersService {
    public static final String JSON_URL = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json";

    private final UserRepositoryNeo4j wordParserRepository;
    private final Neo4jClient neo4jClient;
    private final DatabaseSelectionProvider databaseSelectionProvider;

    @Autowired
    public ParsersService(UserRepositoryNeo4j wordParserRepository, Neo4jClient neo4jClient, DatabaseSelectionProvider databaseSelectionProvider) {
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
            textInFiles(WebClient.create().post().uri("http://localhost:2000/parse").bodyValue(text).exchange().block().bodyToMono(String.class).block(), JSON_URL);
            System.out.println(WebClient.create().post().uri("http://localhost:2000/parse").bodyValue(text).exchange().block().bodyToMono(String.class).block());
            return "200";
        } catch (Exception e) {
            System.out.println("Ошибка");
            return e.toString();
        }
    }

    public static void textInFiles(String text, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write(text);
            writer.append('\n');
            writer.flush();
            System.out.println("Записал в json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String addJsonArrayAfterPostgres(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
        StringBuilder str = new StringBuilder();
        int c;
        str.append("{\"testJson\":");
        while ((c = reader.read()) != -1) {
            str.append((char) c);
        }
        str.append("}");
        System.out.println(str);
        return str.toString();
    }


    public static List<String> testArrayJsonParseAfterPostgres(String filePath) throws IOException, ParseException {
        // считывание файла JSON
        FileReader reader = new FileReader(filePath);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        // получение массива
        JSONArray lang = (JSONArray) jsonObject.get("testJson");

        List<String> listArrayParse = new ArrayList<>();
        // берем элементы массива
        for (int i = 0; i < lang.size(); i++) {
            System.out.println("The " + i + " element of the array1: " + lang.get(i));
            listArrayParse.add(lang.get(i).toString());
        }
        return listArrayParse;
    }

    public static void saveInNeo4j(String path) throws IOException, ParseException {
        String list = addJsonArrayAfterPostgres(path);
        textInFiles(list, path);
        List<String> list1ForNeo4j = testArrayJsonParseAfterPostgres(path);
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();
        for (int f = 0; f < list1ForNeo4j.size(); f++) {
            if (list1ForNeo4j.get(f).contains("Npmsny")) {
                JSONObject json = (JSONObject) parser.parse(list1ForNeo4j.get(f));
                UserModel userModel = new UserModel();
                userModel.setUuid((String) json.get("UUID"));
                userModel.setForm((String) json.get("FORM"));
                userModel.setHead((String) json.get("HEAD"));
                userModel.setDeprel((String) json.get("DEPREL"));
                userModel.setId((String) json.get("ID"));
                userModel.setFeats((String) json.get("FEATS"));
                userModel.setLocalID((String) json.get("LocalID"));
                String jsonString1 = mapper.writeValueAsString(userModel);
                WebClient.create().put().uri("http://localhost:8080/user/add_user/").bodyValue(jsonString1).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).exchange().block().bodyToMono(String.class).block();
            }
            if (list1ForNeo4j.get(f).contains("Vmip3s-a-e") || list1ForNeo4j.get(f).contains("Vmip3s-m-e") || list1ForNeo4j.get(f).contains("Vmn----a-p")) {
                JSONObject json = (JSONObject) parser.parse(list1ForNeo4j.get(f));
                ActionModel userModel = new ActionModel();
                userModel.setUuid((String) json.get("UUID"));
                userModel.setForm((String) json.get("FORM"));
                userModel.setHead((String) json.get("HEAD"));
                userModel.setDeprel((String) json.get("DEPREL"));
                userModel.setId((String) json.get("ID"));
                userModel.setFeats((String) json.get("FEATS"));
                userModel.setLocalID((String) json.get("LocalID"));
                String jsonString2 = mapper.writeValueAsString(userModel);
                WebClient.create().put().uri("http://localhost:8080/user/add_action/").bodyValue(jsonString2).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).exchange().block().bodyToMono(String.class).block();
            }
            if (list1ForNeo4j.get(f).contains("Ncfsan")) {
                JSONObject json = (JSONObject) parser.parse(list1ForNeo4j.get(f));
                ObjectModel userModel = new ObjectModel();
                userModel.setUuid((String) json.get("UUID"));
                userModel.setForm((String) json.get("FORM"));
                userModel.setHead((String) json.get("HEAD"));
                userModel.setDeprel((String) json.get("DEPREL"));
                userModel.setId((String) json.get("ID"));
                userModel.setFeats((String) json.get("FEATS"));
                userModel.setLocalID((String) json.get("LocalID"));
                String jsonString3 = mapper.writeValueAsString(userModel);
                WebClient.create().put().uri("http://localhost:8080/user/add_object/").bodyValue(jsonString3).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).exchange().block().bodyToMono(String.class).block();
            }
        }
    }
}
