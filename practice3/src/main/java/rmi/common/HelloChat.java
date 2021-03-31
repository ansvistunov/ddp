package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author : Alex
 * @created : 31.03.2021, среда
 **/
public interface HelloChat extends Remote {
    void message(String from, String message) throws RemoteException;
}
