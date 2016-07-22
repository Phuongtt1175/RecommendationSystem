package webUsageTracking;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/webtracker")
public class WebTracker {

	@POST
	@Path("/userinfo")
	@Produces(MediaType.APPLICATION_XML)
	public void getUserInfo(String pTitle, String pUrl) {
		
		
		//send web tracking information to RECSYS
		System.out.print("send web tracking information to RECSYS\n");
		String title = pTitle;
		String url = pUrl;
		String timestamp = "";
		String IP = "";
		
		System.out.print(title + ":" + pUrl);
		
	}

}
