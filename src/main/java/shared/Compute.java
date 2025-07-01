package shared;  // Part of the shared package, which is used by both client and server

import java.rmi.Remote;          // Marks this as a remote interface for RMI
import java.rmi.RemoteException; // Required for any method that might be called remotely

/**
 * Remote interface that defines the method a client can call on the RMI server.
 * This is the core of the RMI mechanism: clients submit tasks and receive results.
 */
public interface Compute extends Remote {

    /**
     * Executes a given Task on the RMI server.
     */
    <T> T executeTask(Task<T> t) throws RemoteException;
}
