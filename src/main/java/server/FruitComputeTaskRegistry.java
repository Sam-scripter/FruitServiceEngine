package server;  // This class is part of the 'server' package

import shared.Compute;  // Interface used to remotely execute tasks
import shared.Task;     // Interface implemented by all executable task classes

import java.rmi.registry.LocateRegistry;  // Used to locate the RMI registry
import java.rmi.registry.Registry;        // Interface for interacting with the RMI registry

/**
 * Client-side utility class for connecting to the RMI registry
 * and submitting tasks to the remote Compute engine.
 *
 * This is used by the servlets to communicate with the RMI server.
 */
public class FruitComputeTaskRegistry {

    /// Connects to the RMI registry and sends the given task to the server for execution.

    public static <T> T sendTask(Task<T> task) throws Exception {
        /// Connect to the RMI registry on localhost at the default port 1099
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);

        /// Look up the remote object (FruitComputeEngine) by name
        Compute engine = (Compute) registry.lookup("FruitCompute");

        /// Send the task to the server and return the result
        return engine.executeTask(task);
    }
}
