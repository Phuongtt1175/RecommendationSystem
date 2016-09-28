package phuong.recommend.Scoring;

import java.rmi.Remote;
import java.rmi.RemoteException;

import phuong.recommend.configuration.ComponentParameter;

public interface IPredictiveModuleController extends Remote 
{
	// ping
	public boolean checkConnection() throws RemoteException;
	
	// Control
	public void stop() throws RemoteException;
	
	//  predictor
	public int getNumberOfPredictor() throws RemoteException;
	public String[] getPredictorList() throws RemoteException;
	

	// ********************************
	// Predictive Enginee Configuration
	// ********************************
	
	// predictor manager
	public ComponentParameter getPredictorManagerParameter(String paraName) throws RemoteException;
	public void setPredictorManagerParameter(String para, String value) throws RemoteException;
	public void setPredictorManagerParameter(ComponentParameter comPara) throws RemoteException;
	
	// Query Cache
	public ComponentParameter getQueryCacheParameter(String paraName) throws RemoteException;
	public void setQueryCacheParameter(String para, String value) throws RemoteException;
	public void setQueryCacheParameter(ComponentParameter comPara) throws RemoteException;
	
	public long getQueryCacheSize() throws RemoteException;
	public long setQueryCacheSize() throws RemoteException;
	
	// Predictor
	public ComponentParameter getPredictorParameter(int predictorIndex,String paraName) throws RemoteException;
	public void setPredictorParameter(int predictorIndex,String para, String value) throws RemoteException;
	public void setPredictorParameter(int predictorIndex,ComponentParameter comPara) throws RemoteException;
	
	
	
	
	
	//*************************************************
	// 			FROM ISystemController
	//*************************************************
	//control predictive enge
	public void clearQueryCache() throws RemoteException;
			
			
			
	// Get statictis info of Predictive Engine
	public int getNumberOfQuery() throws RemoteException;
	public int getNumberOfQueryCurrentModel() throws RemoteException;
	public int getNumberOfCacheHit() throws RemoteException;
	public int getNumberOfCacheHitCurrentModel() throws RemoteException;

}
