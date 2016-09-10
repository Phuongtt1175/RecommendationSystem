package spark.demo.SparkApp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import spark.demo.DemoLauncher.*;

public class SparkApp {

	
	static String FILE_PATH ="hdfs://hadoop01:8020/samples/input/pg1661.txt";
	static String SERVER_URL="//localhost/LS";
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException 
	{
		System.out.println("Hello");
		
		ILauncherServer srv = (ILauncherServer) Naming.lookup(SERVER_URL);
		System.out.println("From Server: "+srv.ping("Spark App was started!"));
		
		
		SparkConf conf = new SparkConf().setAppName("Phuong Demo").setMaster("local");
	        //conf.set("spark.executor.cores", "1s");
	        //conf.set("spark.cores.max", "2");
	        
	    JavaSparkContext sc = new JavaSparkContext(conf);
	     
	     
	     
	    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
	    List<Integer> data2 = Arrays.asList(1, 2, 3, 4, 5,6,2,2,4,5,7,8,9,10);
	     
	    JavaRDD<Integer> RDD1 = sc.parallelize(data);
	    JavaRDD<Integer> RDD2 = sc.parallelize(data2);
	     
	    long dataCount = RDD1.count();
	    long data2Count = RDD2.count();
	     
	    System.out.println("RDD1: "+ dataCount);
	    System.out.println("RDD2: "+ data2Count);
	    
	    
	    srv.ping("RDD1: "+ dataCount);
	    srv.ping("RDD2: "+ data2Count);
	    
	    
	    srv.ping("SPARK APP IS STOPING...");

	}

}
