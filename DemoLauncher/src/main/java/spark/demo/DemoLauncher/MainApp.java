package spark.demo.DemoLauncher;

import java.io.IOException;
import java.rmi.RemoteException;
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

	public static void main( String[] args )
    {
		try 
		{
			String SPARK_MASTER="local[2]";
			SparkAppHandle handle = new SparkLauncher()
					.setSparkHome(System.getenv("SPARK_HOME"))
			         .setAppResource("/my/app.jar")
			         .setMainClass("my.spark.app.Main")
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
		// TODO Auto-generated method stub
		return null;
	}
}
