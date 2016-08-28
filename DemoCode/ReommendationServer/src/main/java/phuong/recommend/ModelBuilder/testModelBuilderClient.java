package phuong.recommend.ModelBuilder;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class testModelBuilderClient {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException 
	{
		IModelBuilderAPI svr = (IModelBuilderAPI)Naming.lookup("//localhost/ModelService");
        System.out.println(svr.checkConnection());
        System.out.println(svr.reBuildModel());
        System.out.println(svr.getCurrentModelPath());

	}

}
