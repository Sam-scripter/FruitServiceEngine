package server.tasks;

import server.FruitComputeEngine;     // Access to the shared in-memory fruit-price map
import server.PersistenceUtil;        // Utility for saving changes to persistent storage (JSON)
import shared.Task;                   // The generic interface for RMI-executable tasks

/**
 * Task to delete a fruit entry from the fruit price table.
 * Implements Task<String> so it can be executed remotely via RMI and return a message.
 */
public class DeleteFruitPrice implements Task<String> {

    private final String name;  // Name of the fruit to delete

    /**
     * Constructor initializes the fruit name to delete.
     */
    public DeleteFruitPrice(String name) {
        this.name = name;
    }

    /**
     * Executes the deletion logic when run on the RMI server.
     * It removes the fruit from the in-memory map and updates the JSON file.
     */
    @Override
    public String execute() {
        String key = name.toLowerCase();  // Normalize the fruit name to lowercase for consistent key lookup

        /// Attempt to remove the fruit from the price table
        if (FruitComputeEngine.fruitPriceTable.remove(key) != null) {
            /// If successfully removed, persist the updated map to the JSON file
            PersistenceUtil.save(FruitComputeEngine.fruitPriceTable);
            return "✅ Deleted: " + name;  // Return success message
        } else {
            return "⚠️ Fruit not found: " + name;  // Return error message if fruit wasn't in the table
        }
    }
}
