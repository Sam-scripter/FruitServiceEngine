package server.tasks;

import server.FruitComputeEngine;   // Provides access to the central fruitPriceTable
import shared.Task;                 // The generic task interface to enable RMI execution

import java.util.Set;               // Set interface for storing unique fruit names
import java.util.TreeSet;           // TreeSet is used to return a sorted set

/**
 * Task to retrieve a sorted list of all fruit names from the price table.
 * This is typically used by the web client to populate dropdown menus.
 */
public class ListFruitNames implements Task<Set<String>> {

    /**
     * The logic executed on the RMI server.
     * It collects the keys (fruit names) from the price table and returns them as a sorted set.
     */
    @Override
    public Set<String> execute() {
        /// Get the set of fruit names from the map and return them as a sorted TreeSet
        return new TreeSet<>(FruitComputeEngine.fruitPriceTable.keySet());
    }
}
