package phuong.recommend.Scoring;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import phuong.recommend.RecomendCentralManager.ICMSComponent;
import scala.Tuple2;




public class ScoringService extends UnicastRemoteObject implements IScoringAPI,ISocringControl
{
    protected ScoringService() throws RemoteException { super();}
    
    
    //Parent
    private static String CMS_URL ="//localhost/CMS";
    private static ICMSComponent CMSobj=null;
    
    
    //RMICMS_URL
    private static String RMI_NAME= "ScoringServices";
    private static String SERVER_URL="//localhost/ScoringServices";
    
    
    
    
    
    //SPARK
    private static SparkConf conf;
	private static JavaSparkContext sc;
	
	//RDD
	private static JavaRDD<String> rules;
	
	//Param
	private static String rule_path ="E:\\Study\\Cao Hoc\\luan an\\recommendation news\\RecommendationSystem\\DemoCode\\Scoring\\rule.txt";
	
	
	
	//CACHE
	static Map<String[],String> queryCache;
	
	//********************************************************
	//					MAIN
	//********************************************************
	public static void main( String[] args ) throws RemoteException, UnknownHostException
    {
        System.out.println( "Hello World!" );
        
        //***************************************************
        // Config Spark

     	//System.setProperty("hadoop.home.dir", "D:\\WS\\Lib");
     		
     	conf = new SparkConf().setMaster("local").setAppName("Scoring");
     	sc = new JavaSparkContext(conf);
     	queryCache = new HashMap();
     	
     	
     	    	
     	
     	
     	
     	//***************************************************
        //				RMI Regitry Scoring
       
     	// Get current IP of host 
     	
     	String locaIP="";
     	InetAddress IP=InetAddress.getLocalHost();
     	locaIP = IP.getHostAddress();
     	// set SERVER_URL = //<ip>/RMI_NAME
     	SERVER_URL="//"+locaIP+"/"+RMI_NAME;
     	
     	
     	try 
        {
			LocateRegistry.createRegistry(1099);
			ScoringService svrObj = new ScoringService();
			System.out.println("java RMI registry created.");			
			Naming.rebind(SERVER_URL, svrObj);
			
			System.out.println("RMI Interface was started");
			
		} 
        catch (RemoteException e) 			{e.printStackTrace();}
        catch (MalformedURLException e)    	{e.printStackTrace();}
        
        
        //******************************************************
        //				Registry with CMS
        try 
        {
			CMSobj = (ICMSComponent)Naming.lookup(CMS_URL);
		} 
        catch (MalformedURLException e) {e.printStackTrace();} 
        catch (RemoteException e) {e.printStackTrace();} 
        catch (NotBoundException e) {e.printStackTrace();}
        
        
        CMSobj.registryScoring(SERVER_URL);
        
        
    }

	
	//********************************************************
	//					SCORING API
	//********************************************************
	public String checkConnection() throws RemoteException 
	{
		
		return "Connection Success";
	}

	

	
	
	
	public String Scoring(final String[] input)
	{		
			
		PairFunction<String, String, String> f = new PairFunction<String, String, String>(){

			public Tuple2<String, String> call(String arg0) throws Exception {
				// TODO Auto-generated method stub
				String[] v =arg0.split("\\|");				
				String source = v[0].substring(1,v[0].indexOf("]"));
				String[] array_source = source.split(", ");
				double sim = ArrayCompare(Arrays.asList(input),Arrays.asList(array_source));
				Tuple2 t = null;
				if(sim!=0.0 & (Arrays.asList(input).containsAll(Arrays.asList(array_source)))){
				 t=	new Tuple2(sim+"|"+v[2]+"|"+v[3], v[0]+"=>"+v[1]);
				}				
				return t;
			}
			
		};
		
		JavaPairRDD<String, String> rule_rdd = rules.mapToPair(f);
		org.apache.spark.api.java.function.Function<Tuple2<String, String>,Boolean> f2 = new org.apache.spark.api.java.function.Function<Tuple2<String, String>,Boolean>(){
			public Boolean call(Tuple2<String, String> v1) throws Exception {
			       return v1 != null;
			   }
		};
		JavaPairRDD<String, String> rule= rule_rdd.filter(f2);
		JavaPairRDD<String, String> final_rule = rule.sortByKey();
		String result =final_rule.collect().toString();
		return result;
	}
	
	private String[] reprocessInput(String input){
		String[] array_input = input.split(", ");
		Arrays.sort(array_input);
		array_input=removeDuplicates(array_input);
		return array_input;
	}
	private static String[] removeDuplicates(String[] arr) {
		  Set<String> alreadyPresent = new HashSet<String>();
		  String[] whitelist = new String[0];

		  for (String nextElem : arr) {
		    if (!alreadyPresent.contains(nextElem)) {
		      whitelist = Arrays.copyOf(whitelist, whitelist.length + 1);
		      whitelist[whitelist.length - 1] = nextElem;
		      alreadyPresent.add(nextElem);
		    }
		  }

		  return whitelist;
		}
	private double ArrayCompare(List<String> list, List<String> list2)
    {
        int l1Size = list.size();
        int l2Size = list2.size();
        
        int dulicatedCount=0;
        
        for(int i=0;i<l1Size;i++)
        {
            if(list2.indexOf(list.get(i))>=0)
                dulicatedCount++;
        }
        
        double sim=((double)dulicatedCount*2)/(l1Size+l2Size);
        
        return sim;
    }


	public String Recommend(String currentArticles) throws RemoteException 
	{
		String result="";
		//Convert currentArticles to array
		//Sort and remove duplicate article in currentArt
		String[] currentArt=reprocessInput(currentArticles);
		// Check if currentArt in cache "queryCache"
		if(queryCache.containsKey(currentArt)){
			// IF IN CACHE => result = queryCache.get(...)
			result=queryCache.get(currentArt);			
		}
		else
		
		{
		//IF NOT IN CACHE => Call scoring function to get new value	
		result= Scoring(currentArt);
		// Cache the result
		// Ex: queryCache.put(currentArt,result)
		queryCache.put(currentArt, result);
		}
		return result;		

	}


	public int getCacheSize() throws RemoteException 
	{
		return queryCache.size();
	}


	public void cleanCache() throws RemoteException 
	{
		queryCache.clear();
	}


	public int refeshModel(String ModelPath) throws RemoteException 
	{
		rule_path=ModelPath;
		//Refesh rules RDD
		rules.unpersist();
		rules = sc.textFile(rule_path);
		rules.cache();		
		cleanCache();
		return 0;
	}


	public void stopScoringService() throws RemoteException {
		// TODO Auto-generated method stub
		
		try {
			Naming.unbind(SERVER_URL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
