package phuong.recommend.ModelBuilder;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IModelBuilderAPI extends Remote 
{
	public String checkConnection() throws RemoteException;
	public String reBuildModel() throws RemoteException;
	public String getCurrentModelPath() throws RemoteException;

	
}
