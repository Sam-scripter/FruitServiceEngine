package server.tasks;

import server.FruitComputeEngine;
import server.PersistenceUtil;
import shared.Task;

/**
 * Task to update the price of an existing fruit.
 */
public class UpdateFruitPrice implements Task<String> {
    private final String name;
    private final double newPrice;

    public UpdateFruitPrice(String name, double newPrice) {
        this.name = name;
        this.newPrice = newPrice;
    }

    @Override
    public String execute() {
        String key = name.toLowerCase();
        if (FruitComputeEngine.fruitPriceTable.containsKey(key)) {
            FruitComputeEngine.fruitPriceTable.put(key, newPrice);
            PersistenceUtil.save(FruitComputeEngine.fruitPriceTable);
            return "✅ Updated: " + name + " to " + newPrice;
        } else {
            return "⚠️ Fruit not found: " + name;
        }
    }
}
