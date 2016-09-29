package phuong.recommend.ModelBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class GenerateTransFromLog {
	public void generateTrans(String hadoopHome,String master,String log_checkpoint,String logSavingPath,String  prefix_log,String transFolderPath,String prefix_tran ){
		
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
	}
}
