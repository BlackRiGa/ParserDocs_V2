package movies.spring.data.neo4j.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class JsonUtils {
    public static final String JSON_URL = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json";

    public static void textInFilesInJSON(String text, Integer indexJsonFile) {
        String JSON_URL_USER = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes" + indexJsonFile + ".json";
        try (FileWriter writer = new FileWriter(JSON_URL_USER, false)) {
            System.out.println("text message - " + text);
            writer.write(text);
            writer.append('\n');
            writer.flush();
            System.out.println("Записал в json" + indexJsonFile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<String> testArrayJsonParse() throws IOException, ParseException {
        // считывание файла JSON
        FileReader reader = new FileReader(JSON_URL);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        // получение массива
        JSONArray lang = (JSONArray) jsonObject.get("testJson");
        JSONArray lang2 = (JSONArray) jsonObject.get("testJson2");
        JSONArray lang3 = (JSONArray) jsonObject.get("testJson3");

        List<String> listArrayParse = new ArrayList<>();
        // берем элементы массива
        for (int i = 0; i < lang.size(); i++) {
            System.out.println("The " + i + " element of the array1: " + lang.get(i));
        }
        for (int i = 0; i < lang2.size(); i++) {
            System.out.println("The " + i + " element of the array2: " + lang2.get(i));
        }
        for (int i = 0; i < lang3.size(); i++) {
            System.out.println("The " + i + " element of the array3: " + lang3.get(i));
        }
        listArrayParse.add(ArrayJsonParse(lang, 1));
        listArrayParse.add(ArrayJsonParse(lang2, 2));
        listArrayParse.add(ArrayJsonParse(lang3, 3));
        for (int j = 0; j < 3; j++) {
            System.out.println(listArrayParse.get(j));
        }
        return listArrayParse;
    }

    public static String ArrayJsonParse(JSONArray jsonArray, Integer indexFilesWithJsonArray) {
        Iterator i = jsonArray.iterator();
        StringBuilder str = new StringBuilder();
        str.append("[");
        int flag = 0;
        // берем каждое значение из массива json отдельно
        while (i.hasNext()) {
            flag++;
            JSONObject innerObj = (JSONObject) i.next();
            if (innerObj.get("FEATS").equals("Npmsny") || innerObj.get("FEATS").equals("Vmip3s-a-e") || innerObj.get("FEATS").equals("Ncfsan") || innerObj.get("FEATS").equals("Vmip3s-m-e") || innerObj.get("FEATS").equals("Vmn----a-p")){
                str.append("{\"LocalID\":" + "\"").append(innerObj.get("LocalID")).append("\",").append("\"FORM\":").append("\"").append(innerObj.get("FORM")).append("\",").append("\"ID\":").append("\"").append(innerObj.get("ID")).append("\",").append("\"HEAD\":").append("\"").append(innerObj.get("HEAD")).append("\",").append("\"FEATS\":").append("\"").append(innerObj.get("FEATS")).append("\",").append("\"DEPREL\":").append("\"").append(innerObj.get("DEPREL")).append("\"");
                if (flag == 7) {
                    str.append("}");
                } else {
                    str.append("},");
                }
            } else {
                System.out.println("-");
            }
        }
        str.append("]");
        String newString = str.toString().replace("\"},]", "\"}]");
        System.out.println(newString);
        return newString;
    }

    public static List<String> separetJSON() throws IOException, ParseException {
        // считывание файла JSON
        FileReader reader2 = new FileReader(JSON_URL);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader2);

        // получение массива
        JSONArray lang = (JSONArray) jsonObject.get("testJson");
        JSONArray lang2 = (JSONArray) jsonObject.get("testJson2");
        JSONArray lang3 = (JSONArray) jsonObject.get("testJson3");
        // берем элементы массива
        System.out.println(lang + " - lang");
        System.out.println(lang2 + " - lang2");
        System.out.println(lang3 + " - lang2");
        List<String> listSeparateJson = new ArrayList<>();
        listSeparateJson.add(arrayInString(lang, 1));
        listSeparateJson.add(arrayInString(lang2, 2));
        listSeparateJson.add(arrayInString(lang3, 3));
        return listSeparateJson;
    }

    public static String arrayInString(JSONArray jsonArray, Integer indexFilesWithJsonArray) {
        Iterator i2 = jsonArray.iterator();
        StringBuilder str = new StringBuilder();
        str.append("[");
        int flag = 0;
        // берем каждое значение из массива json отдельно
        while (i2.hasNext()) {
            flag++;
            JSONObject innerObj = (JSONObject) i2.next();
            if (!innerObj.get("LEMMA").equals(".")) {
                str.append("{\"LocalID\":" + "\"").append(innerObj.get("LocalID")).append("\",").append("{\"ID\":" + "\"").append(innerObj.get("ID")).append("\",").append("\"FORM\":").append("\"").append(innerObj.get("FORM")).append("\",").append("\"LEMMA\":").append("\"").append(innerObj.get("LEMMA")).append("\",").append("\"HEAD\":").append("\"").append(innerObj.get("HEAD")).append("\",").append("\"POSTAG\":").append("\"").append(innerObj.get("POSTAG")).append("\",").append("\"FEATS\":").append("\"").append(innerObj.get("FEATS")).append("\",").append("\"DEPREL\":").append("\"").append(innerObj.get("DEPREL")).append("\"");
                if (flag == 7) {
                    str.append("}");
                } else {
                    str.append("},");
                }
            } else {
                break;
            }
        }
        str.append("]");
        String newString = str.toString().replace("\"},]", "\"}]");
        System.out.println("новая строка separateJSON   " + newString);
        textInFilesInJSON(newString, indexFilesWithJsonArray);
        return newString;
    }


    public static void jsonChangeForPerson() throws IOException, ParseException {
        // парсим полученный JSON и печатаем его на экран
        List<String> listArray = testArrayJsonParse();
        String changeLocalId;
        int min = 0;
        int max = 100;
        for (int i = 0; i < listArray.size(); i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
            changeLocalId = listArray.get(i).replace("{\"LocalID\":\"", "{\"LocalID\":\""+randomNum);
            textInFilesInJSON(changeLocalId, i + 1);
        }
    }
}