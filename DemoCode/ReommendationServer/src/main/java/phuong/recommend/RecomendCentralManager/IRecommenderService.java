package phuong.recommend.RecomendCentralManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRecommenderService extends Remote 
{
	public int[] Recommend(int[] currentArticleIDs) throws RemoteException;
	public String Recommend2(String currentArticle) throws RemoteException;
}
