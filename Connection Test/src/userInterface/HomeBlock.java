package userInterface;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import simulator.Simulation;
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
		
		Simulation simulation = new Simulation();
		
		NEPanel = new NetworkEngineer();
		TPanel = new Tester(simulation);
		SOPanel = new SecurityOfficer();
		MPanel = new Manager();
				
		filePanel = new FileUtility();
		activity = new Viewer();
		nodeList = new EntityList();
		console = new Console();
		
		//EntityList gets a copy of network config file when file is loaded
		filePanel.registerObserver(nodeList);
		//TesterPanel gets a copy of network config file when file is loaded
		filePanel.registerObserver(TPanel);
		//Network Engineer panel gets a copy of network config file when file is loaded
		filePanel.registerObserver(NEPanel);
		//Manager panel gets a copy of network config file when file is loaded
		filePanel.registerObserver(MPanel);
		//Security Officer panel gets a copy of network config file when file is loaded
		filePanel.registerObserver(SOPanel);
		//Viewer gets a copy of network config file when file is loaded
		filePanel.registerObserver(activity);
		
		//Entity List needs to update when Network Engineer deletes, edits, or creates a node
		NEPanel.registerObserver(nodeList);
		//Viewer needs to update when Network Engineer deletes, edits, or creates a node
		NEPanel.registerObserver(activity);
		
		//Viewer updates which nodes are active when simulation is executing
		simulation.registerObserver(activity);
		
		//Console could update when simulation is executing but it is currently pulling info from the log
		//simulation.registerObserver(console);
	
		tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
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
				tabbedPane.add("Security Officer Panel", SOPanel);
				break;
			}
			case 3: {
				tabbedPane.add("Manager Panel", MPanel);
				break;
			}
			default: {
				System.out.println("This really shouldn't be possible..");
			}
		}
		
		activity.setLocation(279, 135);
		nodeList.setLocation(10, 136);
		console.setLocation(279, 396);
		tabbedPane.setBounds(279, 11, 495, 115);
		
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
