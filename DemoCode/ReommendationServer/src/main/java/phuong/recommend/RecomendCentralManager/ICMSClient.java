package phuong.recommend.RecomendCentralManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICMSClient extends Remote 
{
	public String checkConnection() throws RemoteException;
	
	// control component
	public void startDataStreaming() throws RemoteException;
	public void startModelBuilder() throws RemoteException;
	public void startScoringService() throws RemoteException;
	public void stopDataStreaming() throws RemoteException;
	public void stopModelBuilder() throws RemoteException;
	public void stopScoringService() throws RemoteException; 
	
	public void stopAll() throws RemoteException;
	
	//Check component status
	//  Status ID	Name
	//	1			Stopped
	//	2			Running
	public int getDataStreamingStatus() throws RemoteException;
	public int getModelBuilderStatus() throws RemoteException;
	public int getScoringServiceStatus() throws RemoteException;
	
	
	
	
	// Get statictis info
	public int getNumberOfQuery() throws RemoteException;
	public int getNumberOfQueryCurrentModel() throws RemoteException;
	public int getNumberOfCacheHit() throws RemoteException;
	public int getNumberOfCacheHitCurrentModel() throws RemoteException;
	
	
	
	
	
	
}
