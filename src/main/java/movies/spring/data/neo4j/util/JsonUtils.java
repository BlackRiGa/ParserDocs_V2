package movies.spring.data.neo4j.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JsonUtils {
    public static final String JSON_URL = "C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes4.json";

    public static void textInFilesInJSON(String text) {
        try (FileWriter writer = new FileWriter("C:\\Users\\i.sobol\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a3b22860-160e-4842-8073-89996fec4f70\\import\\file\\notes5.json", false)) {
            writer.write(text);
            writer.append('\n');
            writer.flush();
            System.out.println("Записал в json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String testArrayJsonParse() {
        try {
            // считывание файла JSON
            FileReader reader = new FileReader(JSON_URL);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            // получение массива
            JSONArray lang = (JSONArray) jsonObject.get("testJson");

            // берем элементы массива
            for (int i = 0; i < lang.size(); i++) {
                System.out.println("The " + i + " element of the array: " + lang.get(i));
            }
            Iterator i = lang.iterator();
            StringBuilder str = new StringBuilder();
            str.append("[");
            int flag = 0;
            // берем каждое значение из массива json отдельно
            while (i.hasNext()) {
                flag++;
                JSONObject innerObj = (JSONObject) i.next();
                if (innerObj.get("FEATS").equals("Npmsny") || innerObj.get("FEATS").equals("Vmip3s-a-e") || innerObj.get("FEATS").equals("Ncfsan")) {
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
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return "Error";
    }

    public static void jsonChangeForPerson() throws ParseException, IOException {
        // парсим полученный JSON и печатаем его на экран
        textInFilesInJSON(testArrayJsonParse());
    }
}