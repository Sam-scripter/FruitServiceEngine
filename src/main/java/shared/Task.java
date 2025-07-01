package shared;

import java.io.Serializable;

/**
 * Represents a generic task that can be executed by the RMI server.
 * It must be serializable so that it can be sent over the network.
 */
public interface Task<T> extends Serializable {
    T execute();
}
