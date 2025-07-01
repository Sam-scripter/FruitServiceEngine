package server.tasks;

import server.FruitComputeEngine;
import server.PersistenceUtil;
import shared.Task;

/**
 * Task to add a new fruit-price entry to the table.
 */
public class AddFruitPrice implements Task<String> {
    private final String name;
    private final double price;

    public AddFruitPrice(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String execute() {
        FruitComputeEngine.fruitPriceTable.put(name.toLowerCase(), price);
        PersistenceUtil.save(FruitComputeEngine.fruitPriceTable);
        return "✅ Added: " + name + " @ " + price;
    }
}
