package json;

import com.google.gson.JsonArray;
import com.google.gson.*;

/**
 * Created by ShenShuaihu on 2018/7/18.
 * erroe
 */
public class CreatJSON {

    public static void main(String[] args) {

        JsonObject object = new JsonObject();
        object.addProperty("name","沈帅虎");
        object.addProperty("age",22);
        JsonArray array = new JsonArray();
        JsonObject city1 = new JsonObject();
        city1.addProperty("id",1);
        city1.addProperty("postion","许昌");
        city1.addProperty("Jane","xuchang");

        JsonObject city2 = new JsonObject();
        city2.addProperty("id",2);
        city2.addProperty("postion","郑州");
        city2.addProperty("Jane","zhengzhou");
        array.add(city1);
        array.add(city2);
        object.add("city",array);

        System.out.println(object.toString());
    }
}
