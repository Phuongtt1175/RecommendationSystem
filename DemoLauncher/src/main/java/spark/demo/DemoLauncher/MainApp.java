package spark.demo.DemoLauncher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

/**
 * Hello world!
 *
 */
public class MainApp  extends UnicastRemoteObject implements ILauncherServer
{
    protected MainApp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

    
    static Registry RMIRegistry;
	static String RMIName="LaucherServer";
	static String LOCAL_IP="localhost";
	static LauncherServer lcsoj;
	
	
	public static void main( String[] args ) throws RemoteException, MalformedURLException
    {
		
		
		//START RMI
		
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
		
		
		
		
		//LAUNCH SPARK APP
		try 
		{
			String SPARK_MASTER="local[2]";
			SparkAppHandle handle = new SparkLauncher()
					.setSparkHome(System.getenv("SPARK_HOME"))
			         .setAppResource("DemoLauncher-0.0.1-SNAPSHOT.jar")
			         .setMainClass("spark.demo.SparkApp.SparkApp")
			         .setMaster(SPARK_MASTER)
			         .setConf(SparkLauncher.DRIVER_MEMORY, "1g")
			         .startApplication();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public String ping(String s) throws RemoteException 
	{
		System.out.println("Spark app connected: " + s);
		return "Hello Spark App";
	}

	public void sendMess(String s) throws RemoteException 
	{
		System.out.println("From Spark App: " + s);
		
	}
}
