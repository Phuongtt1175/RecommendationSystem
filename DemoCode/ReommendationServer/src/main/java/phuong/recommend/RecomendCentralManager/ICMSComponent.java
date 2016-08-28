package phuong.recommend.RecomendCentralManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICMSComponent extends Remote 
{
	//For All
	
	//For ModelBuilder
	public boolean registryModelBuilder(String RMI_URL) throws RemoteException;
	public void updateNewModel(String modelpath, String modelID) throws RemoteException;
	
	
	//For Streaming
	public boolean registryDataStream(String RMI_URL) throws RemoteException;
	
	//For Scoring
	public boolean registryScoring(String RMI_URL) throws RemoteException;
	
}
