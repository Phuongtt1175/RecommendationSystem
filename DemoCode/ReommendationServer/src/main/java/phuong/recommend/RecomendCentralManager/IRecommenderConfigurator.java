package phuong.recommend.RecomendCentralManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

import phuong.recommend.configuration.ComponentParameter;

public interface IRecommenderConfigurator extends Remote 
{
	// ********************************
	// 		CMS Configuration
	// ********************************
	public ComponentParameter[] getAllCMSParameter() throws RemoteException;
	public ComponentParameter getCMSParameter(String paraName) throws RemoteException;
	public void setCMSParameter(ComponentParameter comPara) throws RemoteException;
	
	// common use
	
	public String getDefaultSparkMaster() throws RemoteException;
	public void setDefaultSparkMaster(String sparkMaster) throws RemoteException;
	public void setSPARK_APP_JAR(String AppJarPath) throws RemoteException;
	
	
	
	
	
	// ********************************
	// Model Builder Configuration
	// ********************************
	
	
	
	
	
	// ********************************
	// Predictive Enginee Configuration
	// ********************************
	
	
	// ********************************
	// Data Integrator Configuration
	// ********************************
		
	
}
