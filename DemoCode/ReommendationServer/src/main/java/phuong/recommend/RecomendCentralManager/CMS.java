package phuong.recommend.RecomendCentralManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import phuong.recommend.ModelBuilder.IModelBuilderAPI;
import phuong.recommend.Scoring.IScoringAPI;
import phuong.recommend.Scoring.ISocringControl;

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
	static String SPARK_MASTER="local[2]";
	static String SPARK_APP_JAR="/home/hduser/docs/ReommendationServer-0.0.1-SNAPSHOT.jar";



	//RMI Config
	static Registry RMIRegistry;
	static String RMIName="CMS";
	static String LOCAL_IP="192.168.11.107";
	static String CMS_URL = "//"+LOCAL_IP+"/"+RMIName;
	static CMS CMSobj;
	
	//Services
	
	//ModelBuilder
	static String RMI_URL_ModelBuilder="//192.168.11.107/ModelService";
	static String ModelBuilderJar="";
	static String ModelBuilderMainClass="";
	static IModelBuilderAPI modelBuilder;
	static String MODEL_PATH="";
	
	//Scoring
	static String RMI_URL_Scoring ="";
	static String scoringJar="";
	static String scoringMainClass="";
	static ISocringControl scoringObj;
	static IScoringAPI scoreAPI;
	
	//Log streaming
	static String RMI_URL_DataStream="";
	static String logStreamingJar="";
	static String LogStreamingMainClass="";
	
	
	
	//********************************************************
  	//					MAIN
  	//********************************************************
	
    public static void main( String[] args ) throws RemoteException, MalformedURLException, UnknownHostException
    {
        System.out.println( "CMS is starting..." );
        
        SPARK_MASTER = args[0];
        
        
        
        InetAddress IP=InetAddress.getLocalHost();
        LOCAL_IP = IP.getHostAddress();
        CMS_URL = "//"+LOCAL_IP+"/"+RMIName;
        
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
        Naming.rebind(CMS_URL, CMSobj);
        
        System.out.println("Start CMS at: " + CMS_URL);
       
        
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
		
		
	}





	public void startModelBuilder() throws RemoteException 
	{
		System.out.println("Starting ModelBuilder...");
		
		//Prepare submit commmand
		List<String> cm= new ArrayList<String>();
		cm.add("./spark-submit");
		cm.add("--class");
		cm.add("phuong.recommend.ModelBuilder.ModelBuilderService");
		cm.add("--master");
		cm.add(SPARK_MASTER);
		cm.add("--executor-memory");
		cm.add("1G");
		cm.add("--total-executor-cores");
		cm.add("1");
		cm.add(SPARK_APP_JAR);
		cm.add(CMS_URL);
				
		System.out.println("*************************************");
		System.out.println("Submit as:");
		System.out.println(cm.toString());
		
		
		boolean rs = Lib.startSparkApp(cm);
		if(rs)
			System.out.println("ModelBuilder was started!");
		else
			System.out.println("Cannot Start Modelbuilder Service!");
		
		System.out.println("*************************************");
		
	}



	




	public void startScoringService() throws RemoteException 
	{
		System.out.println("Starting Scoring Service...");
		//Prepare submit commmand
		List<String> cm= new ArrayList<String>();
		cm.add("./spark-submit");
		cm.add("--class");
		cm.add("phuong.recommend.Scoring.ScoringService");
		cm.add("--master");
		cm.add(SPARK_MASTER);
		cm.add("--executor-memory");
		cm.add("1G");
		cm.add("--total-executor-cores");
		cm.add("2");
		cm.add(SPARK_APP_JAR);
		cm.add(CMS_URL);
		
		
		System.out.println("*************************************");
		System.out.println("Submit as:");
		System.out.println(cm.toString());
		
		boolean rs = Lib.startSparkApp(cm);
		if(rs)
			System.out.println("ScoringService was started!");
		else
			System.out.println("Cannot Start Scoring Service!");
		
		System.out.println("*************************************");
	}





	public void stopDataStreaming() throws RemoteException 
	{
		// TODO Auto-generated method stub
		
	}





	public void stopModelBuilder() throws RemoteException 
	{
		
		
	}





	public void stopScoringService() throws RemoteException 
	{
		scoringObj.stopScoringService();
	}





	public void stopAll() throws RemoteException 
	{
		
		
	}



	public boolean registryModelBuilder(String RMI_URL) throws RemoteException 
	{
		System.out.println("Model Builder registry at "+RMI_URL);
		RMI_URL_ModelBuilder=RMI_URL;
		try 
		{
			modelBuilder= (IModelBuilderAPI) Naming.lookup(RMI_URL_ModelBuilder);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}



	public void updateNewModel(String modelpath, String modelID) throws RemoteException 
	{
		MODEL_PATH=modelpath;
		
		//Check if socring service is null
		if(scoringObj==null)
			return;
		
		scoringObj.refeshModel(MODEL_PATH);
			
		
	}



	public boolean registryDataStream(String RMI_URL) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}



	public boolean registryScoring(String RMI_URL) throws RemoteException 
	{
		System.out.println("ScoringService registry at "+RMI_URL);
		RMI_URL_Scoring=RMI_URL;
		try 
		{
			scoringObj= (ISocringControl) Naming.lookup(RMI_URL_Scoring);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		scoringObj.refeshModel(MODEL_PATH);
		
		
		return true;
	}



	public int getDataStreamingStatus() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	public int getModelBuilderStatus() throws RemoteException 
	{
		if(modelBuilder==null)
			return 1;

		try
		{
			String txt = modelBuilder.checkConnection();
		}
		catch(RemoteException ex)
		{
			return 2;
		}
		
		return 3;
	}



	public int getScoringServiceStatus() throws RemoteException 
	{
		if(scoringObj==null)
			return 1;

		try
		{
			String txt = scoringObj.checkConnection();
		}
		catch(RemoteException ex)
		{
			return 2;
		}
		
		return 3;
	}



	public int getNumberOfQuery() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	public int getNumberOfQueryCurrentModel() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	public int getNumberOfCacheHit() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	public int getNumberOfCacheHitCurrentModel() throws RemoteException 
	{
		int rs=0;

		if(scoringObj!=null)
			rs=1;
		
		return 0;
	}



	public String Recommend(String currentArticle) throws RemoteException 
	{
		
		String rs;
		if(scoreAPI==null)
		{
		
			try 
			{
				scoreAPI = (IScoringAPI) Naming.lookup(RMI_URL_Scoring);
			} 
			catch (MalformedURLException e) 
			{	
				return "";
			} 
			catch (NotBoundException e) 
			{		
				return "";
			}
		}
		
		
		rs = scoreAPI.Recommend(currentArticle);
		
		return rs;
	}



	public String getScoringURL() throws RemoteException 
	{
		if(scoringObj==null)
			return "<ScoringServie not running>";
		return RMI_URL_Scoring;
	}



	public String getModelBuilderURL() throws RemoteException 
	{
		if(modelBuilder==null)
			return "<modelBuider not running>";
		return RMI_URL_ModelBuilder;
	}



	public String getStreamingURL() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



}
