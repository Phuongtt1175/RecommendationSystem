package phuong.recommend.Scoring;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISocringControl extends Remote 
{
	//CACHE
	public int getCacheSize() throws RemoteException;
	public void cleanCache() throws RemoteException;
	
	//MODEL
	public int refeshModel(String ModelPath) throws RemoteException;
	
	
	//Control
	public void stopScoringService() throws RemoteException;
	public String checkConnection() throws RemoteException;
	
}
