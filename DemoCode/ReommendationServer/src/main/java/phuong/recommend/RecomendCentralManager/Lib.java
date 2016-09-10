package phuong.recommend.RecomendCentralManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Lib 
{
	
	public static boolean startSparkApp(List<String> submitCommand)
	{
		String SPARK_BIN=System.getenv("SPARK_HOME")+"/bin";
		ProcessBuilder pb = new ProcessBuilder(submitCommand);
		pb.directory(new File(SPARK_BIN));
		try 
		{
			Process p = pb.start();
		} 
		catch (IOException e) 
		{
			return false;
		}
		
		return true;
	}

}
