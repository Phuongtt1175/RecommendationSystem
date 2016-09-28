package phuong.recommend.configuration;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IComponentConfigurator extends Remote 
{
	public ComponentParameter[] getAllParameters() throws RemoteException;
	public ComponentParameter getParameter(String paraName) throws RemoteException;
	public void setParameter(ComponentParameter comPara) throws RemoteException;
	public void setParameter(String paraName, String paraValue) throws RemoteException;
}
