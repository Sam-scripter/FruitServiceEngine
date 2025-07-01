package server.tasks;

import server.FruitComputeEngine;
import shared.Task;

/**
 * Task to calculate the cost of a given quantity of fruit.
 */
public class CalFruitCost implements Task<String> {
    private final String name;
    private final double quantity;

    public CalFruitCost(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String execute() {
        String key = name.toLowerCase();
        Double price = FruitComputeEngine.fruitPriceTable.get(key);
        if (price != null) {
            double cost = price * quantity;
            return "💰 Cost of " + quantity + " " + name + "(s): " + cost;
        } else {
            return "⚠️ Fruit not found: " + name;
        }
    }
}
