package server.tasks;

import server.FruitComputeEngine;
import server.PersistenceUtil;
import shared.Task;

/**
 * Task to delete a fruit entry from the table.
 */
public class DeleteFruitPrice implements Task<String> {
    private final String name;

    public DeleteFruitPrice(String name) {
        this.name = name;
    }

    @Override
    public String execute() {
        String key = name.toLowerCase();
        if (FruitComputeEngine.fruitPriceTable.remove(key) != null) {
            PersistenceUtil.save(FruitComputeEngine.fruitPriceTable);
            return "✅ Deleted: " + name;
        } else {
            return "⚠️ Fruit not found: " + name;
        }
    }
}
