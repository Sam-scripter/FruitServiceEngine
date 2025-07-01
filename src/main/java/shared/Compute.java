package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface that defines the method a client can call on the RMI server.
 * The method accepts a Task and returns a result.
 */
public interface Compute extends Remote {
    <T> T executeTask(Task<T> t) throws RemoteException;
}
