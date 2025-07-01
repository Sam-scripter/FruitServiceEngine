package server.tasks;

import server.FruitComputeEngine;     // Access to the shared fruit-price map
import server.PersistenceUtil;        // Utility for persisting changes to disk
import shared.Task;                   // Generic RMI Task interface that this class implements

/**
 * Task to update the price of an existing fruit in the price table.
 * Implements Task<String> to return a confirmation or error message as a String.
 */
public class UpdateFruitPrice implements Task<String> {

    private final String name;       // Name of the fruit to update
    private final double newPrice;   // New price to set

    /// Constructor to initialize the fruit name and new price.
    public UpdateFruitPrice(String name, double newPrice) {
        this.name = name;
        this.newPrice = newPrice;
    }

    /**
     * This method is executed on the RMI server.
     * It updates the price of the specified fruit in the in-memory map
     * and saves the updated state to disk.
     */
    @Override
    public String execute() {
        String key = name.toLowerCase();  // Normalize to lowercase for consistent key usage

        /// Check if the fruit exists in the price table
        if (FruitComputeEngine.fruitPriceTable.containsKey(key)) {
            /// Update the price in the map
            FruitComputeEngine.fruitPriceTable.put(key, newPrice);

            /// Persist the updated map to the JSON file
            PersistenceUtil.save(FruitComputeEngine.fruitPriceTable);

            /// Return confirmation message
            return "✅ Updated: " + name + " to " + newPrice;
        } else {
            /// Fruit doesn't exist in the table
            return "⚠️ Fruit not found: " + name;
        }
    }
}
