package webUsageTracking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/webtracker")
public class WebTracker {

	@POST
	@Path("/userinfo")
	@Produces(MediaType.APPLICATION_XML)
	public void getUserInfo(String pTitle, String pUrl, @Context HttpServletRequest requestContext) throws Exception {
		
		
		//send web tracking information to RECSYS
		System.out.print("send web tracking information to RECSYS\n");
		String title = pTitle;
		String url = pUrl;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
		
		String timestamp = dateFormat.format(date);
		String IP = requestContext.getRemoteAddr();;
		
		
		
		
		Socket socket1;
	    int portNumber = 1777;
	    String str = url + "," + title + ","  + IP + "," + timestamp;
	    System.out.println(str);

	    socket1 = new Socket("192.168.88.85", portNumber);

	    BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

	    PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

	    pw.println(str);

	    while ((str = br.readLine()) != null) {
	      System.out.println(str);
	      pw.println("bye");

	      if (str.equals("bye"))
	        break;
	    }

	    br.close();
	    pw.close();
	    socket1.close();
		
	}

}
