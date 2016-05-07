package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by sarleon on 16-5-6.
 */
public interface TestService extends Remote {
    public boolean echo() throws RemoteException;
}
