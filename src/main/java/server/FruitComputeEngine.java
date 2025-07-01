package server;

import shared.Compute;
import shared.Task;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * The RMI server that handles fruit pricing tasks.
 * It registers itself on the RMI registry and provides a generic interface to execute tasks.
 */
public class FruitComputeEngine extends UnicastRemoteObject implements Compute {

    // In-memory fruit-price table shared across tasks
    public static final Map<String, Double> fruitPriceTable = new HashMap<>();

    protected FruitComputeEngine() throws RemoteException {
        super();
    }

    /**
     * Executes any task submitted through the RMI interface.
     * @param t the task to execute
     * @return the result of the task
     * @throws RemoteException in case of network issues
     */
    @Override
    public synchronized <T> T executeTask(Task<T> t) throws RemoteException {
        return t.execute();
    }

    public static void main(String[] args) {
        try {
            // Load fruit data from JSON file before registration
            fruitPriceTable.putAll(PersistenceUtil.load());
            System.out.println("✅ Loaded fruits from file.");

            // Optionally start the registry here (uncomment if needed)
            // LocateRegistry.createRegistry(1099);

            // Get registry and register the compute engine
            System.out.println("Using existing RMI registry.");
            FruitComputeEngine engine = new FruitComputeEngine();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("FruitCompute", engine);

            System.out.println("✅ FruitComputeEngine is ready and bound to 'FruitCompute'.");

        } catch (Exception e) {
            System.err.println("❌ Failed to start FruitComputeEngine:");
            e.printStackTrace();
        }
    }
}
