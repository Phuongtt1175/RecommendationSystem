package phuong.recommend.Scoring;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IScoringAPI extends Remote  
{
	
	public String Recommend(String currentArticles) throws RemoteException;
}
