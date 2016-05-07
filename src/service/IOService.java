//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String[] readFileList(String userId)throws RemoteException;

	public boolean saveFileCopy(String filename,String userid,String filetext)throws RemoteException;

	public String readFileCopy(String username,String filename) throws RemoteException;

	public String[] readFileCopyList(String username,String fileName)throws RemoteException;
}
