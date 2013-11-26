package userInterface;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import userPanels.Manager;
import userPanels.NetworkEngineer;
import userPanels.SecurityOfficer;
import userPanels.Tester;

import java.awt.Color;

public class HomeBlock extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//USER PANELS
	Tester TPanel = null;
	NetworkEngineer NEPanel = null;
	SecurityOfficer SOPanel = null;
	Manager MPanel = null;
	
	//Compositely aggregated components
	FileUtility filePanel= null;
	Viewer activity = null;
	EntityList nodeList = null;
	Console console = null;
	
	JTabbedPane tabbedPane = null;	
	
	public HomeBlock(int role) {
		setTitle("Connection Tester");
		setBounds(200, 200, 800, 600);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		NEPanel = new NetworkEngineer();
		TPanel = new Tester();
		filePanel = new FileUtility();
		activity = new Viewer();
		activity.setLocation(279, 135);
		nodeList = new EntityList();
		nodeList.setLocation(10, 136);
		console = new Console();
		console.setLocation(279, 396);
	
		tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(BorderFactory.createLineBorder(Color.black));
		tabbedPane.setBounds(279, 11, 495, 115);
		
		switch(role) {
			case 0: {
				tabbedPane.add("Network Engineer Panel", NEPanel);
				tabbedPane.add("Tester Panel", TPanel);
				break;
			}
			case 1: {
				tabbedPane.add("Tester Panel", TPanel);	
				break;
			}
			case 2: {
				//tabbedPane.add("Tester Panel", TPanel);
				break;
			}
			case 3: {
				//tabbedPane.add("Tester Panel", TPanel);
				break;
			}
			default: {
				System.out.println("This really shouldn't be possible..");
			}
		}
		
		getContentPane().add(tabbedPane);
		getContentPane().add(filePanel);
		getContentPane().add(activity);
		getContentPane().add(nodeList);
		getContentPane().add(console);
		
	}
	
	public void start() {
		setVisible(true);
	}
	
	public void stop() {
		setVisible(false);
	}
}
