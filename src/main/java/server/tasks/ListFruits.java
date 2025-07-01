package server.tasks;

import server.FruitComputeEngine;
import shared.Task;

import java.util.Map;

/**
 * Task to list all current fruit entries.
 */
public class ListFruits implements Task<String> {
    @Override
    public String execute() {
        Map<String, Double> fruits = FruitComputeEngine.fruitPriceTable;
        if (fruits.isEmpty()) {
            return "🍌 No fruits stored.";
        }

        StringBuilder sb = new StringBuilder("🍇 Current Fruit Prices:\n");
        for (Map.Entry<String, Double> entry : fruits.entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
