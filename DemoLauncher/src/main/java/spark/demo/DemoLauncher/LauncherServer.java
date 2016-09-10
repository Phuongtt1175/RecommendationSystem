package spark.demo.DemoLauncher;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class LauncherServer implements ILauncherServer {

	static Registry RMIRegistry;
	static String RMIName="LaucherServer";
	static String LOCAL_IP="192.168.11.107";
	static LauncherServer lcsoj;
	public String ping(String s) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void startRMILauncher() throws RemoteException, MalformedURLException{
		try 
        {
        	RMIRegistry = LocateRegistry.createRegistry(1099);
        	System.out.println("Create new RMI Registry at port 1099");
		} 
        catch (RemoteException e) 
        {
        	try 
        	{
				RMIRegistry = LocateRegistry.getRegistry(1099);
			} catch (RemoteException e1) {e1.printStackTrace();	}
        	System.out.println("Using current RMI Registry at port 1099");
        }
        
        lcsoj = new LauncherServer();
        Naming.rebind("//"+LOCAL_IP+"/"+RMIName, lcsoj);

	}

	public void sendMess(String s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
}
