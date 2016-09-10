package spark.demo.DemoLauncher;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILauncherServer extends Remote 
{
	public String ping(String s) throws RemoteException;
}
