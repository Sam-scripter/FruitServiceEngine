package server;  // Part of the server package where utility classes are stored

import com.google.gson.Gson;                    // Gson is used to convert Java objects to/from JSON
import com.google.gson.reflect.TypeToken;       // Used for representing generic types (like Map<String, Double>)

import java.io.*;                               // I/O classes for file reading/writing
import java.lang.reflect.Type;                  // Used to capture generic type information at runtime
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to persist fruit prices to and from a JSON file.
 * This handles saving the in-memory fruit price map to disk, and reloading it at startup.
 */
public class PersistenceUtil {

    // The name of the file where fruit data will be saved
    private static final String FILE_NAME = "fruit_data.json";

    // A single Gson instance used for both serialization and deserialization
    private static final Gson gson = new Gson();

    /**
     * Saves the provided fruit data map to a JSON file.
     * This ensures that data added at runtime is not lost when the app is stopped.
     */
    public static void save(Map<String, Double> data) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            /// Convert the map into JSON and write it to the file
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }

    /**
     * Loads fruit data from the JSON file into memory.
     * If the file doesn't exist or an error occurs, returns an empty map.
     */
    public static Map<String, Double> load() {
        File file = new File(FILE_NAME);  // Reference to the file

        /// If the file does not exist, return an empty map
        if (!file.exists()) return new HashMap<>();

        try (Reader reader = new FileReader(file)) {
            /// Specify the expected type: Map<String, Double>
            Type type = new TypeToken<Map<String, Double>>() {}.getType();

            /// Read and parse the JSON into a Java Map
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            System.err.println("Failed to load data: " + e.getMessage());
            return new HashMap<>();  // Return empty map if reading fails
        }
    }
}
