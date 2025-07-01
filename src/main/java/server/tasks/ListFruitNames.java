package server.tasks;

import server.FruitComputeEngine;
import shared.Task;

import java.util.Set;
import java.util.TreeSet;

/**
 * Task to return the list of all fruit names (for dropdowns).
 */
public class ListFruitNames implements Task<Set<String>> {
    @Override
    public Set<String> execute() {
        return new TreeSet<>(FruitComputeEngine.fruitPriceTable.keySet());
    }
}
