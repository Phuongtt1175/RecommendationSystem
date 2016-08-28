package phuong.recommend.ModelBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang.time.DateFormatUtils;


public class IncrementalLogs {
	private String logPath="";
	private String logSavingPath ="";
	private String prefix_log = "";

	public IncrementalLogs(String logPath, String logSavingPath, String prefix_log) {
		super();
		this.logPath = logPath;
		this.logSavingPath = logSavingPath;
		this.prefix_log=prefix_log;
	}

	public String getLogSavingPath() {
		return logSavingPath;
	}

	public void setLogSavingPath(String logSavingPath) {
		this.logSavingPath = logSavingPath;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	
	public void readLog(){
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(this.logPath);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        if(line.contains("content-component/article-category-list/")){

		        	ApacheAccessLog a =  ApacheAccessLog.parseFromLogLine(line);
		        	String appendS="";
		        	if(a.getCookie()!=null && a.getDateTimeString()!=null && a.getEndpoint()!=null){
		        		String s = a.getEndpoint().substring(a.getEndpoint().lastIndexOf("/")+1);
		        		String cookie =a.getCookie();
		        		if(cookie.lastIndexOf(';')>0){
		        			cookie=cookie.substring(cookie.lastIndexOf(';')+1).trim();
		        		}
		        		if(s.indexOf("-")>0)
		        		{
		        			appendS=a.getDateTimeString()+","+cookie+","+ s.substring(0, s.indexOf("-"));//+","+s.substring(s.indexOf("-")+1);
		        		}
		        		
		        		
		        	}
		        	try {
		        	if(appendS!=""){
		        	DateFormat format = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss Z");
		        	Date d = format.parse(a.getDateTimeString());
		        	String ds = DateFormatUtils.format(d, "yyyyMMdd");
		        	//System.out.println(ds);
		        	File file = new File(this.logSavingPath+prefix_log+ds+".txt");

//		        	// if file doesnt exists, then create it
		        	if (!file.exists()) {
		        		file.createNewFile();
		        		System.out.println("New processed Log File created");
		        	}

		        	FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		        	BufferedWriter bw = new BufferedWriter(fw);
		        	bw.write(appendS+"\n");
		        	bw.close();
		        	}
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }		    
		        }
		        }
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
