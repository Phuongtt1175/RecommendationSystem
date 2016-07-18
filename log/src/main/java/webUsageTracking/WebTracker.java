package webUsageTracking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/webtracker")
public class WebTracker {

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	public void getUsers() {
		
		
		//send web tracking information to RECSYS
		System.out.print("send web tracking information to RECSYS");
	}

}
