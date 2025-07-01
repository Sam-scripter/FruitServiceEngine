package server.tasks;

import server.FruitComputeEngine;  // Imports the class containing the shared fruitPriceTable
import shared.Task;                // Imports the generic Task interface used for RMI execution

/**
 * Task to calculate the cost of purchasing a given quantity of a specified fruit.
 * Implements Task<String> so that it can be executed remotely and return a textual result.
 */
public class CalFruitCost implements Task<String> {

    // Fields to hold the fruit name and quantity requested
    private final String name;
    private final double quantity;

    /// Constructor to initialize the fruit name and quantity.

    public CalFruitCost(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * This method is called on the RMI server to perform the cost calculation.
     * It retrieves the unit price from the shared map and computes the total cost.
     * @return A string showing the calculated cost or a warning if the fruit doesn't exist
     */
    @Override
    public String execute() {
        String key = name.toLowerCase();  // Normalize fruit name to lowercase for consistency
        Double price = FruitComputeEngine.fruitPriceTable.get(key);  // Look up price in the map

        if (price != null) {
            double cost = price * quantity;  // Calculate total cost
            return "💰 Cost of " + quantity + " " + name + "(s): " + cost;  // Return result
        } else {
            return "⚠️ Fruit not found: " + name;  // Handle case where fruit isn't in the map
        }
    }
}
