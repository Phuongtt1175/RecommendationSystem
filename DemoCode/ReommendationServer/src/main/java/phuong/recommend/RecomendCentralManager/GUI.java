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
	private Text text;
	private Text text_1;
	private boolean modelStart = false;
	private boolean scoringStart = false;

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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Label lblCmsServer = new Label(shell, SWT.NONE);
		lblCmsServer.setBounds(44, 34, 63, 15);
		lblCmsServer.setText("CMS Server:");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(113, 31, 76, 21);
		
		Label lblPort = new Label(shell, SWT.NONE);
		lblPort.setBounds(204, 34, 55, 15);
		lblPort.setText("Port:");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(236, 31, 76, 21);
		final Label lblStarting = new Label(shell, SWT.NONE);
		lblStarting.setBounds(134, 117, 55, 15);
		lblStarting.setText("Starting");
		final Label label = new Label(shell, SWT.NONE);
		label.setText("Starting");
		label.setBounds(133, 76, 55, 15);
		final Button btnStartModel = new Button(shell, SWT.NONE);
		btnStartModel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ICMSClient cmsClient = (ICMSClient) Naming.lookup("//"+text.getText()+"/"+text_1.getText());
					if(modelStart){
						label.setText("Starting");
						btnStartModel.setText("Stop");					
						modelStart=false;
						cmsClient.startModelBuilder();
					}else
					{
						label.setText("Stopping");				
						btnStartModel.setText("Start");
						modelStart=true;
						cmsClient.stopModelBuilder();
					}
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
		btnStartModel.setBounds(236, 71, 75, 25);
		btnStartModel.setText("Start");
		
		Label lblModelBuilder = new Label(shell, SWT.NONE);
		lblModelBuilder.setBounds(44, 76, 83, 15);
		lblModelBuilder.setText("Model Builder:");
		
		Label lblScoring = new Label(shell, SWT.NONE);
		lblScoring.setBounds(44, 117, 55, 15);
		lblScoring.setText("Scoring:");
		
		final Button btnStartScoring = new Button(shell, SWT.NONE);
		btnStartScoring.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ICMSClient cmsClient = (ICMSClient) Naming.lookup("//"+text.getText()+"/"+text_1.getText());
					if(scoringStart){
						lblStarting.setText("Starting");
						btnStartScoring.setText("Stop");					
						scoringStart=false;
						cmsClient.startScoringService();
					}else
					{
						lblStarting.setText("Stopping");				
						btnStartScoring.setText("Start");
						scoringStart=true;
						cmsClient.stopScoringService();
					}
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
		btnStartScoring.setText("Start");
		btnStartScoring.setBounds(236, 107, 75, 25);
		
		final Button btnStartCMS = new Button(shell, SWT.NONE);
		btnStartCMS.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
									try {
					ICMSClient cmsClient = (ICMSClient) Naming.lookup("//"+text.getText()+"/"+text_1.getText());
					if(scoringStart){
						label.setText("Starting");
						lblStarting.setText("Starting");
						btnStartCMS.setText("Stop");
						btnStartModel.setText("Stop");
						btnStartScoring.setText("Stop");
						scoringStart=false;
						modelStart=false;
					}else
					{
						lblStarting.setText("Stopping");				
						label.setText("Stopping");
						btnStartCMS.setText("Start");
						btnStartScoring.setText("Start");
						btnStartModel.setText("Start");
						scoringStart=true;
						modelStart=true;
						cmsClient.stopAll();
					}
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
		btnStartCMS.setBounds(335, 27, 75, 25);
		btnStartCMS.setText("Start");
		
		
	}
}
