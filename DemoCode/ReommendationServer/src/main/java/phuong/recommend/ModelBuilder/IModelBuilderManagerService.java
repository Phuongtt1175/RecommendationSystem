package phuong.recommend.ModelBuilder;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IModelBuilderManagerService extends Remote 
{
	//ping
	public boolean checkConnection() throws RemoteException;
	
	public void updateNewModel(String ModelName,String modelpath, String modelID) throws RemoteException;
	public void alertBuilModelFail(Exception ex, String message) throws RemoteException;

}
