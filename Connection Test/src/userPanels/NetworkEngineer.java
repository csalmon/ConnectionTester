package userPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import fileIO.NetworkConfig;
import fileIO.XMLWriter;
import observation.Observable;
import observation.Observer;
import simulator.Message;
import simulator.Node;
import userInterface.NodeCreationPanel;


public class NetworkEngineer extends JPanel implements ActionListener, Observer, Observable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private final JFileChooser browser = new JFileChooser();
	File xmlFile = null;
	
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
			System.out.println("Pressed create Node button");
			this.createNode();
		
		} else if(e.getSource() == deleteNodeBtn) {
			System.out.println("Pressed delete Node button");
			this.deleteNode();
			
		} else if(e.getSource() == editNodeBtn) {			
			System.out.println("Pressed edit Node button");
			this.editNode();
			
		} else if(e.getSource() == newFileBtn) {
			System.out.println("Pressed new File button");
			this.createNetworkConfigFile();
		}
	}
	
	
	private void editNode() {
		if(this.configFile == null) {
			JOptionPane.showMessageDialog(null, "You cannot edit a node in a non-existant netowrk Config File. Create a Network Config File or load one to continue.");
			return;
		}
		int index = Integer.valueOf(JOptionPane.showInputDialog ( "Enter INDEX of the node you want to edit:" ));
		/* Alright Brandon & Colby this is where stuff gets real. How in-depth do we want this? We didn't exactly design
		 * for this explicitly.. I mean, sure its a use case that's been floating around since the beginning
		 * but we never talked about what the interface for this use case would look like? Will it be simple?
		 * will it allow configuration of ALL fields of a node? Will it be mighty? WILL IT?!
		 */
		JOptionPane.showMessageDialog(null, "This feature is unimplemented because we did not carry out our design with the intent to\n"
				+ "*make* network config file--it was assummed that the XML file would be supplied.\n"
				+ "Therefore, not implementing this use case is more in line with our design choices than implementing it.\n"
				+ "Oh, the irony.");
	}

	private void createNetworkConfigFile() {
		try {
			
			browser.setSelectedFile(xmlFile);
			
			if (browser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				this.configFile = new NetworkConfig(null);
				FileWriter writer = new FileWriter(browser.getSelectedFile());
				String header = "<?xml version=\"1.0\"?>" + "\n<networkconfig version=\"1.0\">";
				String footer = "</networkconfig>";
				writer.append(header);
				writer.append(footer);
				
				System.out.println("Network Configuration saved as XML file " + browser.getSelectedFile().getAbsolutePath());
                writer.flush();
                writer.close();
				
				JOptionPane.showMessageDialog(null, "Network Configuration saved here: " + browser.getSelectedFile().getAbsolutePath() 
						+ "\nBe sure to load it to add nodes to it.");

			}else if(browser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION) {
				System.out.println("Operation Cancelled");
            }
		} catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "File could not be written, try again.");
        }
	}

	private void deleteNode() {
		if(this.configFile == null) {
			JOptionPane.showMessageDialog(null, "You cannot delete a node from... nothing. Load a Network Config File to start deleting.");
			return;
		}
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
			if(this.configFile == null) {
				JOptionPane.showMessageDialog(null, "You cannot add a node to.. nothing. Create a Network Config File or load one to continue.");
				return;
			}
			
			NodeCreationPanel nodeCreationPanel = new NodeCreationPanel();
			
			int choice = JOptionPane.showConfirmDialog(null, nodeCreationPanel, "Enter new node info", JOptionPane.OK_CANCEL_OPTION);
			
			if (choice == JOptionPane.OK_OPTION) {
				Node newNode = nodeCreationPanel.getCreatedNode();
				this.configFile.add(newNode);
				System.out.println("Node " + newNode.getName() + " created!");
			} else  {
				System.out.println("Node creation cancelled.");
			}

		      
			
//			JOptionPane.showMessageDialog(null, "This feature is unimplemented because we did not carry out our design with the intent to\n"
//					+ "*make* network config file--it was assummed that the XML file would be supplied due to the numerous nodes that could be in a network.\n"
//					+ "Therefore, not implementing this use case is more in line with our design choices than implementing it.\n"
//					+ "Oh, the irony.");
			
			//NOTIFY ANY OBSERVER THAT MY NETWORK CONFIG FILE HAS CHANGED.
			//this.notifyObservers();
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "You did not fill out the new Node properly. Bummer.");
		}

	}

	@Override
	public void update(NetworkConfig netConfigFile) {
		this.configFile = netConfigFile;
		System.out.println("Network engineer successfully loaded the network config file");
		
	}

	@Override
	public void update(Message message, int flag) {
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
