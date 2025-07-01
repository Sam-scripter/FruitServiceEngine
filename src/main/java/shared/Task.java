package shared;  // This is in the shared package, accessible to both server and client sides

import java.io.Serializable;  // Required for any object that needs to be sent over RMI

/**
 * Represents a generic task that can be executed by the RMI server.
 *
 * All task classes must implement this interface to be processed by the RMI server.
 * It must extend Serializable because RMI transfers objects over the network.
 *
 * @param <T> the type of result the task returns when executed
 */
public interface Task<T> extends Serializable {

    /**
     * The method that contains the logic for this task.
     * It will be run on the server side once the task is received via RMI.
     *
     * @return the result of executing the task (type T)
     */
    T execute();
}
