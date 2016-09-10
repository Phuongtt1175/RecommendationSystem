package spark.demo.DemoLauncher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Hello world!
 *
 */
public class MainApp  extends UnicastRemoteObject implements ILauncherServer
{
    protected MainApp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

	public String ping(String s) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
