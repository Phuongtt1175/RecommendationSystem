package phuong.recommend.ModelBuilder;

import java.rmi.Remote;
import java.rmi.RemoteException;

import phuong.recommend.configuration.ComponentParameter;

public interface IMBModuleController extends Remote 
{
	//ping
	public boolean checkConnection() throws RemoteException;
	
	public void stop() throws RemoteException;
	//  ModelBUilder
	public int getNumberOfModelBuilder() throws RemoteException;
	public String[] getModelBuiderList() throws RemoteException;
	
	
	
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
		
}
