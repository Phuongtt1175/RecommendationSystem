package phuong.recommend.RecomendCentralManager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import phuong.recommend.Scoring.IScoringAPI;

public class GUI {
	protected Shell shell;
	private Text tb_CMSServerIP;
	private Text tb_port;
	private boolean modelStart = false;
	private boolean scoringStart = false;
	private boolean cmsConnect = false;
	static ICMSClient cmsClient;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI window = new GUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(550, 300);
		shell.setText("SWT Application");
		
		Label lblCmsServer = new Label(shell, SWT.NONE);
		lblCmsServer.setBounds(44, 34, 63, 15);
		lblCmsServer.setText("CMS Server:");
		
		tb_CMSServerIP = new Text(shell, SWT.BORDER);
		tb_CMSServerIP.setBounds(113, 31, 76, 21);
		tb_CMSServerIP.setText("192.168.184.135");
		
		Label lblPort = new Label(shell, SWT.NONE);
		lblPort.setBounds(204, 34, 55, 15);
		lblPort.setText("Port:");
		
		tb_port = new Text(shell, SWT.BORDER);
		tb_port.setBounds(236, 31, 76, 21);
		tb_port.setText("CMS");
		
		final Label lblStarting = new Label(shell, SWT.NONE);
		lblStarting.setBounds(134, 117, 55, 15);
		lblStarting.setText("Starting");
		final Label label = new Label(shell, SWT.NONE);
		label.setText("Starting");
		label.setBounds(133, 76, 55, 15);
				
		
		Label lblModelBuilder = new Label(shell, SWT.NONE);
		lblModelBuilder.setBounds(44, 76, 83, 15);
		lblModelBuilder.setText("Model Builder:");
		
		Label lblScoring = new Label(shell, SWT.NONE);
		lblScoring.setBounds(44, 117, 55, 15);
		lblScoring.setText("Scoring:");
		
		final Label lblModelBuilderURL = new Label(shell, SWT.NONE);
		lblModelBuilderURL.setBounds(320, 76, 150, 15);
		lblModelBuilderURL.setText("?");
		
		
		final Label lblScoringURL = new Label(shell, SWT.NONE);
		lblScoringURL.setBounds(320, 110, 150, 15);
		lblScoringURL.setText("?");
		
		final Button btnStartModel = new Button(shell, SWT.NONE);
		btnStartModel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {				
					System.out.println(cmsClient.checkConnection());
					if(cmsClient.getModelBuilderStatus()!=3){
						label.setText("Starting");
						btnStartModel.setText("Stop");					
						//modelStart=false;
						cmsClient.startModelBuilder();
						lblModelBuilderURL.setText(cmsClient.getModelBuilderURL());
					}else
					{
						label.setText("Stopping");				
						btnStartModel.setText("Start");
						//modelStart=true;
						cmsClient.stopModelBuilder();
						lblModelBuilderURL.setText("");
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnStartModel.setBounds(236, 71, 75, 25);
		btnStartModel.setText("Start");

		final Button btnStartScoring = new Button(shell, SWT.NONE);
		btnStartScoring.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {					
					if(cmsClient.getScoringServiceStatus()==3){
						lblStarting.setText("Starting");
						btnStartScoring.setText("Stop");					
						//scoringStart=false;
						cmsClient.startScoringService();
						lblScoringURL.setText(cmsClient.getScoringURL());
					}else
					{
						lblStarting.setText("Stopping");				
						btnStartScoring.setText("Start");
						//scoringStart=true;
						lblScoringURL.setText("");
						cmsClient.stopScoringService();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 				
			}
		});
		btnStartScoring.setText("Start");
		btnStartScoring.setBounds(236, 107, 75, 25);
		
		btnStartModel.setVisible(false);
		btnStartScoring.setVisible(false);
		final Button btnStartCMS = new Button(shell, SWT.NONE);
		btnStartCMS.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
									try {
										
										
					cmsClient = (ICMSClient) Naming.lookup("//"+tb_CMSServerIP.getText()+"/"+tb_port.getText());
					
				if(cmsConnect){

					cmsConnect=false;
				}else
				{

					cmsConnect=true;
				}
					if(cmsConnect){
						btnStartModel.setVisible(true);
						btnStartScoring.setVisible(true);
						label.setText("Starting");
						lblStarting.setText("Starting");
						btnStartCMS.setText("Disconnect");
						btnStartModel.setText("Stop");
						btnStartScoring.setText("Stop");

					}else
					{
						lblStarting.setText("Stopping");				
						label.setText("Stopping");
						btnStartCMS.setText("Connect");
						btnStartScoring.setText("Start");
						btnStartModel.setText("Start");

						btnStartModel.setVisible(false);
						btnStartScoring.setVisible(false);
						//cmsClient.stopAll();
					}
					
					cmsClient.checkConnection();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
									
				
				
			}
		});
		btnStartCMS.setBounds(350, 27, 75, 25);
		btnStartCMS.setText("Connect");
		
		
	}
}
