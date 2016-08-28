package phuong.recommend.Scoring;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class testScoringClient {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException 
	{
		IScoringAPI svr = (IScoringAPI)Naming.lookup("//localhost/ScoringServices");
        System.out.println(svr.checkConnection());
        System.out.println(svr.Recommend("a, b, c"));

	}

}
