package phuong.recommend.RecomendCentralManager;

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
public class CMS extends UnicastRemoteObject implements ICMSClient,ICMSComponent
{
	protected CMS() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	//SPARK CONFIG
	static final String SPARK_MASTER="local[2]";



	//RMI Config
	static Registry RMIRegistry;
	static String RMIName="CMS";
	static String LOCAL_IP="192.168.11.107";
	static CMS CMSobj;
	
	//Services
	
	//ModelBuilder
	static String RMI_URL_ModelBuilder="//192.168.11.107/ModelService";
	static String ModelBuilderJar="";
	static String ModelBuilderMainClass="";
	//static IModelBuilderAPI modelBuilder = (IModelBuilderAPI)Naming.lookup(RMI_URL_ModelBuilder);
	
	//Scoring
	static String RMI_URL_Scoring ="";
	static String scoringJar="";
	static String scoringMainClass="";
	
	//Log streaming
	static String RMI_URL_DataStream="";
	static String logStreamingJar="";
	static String LogStreamingMainClass="";
	
	
	
	//********************************************************
  	//					MAIN
  	//********************************************************
	
    public static void main( String[] args ) throws RemoteException, MalformedURLException
    {
        System.out.println( "CMS is starting..." );
        
        
        //RMI Start
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
        
        CMSobj = new CMS();
        Naming.rebind("//"+LOCAL_IP+"/"+RMIName, CMSobj);
        
    }



    //********************************************************
  	//					RMI API
  	//********************************************************

	public String checkConnection() throws RemoteException 
	{
		// TODO Auto-generated method stub
		return "Connect to CMS successful";
	}





	public void startDataStreaming() throws RemoteException 
	{
		try 
		{
			SparkAppHandle handle = new SparkLauncher()
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





	public void startModelBuilder() throws RemoteException 
	{
		try 
		{
			SparkAppHandle handle = new SparkLauncher()
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





	public void startScoringService() throws RemoteException 
	{
		try 
		{
			SparkAppHandle handle = new SparkLauncher()
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





	public void stopDataStreaming() throws RemoteException 
	{
		// TODO Auto-generated method stub
		
	}





	public void stopModelBuilder() throws RemoteException 
	{
		// TODO Auto-generated method stub
		
	}





	public void stopScoringService() throws RemoteException 
	{
		// TODO Auto-generated method stub
		
	}





	public void stopAll() throws RemoteException 
	{
		
		
	}



	public boolean registryModelBuilder(String RMI_URL) throws RemoteException 
	{
		RMI_URL_ModelBuilder=RMI_URL;
		return true;
	}



	public void updateNewModel(String modelpath, String modelID) throws RemoteException {
		// TODO Auto-generated method stub
		
	}



	public boolean registryDataStream(String RMI_URL) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}



	public boolean registryScoring(String RMI_URL) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
