package server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to persist fruit prices to and from a JSON file.
 */
public class PersistenceUtil {
    private static final String FILE_NAME = "fruit_data.json";
    private static final Gson gson = new Gson();

    public static void save(Map<String, Double> data) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }

    public static Map<String, Double> load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new HashMap<>();

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, Double>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            System.err.println("Failed to load data: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
