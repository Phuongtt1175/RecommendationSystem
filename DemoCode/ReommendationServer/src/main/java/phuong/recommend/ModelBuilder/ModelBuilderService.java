package phuong.recommend.ModelBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;

import phuong.recommend.RecomendCentralManager.ICMSComponent;
import phuong.recommend.Scoring.ScoringService;
import scala.Tuple2;

/**
 * Hello world!
 *
 */
public class ModelBuilderService extends UnicastRemoteObject implements IModelBuilderAPI
{
    protected ModelBuilderService() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

    //MODEL
    private static String CURRENT_MODEL="hdfs://master:8020/associtaionRule/20160202/";
    
    //Parent
    private static String CMS_URL ="//localhost/CMS";
    private static ICMSComponent CMSobj=null;
    
    
    //RMICMS_URL
    private static String RMI_NAME= "ModelServices";
    private static String SERVER_URL="//localhost/ModelService";
    
    
    //INPUT
    private static String accessLogPath = "D:\\Xampp5.6\\apache\\logs\\access.log";
	private static String logSavingPath = "E:\\Study\\Cao Hoc\\luan an\\recommendation news\\Demo\\sparkDemo\\Logs\\";
	private static String hadoopHome = "E:\\EclipseWS\\hadoop";
	private static String transFolderPath="E:\\Study\\Cao Hoc\\luan an\\recommendation news\\Demo\\sparkDemo\\Trans\\";
	private static String log_checkpoint ="E:\\Study\\Cao Hoc\\luan an\\recommendation news\\Demo\\sparkDemo\\log_checkpoint.txt";
	private static String prefix_tran = "MyTran";
	private static String prefix_log = "MyLogs";
	private static int from =0;
	
	//Model Parameter
	private static double minSup = 0.2;
	private static double minConf = 0.8;
	private static int storing_duration_days = 300;
	
	//OUTPUT
	private static String rule ="E:\\Study\\Cao Hoc\\luan an\\recommendation news\\Demo\\sparkDemo\\rule.txt";
	
	
	
	
	
	
	
    
    
    //SPARK
    private static SparkConf conf;
	private static JavaSparkContext sc;
	private static String master ="local";
	
	//RDD
	private static JavaRDD<String> inputTransaction;
    
    
    //********************************************************
  	//					MAIN
  	//********************************************************
	public static void main( String[] args ) throws UnknownHostException, RemoteException
    {
        System.out.println( "Hello World!" );
        conf = new SparkConf().setMaster("local").setAppName("ModelBuilder");
     	sc = new JavaSparkContext(conf);
     	
     	
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
			ModelBuilderService svrObj = new ModelBuilderService();
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
	//					Model Builder API
	//********************************************************
	public String checkConnection() throws RemoteException {
		// TODO Auto-generated method stub
		return "Connect Success";
	}

	public String reBuildModel() throws RemoteException 
	{
		
		//Build and save model
		
		//Merge Model
		
		
		CURRENT_MODEL = "hdfs://master:8020/associtaionRule/20160202/";
				
		System.setProperty("hadoop.home.dir", hadoopHome);
		SparkConf conf = new SparkConf().setMaster(master).setAppName("SampleSparkAppByJava");
        
        // Create spark context with the conf that we've just created
        JavaSparkContext sc = new JavaSparkContext(conf);
        ////read check point file to get last_date of transaction
		JavaRDD<String> rdd = sc.textFile(log_checkpoint);
		String last_date = rdd.collect().get(0).toString();
		//get all logs from logs folder
		JavaPairRDD<String, String> rdd1 = sc.wholeTextFiles(logSavingPath);
		
		//map only logs file has index >= last_date above
		for(Tuple2<String, String> key: rdd1.collect()){        	
      	String logFilePath = key._1;
      	String logIndex = key._1.substring(key._1.indexOf(prefix_log)+6, key._1.indexOf(prefix_log)+6+8);
      	//System.out.println(logIndex);
      	if(logIndex.compareTo(last_date)>0){
      	JavaRDD<Logs> logs = sc.textFile(logFilePath).map(new Function<String,Logs>(){
         	 public Logs call(String line) throws Exception {
         		 String[] parts = line.split(",");
         		 Logs l = new Logs();        		 
         		 l.setDatetimes(parts[0]);
         		 l.setCookie(parts[1]);
         		 l.setItem(parts[2]);
         		 return l;
         	 }
         });
      	//convert logs to transaction
      	PairFunction f = new PairFunction<Logs, String, String>()
  		{
  
  			public Tuple2<String, String> call(Logs arg0) throws Exception {
  				// TODO Auto-generated method stub
  				return new Tuple2(arg0.getCookie(),arg0.getItem());
  			}
  		};
  		JavaPairRDD<String,String> RDD2 = logs.mapToPair(f);
  		
  		
  		
  		Function2<String, String, String> func = new Function2<String,String,String>()
  		{
  
  			public String call(String v1, String v2) throws Exception {
  				// TODO Auto-generated method stub
  				return v1 +"#"+ v2;
  			}
  			
  		};
  		JavaPairRDD<String,String> RDD3 = RDD2.reduceByKey(func);
  		
  		//save RDD as transaction into transaction_folder
  		RDD3.saveAsTextFile(transFolderPath+prefix_tran+logIndex);
  		
  		//update check point file
  		File file = new File(log_checkpoint);
  		file.delete();
  		PrintWriter writer;
			try {
				writer = new PrintWriter(log_checkpoint, "UTF-8");
	    		writer.println(logIndex);
	    		writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
      }
      }
      //create rule
      JavaPairRDD<String, String> rdd4 = sc.wholeTextFiles(transFolderPath+"*");
      JavaRDD<List<String>> transactions = null;
      //System.out.println(transFolderPath);
      //get current date
      DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
 	  Date date = new Date();
 	  Date d= new Date(date.getTime() - storing_duration_days * 24 * 3600 * 1000 );
 	  final String lastupdatedate = dateFormat.format(date);
 	  final int lastStoreDate = Integer.parseInt(dateFormat.format(d));
      //generate rule
 	  //get transactions
 	  for(Tuple2<String, String> key: rdd4.collect()){        	
      	String tranFilePath = key._1;
      	//System.out.println(tranFilePath);
      	int tranIndex = Integer.parseInt(key._1.substring(key._1.indexOf(prefix_tran)+6, key._1.indexOf(prefix_tran)+6+8));
      	//System.out.println(tranIndex);
      	//if(tranIndex>=from && tranIndex<=to){
      	File rFile = new File(rule);
      	
      	//check if rule exist --> update from variable as today
      	if(rFile.exists()){
      		from=Integer.parseInt(lastupdatedate);
      		}
      	//update or create new based on from variable
      	if(tranIndex>=from ){
      	JavaRDD<String> data = sc.textFile(tranFilePath);
      	transactions = data.map( 
			       new Function<String, List<String>>() { 
			         public List<String> call(String line) { 
			           String[] parts = line.split(",");
			           String[] items = parts[1].substring(0, parts[1].toString().length()-1).split("#");				           
			           return Arrays.asList(removeDuplicates(items)); 
			         } 
			       } 
			     );      	
      }
      }
      //}        
      //begin generate rule based on transactions generated above
    //  	System.out.println(transactions.collect().size());
		FPGrowth fpg = new FPGrowth() 
			       .setMinSupport(minSup) 
			       .setNumPartitions(10); 
		FPGrowthModel<String> model = fpg.run(transactions); 
			
			
//	     for (FPGrowth.FreqItemset<String> itemset: model.freqItemsets().toJavaRDD().collect()) { 
//	       System.out.println("[" + itemset.javaItems() + "], " + itemset.freq()); 
//	     }
		String result="";
		PrintWriter wr = null;				
		List<String> l = new ArrayList<String>();
		try{
			
			//if existing rule file, read all rules and add into list
			if(from !=0){
			
			BufferedReader br = null;
			String sCurrentLine;
				br = new BufferedReader(new FileReader(rule));
				while ((sCurrentLine = br.readLine()) != null) {
					 l.add(sCurrentLine+"\n");
				}			    
			}
			//get new rule, and add to list
				for (AssociationRules.Rule<String> rule 
					       : model.generateAssociationRules(minConf).toJavaRDD().collect()) {	 
					    List<String> antc = new LinkedList<String>(rule.javaAntecedent()) ;
						//sort rule
					    Collections.sort(antc); 
						result =  antc + "| " + rule.javaConsequent() + "| " + rule.confidence()+"| "+lastupdatedate+"\n"; 
					     l.add(result);
			    
				}
			    //String[] a = removeDuplicates(l.toArray(new String[l.size()]));
				//remove in list duplicated items(rule)
				wr = new PrintWriter(rule, "UTF-8");
				Set<String> set = new HashSet<String>();
			    for (int i = 0; i < l.size(); i++) {
					if(set.contains(l.get(i).substring(0,l.get(i).lastIndexOf("|")))){
						l.remove(i);
					}
					else
					{
						int dates = Integer.parseInt(l.get(i).substring(l.get(i).lastIndexOf("|")+1).trim());
						if(dates>lastStoreDate){
							wr.print(l.get(i));
						}
						set.add(l.get(i).substring(0,l.get(i).lastIndexOf("|")));
					}
					
				}   
				wr.close();
		
			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
		
		return CURRENT_MODEL;
	}
	public static String[] removeDuplicates(String[] arr) {
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
	public String getCurrentModelPath() throws RemoteException 
	{
		// TODO Auto-generated method stub
		return CURRENT_MODEL;
	}
}
