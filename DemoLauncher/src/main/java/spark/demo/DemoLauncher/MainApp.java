package spark.demo.DemoLauncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

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
	static MainApp lcsoj;
	
	
	public static void main( String[] args ) throws IOException, InterruptedException
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
        
        lcsoj = new MainApp();
        Naming.rebind("//"+LOCAL_IP+"/"+RMIName, lcsoj);
		
		
		
		
		//LAUNCH SPARK APP
        /*
		try 
		{
			String SPARK_MASTER="spark://hadoop01:7077";
			
			System.out.println("Spark home: " + System.getenv("SPARK_HOME"));
			
			SparkAppHandle handle = new SparkLauncher()
					.setSparkHome(System.getenv("SPARK_HOME"))
			         .setAppResource("/home/hduser/docs/DemoLauncher-0.0.1-SNAPSHOT.jar")
			         .setMainClass("spark.demo.SparkApp.SparkApp")
			         .setMaster(SPARK_MASTER)
			         .setConf(SparkLauncher.DRIVER_MEMORY, "1g")
			         .startApplication();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
        
        
        // LAUNCH BY CMD
        
        List<String> cm= new ArrayList<String>();
		
        //cm.add("/bin/sh");
		//cm.add("$SPARK_HOME/bin/spark-submit");
        cm.add("./spark-submit");
		cm.add("--class");
		cm.add("spark.demo.SparkApp.SparkApp");
		cm.add("--master");
		cm.add("spark://hadoop01:7077");
		cm.add("/home/hduser/docs/DemoLauncher-0.0.1-SNAPSHOT.jar");
		
		
		
		//ProcessBuilder pb = new ProcessBuilder("./runApp.sh");
		
		String SPARK_BIN=System.getenv("SPARK_HOME")+"/bin";
		System.out.println("SPARK BIN: " + SPARK_BIN);
		
		ProcessBuilder pb = new ProcessBuilder(cm);
		pb.directory(new File(SPARK_BIN));
		Process p = pb.start();
	
		
		
		//String command = "/bin/sh $SPARK_HOME/bin/spark-submit --class spark.demo.SparkApp.SparkApp --master spark://hadoop01:7077 /home/hduser/docs/DemoLauncher-0.0.1-SNAPSHOT.jar";
		//Process p = Runtime.getRuntime().exec(command);
		System.out.println("SPARK Subtmit was started!");
		
		
		System.out.println("Start IF config");
		pb=new ProcessBuilder("ifconfig");
		Process p1 = pb.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null)
		{
		   System.out.println(line);
		}
		System.out.println("Stop if config");
		
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
	
	
	
	static void startCMD(List<String> cm) throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder(cm);
		Process p = pb.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null)
		{
		   System.out.println(line);
		}
	}
}
