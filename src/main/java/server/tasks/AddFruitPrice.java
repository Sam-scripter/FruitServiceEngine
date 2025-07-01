package server.tasks;

import server.FruitComputeEngine;
import server.PersistenceUtil;
import shared.Task;

/// Task to add a new fruit-price entry to the table.
public class AddFruitPrice implements Task<String> {
    private final String name;  // The name of the fruit to add
    private final double price; // The price of the fruit

    /// Constructor initializes the fruit name and price.
    public AddFruitPrice(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * The logic that is executed remotely on the RMI server.
     * Adds the fruit and its price to the shared map and persists the updated map to file.
     */
    @Override
    public String execute() {
        /// Add the fruit to the central price table (convert to lowercase to maintain consistency)
        FruitComputeEngine.fruitPriceTable.put(name.toLowerCase(), price);

        /// Save the updated map to the JSON file
        PersistenceUtil.save(FruitComputeEngine.fruitPriceTable);

        /// Return a success message
        return "✅ Added: " + name + " @ " + price;
    }
}
