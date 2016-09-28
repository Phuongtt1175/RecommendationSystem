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
	public String getSPARK_HOME() throws RemoteException;
	public void setSPARK_HOME() throws RemoteException;
	
	
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
	
	
	// ********************************
	// Model Builder Configuration
	// ********************************
	
	// Model Builder manager
	public ComponentParameter getModelBuilderManagerParameter(String paraName) throws RemoteException;
	public void setModelManagerParameter(String para, String value) throws RemoteException;
	public void setModelManagerParameter(ComponentParameter comPara) throws RemoteException;
	
	// ModeulBuilder
	public ComponentParameter getBuilderParameter(int builderIndex,String paraName) throws RemoteException;
	public void setBuilderParameter(int builderIndex,String para, String value) throws RemoteException;
	public void setBuilderParameter(int builderIndex,ComponentParameter comPara) throws RemoteException;
	

	

	
	// ********************************
	// Data Integrator Configuration
	// ********************************
		
	// DI manager
	public ComponentParameter getDIManagerParameter(String paraName) throws RemoteException;
	public void setDIManagerParameter(String para, String value) throws RemoteException;
	public void setDIManagerParameter(ComponentParameter comPara) throws RemoteException;
	
	// Data Integrator
	public ComponentParameter getDIParameter(int builderIndex,String paraName) throws RemoteException;
	public void setDIParameter(int builderIndex,String para, String value) throws RemoteException;
	public void setDIParameter(int builderIndex,ComponentParameter comPara) throws RemoteException;
}
