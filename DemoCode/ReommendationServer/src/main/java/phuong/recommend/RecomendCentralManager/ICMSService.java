package phuong.recommend.RecomendCentralManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICMSService extends Remote 
{
		// Check connection
		public boolean checkCMSConnection() throws RemoteException;
		public void alert(String sender, String message) throws RemoteException;
		public void log(String sender,String message) throws RemoteException;
	
		//For ModelBuilder
		public boolean registryModelBuilder(String RMI_URL) throws RemoteException;
		public void notifyModelBuilderShutdown() throws RemoteException;
		
		public void updateNewModel(String ModelName,String modelpath, String modelID) throws RemoteException;
		public void alertBuilModelFail(Exception ex, String message) throws RemoteException;
		
		//For DataIntegrator
		public boolean registryDataIntegrator(String name,String RMI_URL) throws RemoteException;
		public void notifyDataIntegratorShutdown() throws RemoteException;
		
		//For Predictive Engine
		public boolean registryPredictiveEngine(String name,String RMI_URL) throws RemoteException;
		public void notifyPredictiveEngineShutdown() throws RemoteException;
		public void alertQueryCacheFull() throws RemoteException;
}
