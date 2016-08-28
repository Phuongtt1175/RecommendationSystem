package phuong.recommend.RecomendCentralManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICMSClient extends Remote 
{
	public String checkConnection() throws RemoteException;
	public void startDataStreaming() throws RemoteException;
	public void startModelBuilder() throws RemoteException;
	public void startScoringService() throws RemoteException;
	public void stopDataStreaming() throws RemoteException;
	public void stopModelBuilder() throws RemoteException;
	public void stopScoringService() throws RemoteException;
	
	public void stopAll() throws RemoteException;
	
}
