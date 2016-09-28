package phuong.recommend.ModelBuilder;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IModelBuilderService extends Remote 
{
	//ping
	public boolean checkConnection() throws RemoteException;
	
	public String reBuildModel() throws RemoteException;
	public String getCurrentModelPath() throws RemoteException;
}
