package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import fileIO.NetworkConfig;
import observation.Observer;
import simulator.Message;
import simulator.Node;

public class Viewer extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	int numNodesInSimulation = 0;
	final int ROW_START = 4;
	final int COL_START = 4;
	final int POSITION_CHANGE = 4;
	ArrayList <JLabel> nodeNameLabels = new ArrayList<JLabel>();
	ArrayList <JLabel> nodeStatusLabels = new ArrayList<JLabel>();
	ArrayList <String> nodeLabelLocations = new ArrayList<String>();
	ArrayList <String> nodeStatusLocations = new ArrayList<String>();
	NetworkConfig configFile = null;
	
	
	public Viewer() {
		setSize(495, 250);
		setLocation(279, 117);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		JLabel lblSimulationActivity = new JLabel("Simulation Activity");
		add(lblSimulationActivity, "2, 2, left, top");
		
		JLabel lblYellow = new JLabel("Red =");
		lblYellow.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblYellow.setForeground(Color.red);
		lblYellow.setBackground(Color.red);
		lblYellow.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblYellow, "6, 2");
		
		JLabel lblClientNode = new JLabel("Client Node");
		lblClientNode.setBackground(Color.red);
		add(lblClientNode, "8, 2");
	 
	}
	
	//I realize this code is a bit of a hack because the magic numbers. I'm a bit stuck thogh because the form layout requires explicit declaration of rows/cols
	//or else it doesn't allow me to use the 'Design' tab at will--I'd have to run the program to see the results of my code each time. Too tedious at the moment.
	private void populateLabelLists() {
		//set all nodes to invisbile
		for (int index = 0; index < nodeNameLabels.size(); index++) {
			nodeNameLabels.get(index).setVisible(false);
			nodeStatusLabels.get(index).setVisible(false);
		}
		
		//this.repaint();
		
		int col = 2;
		int row = 4;
		Node activeNode = this.configFile.getActiveNode();
		for(int index = 0; index < numNodesInSimulation; index++) {
			Node currentNode = this.configFile.get(index);
			System.out.println("Populating VIEWER with this node: " + currentNode.getName());
			
			JLabel nodeNameLabel = new JLabel(currentNode.getName());
			String nodeNameLoc = col + ", " + row;
			
			JLabel nodeStatusLabel = new JLabel("Inactive");
			String nodeStatusLoc = col + ", " + (row+1);
			
			if(!(activeNode == null)) {
				if(currentNode.getNID().compareTo(activeNode.getNID()) == 0) {
					nodeNameLabel.setForeground(Color.red);
					nodeStatusLabel.setForeground(Color.green);
					nodeStatusLabel.setText("ACTIVE");
				}
			}
			nodeNameLabels.add(nodeNameLabel);
			nodeLabelLocations.add(nodeNameLoc);
			nodeStatusLabels.add(nodeStatusLabel);
			nodeStatusLocations.add(nodeStatusLoc);
			
			add( nodeNameLabel, nodeNameLoc );
			add( nodeStatusLabel, nodeStatusLoc );

			if( (row+3) > 17) {
				col+=2;
				row = 4;
			}
			
			row+=3;
		}
	}

	@Override
	public void update(NetworkConfig netConfigFile) {
		this.configFile = netConfigFile;
		this.numNodesInSimulation = this.configFile.size();
		System.out.println("Viewer successfully loaded the network config file");
		this.populateLabelLists();
		
	}

	@Override
	public void update(Message message, int flag) {
		ArrayList <UUID> activeUUIDs = message.getNodeIDs();
		
		for(UUID id : activeUUIDs) {
			String activeNodeName = this.configFile.get(id).getName();
			
			for(int index = 0; index < nodeNameLabels.size(); index++) {
				if(nodeNameLabels.get(index).getText().compareTo(activeNodeName) == 0) {
					nodeStatusLabels.get(index).setText("Active");
					nodeStatusLabels.get(index).setForeground(Color.green);
				}
			}
		}
		
		//this.populateLabelLists();
	}

}
