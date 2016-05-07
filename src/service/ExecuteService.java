package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by sarleon on 16-5-6.
 */
public interface ExecuteService extends Remote {
    public String execute(String code,String param) throws RemoteException;


}
