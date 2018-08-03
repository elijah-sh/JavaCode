package json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class ReadJSON {
    public static void main(String[] args) {
        try {
            JsonParser parser = new JsonParser();
            FileReader fr = new FileReader("code/json.txt");
            JsonObject object = (JsonObject) parser.parse(fr);

            String str1 = object.get("name").getAsString();
            Boolean bool = object.get("sure").getAsBoolean();
            Integer int1 = object.get("age").getAsInt();
            System.out.println("姓名："+str1);
            System.out.println("真实："+bool);
            System.out.println("年纪："+int1);

            JsonArray array = object.get("city").getAsJsonArray();
            for (int i = 0; i < array.size(); i++){
                System.out.println("------------");
                JsonObject subObject = array.get(i).getAsJsonObject();
                int id = subObject.get("id").getAsInt();
                String str2 = subObject.get("position").getAsString();
                int time  = subObject.get("time").getAsInt();
                System.out.println(id);
                System.out.println(str2);
                System.out.println(time);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
