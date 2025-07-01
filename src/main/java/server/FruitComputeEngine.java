package server;

import shared.Compute;  // Interface defining the remote execution method
import shared.Task;     // Interface for tasks that can be executed remotely

import java.rmi.RemoteException;                  // Exception for RMI-related issues
import java.rmi.registry.LocateRegistry;          // Used to locate or create the RMI registry
import java.rmi.registry.Registry;                // Reference to the RMI registry
import java.rmi.server.UnicastRemoteObject;       // Allows exporting this server as a remote object
import java.util.HashMap;
import java.util.Map;

/**
 * The RMI server that handles fruit pricing tasks.
 * It registers itself on the RMI registry and provides a generic interface to execute tasks.
 */
public class FruitComputeEngine extends UnicastRemoteObject implements Compute {

    /// Shared in-memory fruit-price table
    /// Acts as the database for storing fruit name and price
    public static final Map<String, Double> fruitPriceTable = new HashMap<>();

    /// Constructor exports the object for remote invocation

    protected FruitComputeEngine() throws RemoteException {
        super();  // Export this object as a remote object
    }

    /**
     * Executes a generic task submitted via RMI.
     * Any class implementing Task<T> can be passed in and run here.
     */
    @Override
    public synchronized <T> T executeTask(Task<T> t) throws RemoteException {
        return t.execute();  // Run the task and return its result
    }

    /**
     * Main method: entry point for starting the RMI server.
     * It loads stored fruit data, connects to the RMI registry, and binds this engine.
     */
    public static void main(String[] args) {
        try {
            /// Load fruits from JSON file into memory
            fruitPriceTable.putAll(PersistenceUtil.load());
            System.out.println("✅ Loaded fruits from file.");

            /// Access the existing RMI registry (assumes `rmiregistry` is already running)
            System.out.println("Using existing RMI registry.");

            /// Create and export this compute engine
            FruitComputeEngine engine = new FruitComputeEngine();

            /// Get reference to the registry
            Registry registry = LocateRegistry.getRegistry();

            /// Bind this engine to the name "FruitCompute" for lookup by clients
            registry.rebind("FruitCompute", engine);
            System.out.println("✅ FruitComputeEngine is ready and bound to 'FruitCompute'.");

        } catch (Exception e) {
            System.err.println("❌ Failed to start FruitComputeEngine:");
            e.printStackTrace();  // Print detailed error to server logs
        }
    }
}
