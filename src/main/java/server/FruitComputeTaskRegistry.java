package server;

import shared.Compute;
import shared.Task;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client-side utility to connect to the RMI registry and execute tasks on the server.
 */
public class FruitComputeTaskRegistry {
    public static <T> T sendTask(Task<T> task) throws Exception {
        // Connect to the registry
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        Compute engine = (Compute) registry.lookup("FruitCompute");

        // Execute the task remotely
        return engine.executeTask(task);
    }
}
