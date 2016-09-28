package phuong.recommend.RecomendCentralManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISystemController extends Remote 
{
		// control functional components
		public void startDataIntegrator() throws RemoteException;
		public void startModelBuilder() throws RemoteException;
		public void startPredictiveEnginee() throws RemoteException;
		
		public void stopDataStreaming() throws RemoteException;
		public void stopModelBuilder() throws RemoteException;
		public void stopPredictiveEnginee() throws RemoteException; 
		
		public void startAllComponent() throws RemoteException;
		public void stopAllComponent() throws RemoteException;
		
		//Component status
		//  Status ID	Name
		//	1			Stopped
		//	2			Not Responding
		//  3			Running
		public int getDataStreamingStatus() throws RemoteException;
		public int getModelBuilderStatus() throws RemoteException;
		public int getPredictiveEngineeStatus() throws RemoteException;
		
		
		//Get component info 
		public String getPredictiveEngineeRemoteURL() throws RemoteException;
		public String getModelBuilderRemoteURL() throws RemoteException;
		public String getStreamingRemoteURL() throws RemoteException;
		
		//control predictive enge
		public void clearQueryCache() throws RemoteException;
		
		
		
		// Get statictis info of Predictive Engine
		public int getNumberOfQuery() throws RemoteException;
		public int getNumberOfQueryCurrentModel() throws RemoteException;
		public int getNumberOfCacheHit() throws RemoteException;
		public int getNumberOfCacheHitCurrentModel() throws RemoteException;
		
		// Get statictis info of Modeul Builder
		public String getLastModelPath() throws RemoteException;
		
		
		
		
		
		
		
}
