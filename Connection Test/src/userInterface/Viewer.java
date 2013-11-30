package userInterface;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import fileIO.NetworkConfig;

import javax.swing.JLabel;

import observation.Observer;
import simulator.Node;

public class Viewer extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	int numNodesInSimulation = 0;
	final int ROW_START = 4;
	final int COL_START = 4;
	final int POSITION_CHANGE = 4;
	ArrayList <JLabel> nodeNameLabels = new ArrayList<JLabel>();
	ArrayList <JLabel> nodeStatusLabels = new ArrayList<JLabel>();
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
	 
	}
	
	//I realize this code is a bit of a hack because the magic numbers. I'm a bit stuck thogh because the form layout requires explicit declaration of rows/cols
	//or else it doesn't allow me to use the 'Design' tab at will--I'd have to run the program to see the results of my code each time. Too tedious at the moment.
	private void populateLabelList() {
		int col = 2;
		int row = 4;
		for(int index = 0; index < this.numNodesInSimulation; index++) {
			Node currentNode = this.configFile.get(index);
			JLabel nodeNameLabel = new JLabel(currentNode.getName());
			JLabel nodeStatusLabel = new JLabel("Inactive");
			nodeNameLabels.add(nodeNameLabel);
			nodeStatusLabels.add(nodeStatusLabel);
			String nodeNameLoc = col + ", " + row;
			String nodeStatusLoc = col + ", " + (row+1);
			
			System.out.println(currentNode.getName() + " name Location: " + nodeNameLoc + " status Location: " + nodeStatusLoc);
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
		this.populateLabelList();
		
	}

	@Override
	public void update(Node node, int flag) {
		// Intentionally left blank for now...
		
	}

}
