package server.tasks;

import server.FruitComputeEngine;   // Gives access to the central fruit price map
import shared.Task;                 // The Task interface to allow RMI execution

import java.util.Map;               // For using the Map data structure

/**
 * Task to return a formatted list of all fruit-price entries.
 * Implements Task<String> because it returns the result as a human-readable string.
 */
public class ListFruits implements Task<String> {

    /**
     * The logic that runs when this task is executed on the RMI server.
     * It builds a multiline string showing all fruits and their prices.
     */
    @Override
    public String execute() {
        /// Access the shared fruit price table from the compute engine
        Map<String, Double> fruits = FruitComputeEngine.fruitPriceTable;

        /// If the map is empty, return a message indicating no fruits are stored
        if (fruits.isEmpty()) {
            return "🍌 No fruits stored.";
        }

        /// Start building a formatted string listing all fruits
        StringBuilder sb = new StringBuilder("🍇 Current Fruit Prices:\n");

        /// Loop through each fruit entry in the map and append to the string
        for (Map.Entry<String, Double> entry : fruits.entrySet()) {
            sb.append(" - ").append(entry.getKey())   // Fruit name
                    .append(": ").append(entry.getValue()) // Fruit price
                    .append("\n");
        }

        /// Return the final string
        return sb.toString();
    }
}
