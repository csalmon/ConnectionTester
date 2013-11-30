package userPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import fileIO.NetworkConfig;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import observation.Observable;
import observation.Observer;
import simulator.Node;
import simulator.Simulation;

public class NetworkEngineer extends JPanel implements ActionListener, Observer, Observable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	JButton createNodeBtn = null;
	JButton deleteNodeBtn = null;
	JButton editNodeBtn = null;
	JButton newFileBtn = null;
	NetworkConfig configFile = null;
	
	
	public NetworkEngineer() {
		setSize(470, 100);
		setVisible(true);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblOperations = new JLabel("Operations");
		lblOperations.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblOperations, "2, 2");
		
		editNodeBtn = new JButton("Edit Node");
		editNodeBtn.addActionListener(this);
		
		deleteNodeBtn = new JButton("Delete Node");
		deleteNodeBtn.addActionListener(this);
		
		createNodeBtn = new JButton("Create Node");
		createNodeBtn.addActionListener(this);
		
		newFileBtn = new JButton("Start New File");
		newFileBtn.addActionListener(this);
		
		add(newFileBtn, "2, 4 left, top");
		add(createNodeBtn, "4, 4, left, top");
		add(deleteNodeBtn, "6, 4, left, top");
		add(editNodeBtn, "8, 4, left, top");

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == createNodeBtn ) {			
			System.out.println("pressed create Node button");
			this.createNode();
		
		} else if(e.getSource() == deleteNodeBtn) {
			System.out.println("pressed delete Node button");
			this.deleteNode();
			
		} else if(e.getSource() == editNodeBtn) {			
			System.out.println("pressed edit Node button");
			this.editNode();
			
		} else if(e.getSource() == newFileBtn) {
			System.out.println("pressed new File button");
			this.createNetworkConfigFile();
		}
	}
	
	
	private void editNode() {
		int index = Integer.valueOf(JOptionPane.showInputDialog ( "Enter INDEX of the node you want to edit:" ));
		/* Alright Brandon & Colby this is where stuff gets real. How in-depth do we want this? We didn't exactly design
		 * for this explicitly.. I mean, sure its a use case that's been floating around since the beginning
		 * but we never talked about what the interface for this use case would look like? Will it be simple?
		 * will it allow configuration of ALL fields of a node? Will it be mighty? WILL IT?!
		 * 
		 * Anyway. I'm thinking I'll draft up a UI for this thing after we complete the objectives lined up
		 * from our meeting a few days back
		 */
		JOptionPane.showMessageDialog(null, "HAHAAAAA you can't edit nodes!\n THE NODE IS MIGHTIER THAN YOU! COME BACK WHEN YOU'RE WORTHY");
	}

	private void createNetworkConfigFile() {
		try {
			//This throws an exception since the system can't find a file of name "newNetworkFile.xml". Brandon should probably fix this...
			this.configFile = new NetworkConfig("newNeworkFile.xml");
		} catch(Exception e) {
			//this doesn't get caught...
			JOptionPane.showMessageDialog(null, "The program is trying to find a file called 'newNetworkFile.xml'...\n"
					+ "That doesn't make any sense! The ctor of the network config file doesn't work for creating a new file.\n"
					+ "We need to add this ctor to NetworkConfig.java");
		}
	}

	private void deleteNode() {
		try {
			int index = Integer.valueOf(JOptionPane.showInputDialog ( "Enter INDEX of the node you want to delete:" ));
			this.configFile.remove(index);
			
			//NOTIFY ANY OBSERVER THAT MY NETWORK CONFIG FILE HAS CHANGED.
			this.notifyObservers();
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "There was a mistake deleting the node.");
		}		
	}

	private void createNode() {
		try {
			String newName= JOptionPane.showInputDialog ( "Enter node name:" );
			Node newNode = new Node(newName);
			//This doesn't work because there is no network config file.
			this.configFile.add(newNode);
			
			//NOTIFY ANY OBSERVER THAT MY NETWORK CONFIG FILE HAS CHANGED.
			this.notifyObservers();
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "There was a mistake adding the new node. Click 'Start New File' first.");
		}

	}

	@Override
	public void update(NetworkConfig netConfigFile) {
		this.configFile = netConfigFile;
		System.out.println("Network engineer successfully loaded the network config file");
		
	}

	@Override
	public void update(Node node, int flag) {
		//This method intentionally left blank. No reason for NetowkEngineer to RECEIVE updates about individual nodes...
		
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
		
	}

	@Override
	public void notifyObservers() {
		for (Observer ob : observers) {
            System.out.println("Notifying Observers (should only be the Entity List) about a node change (whether it be an update, deletion, or creation.");
            //changes are made on the network config file and then the entity list redoes its population of the list based on new netconfig file
            ob.update(this.configFile);
		}
		
	}

}
