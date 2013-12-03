package userInterface;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import org.apache.log4j.Logger;

import fileIO.NetworkConfig;
import observation.Observer;
import simulator.Message;
import simulator.Node;

public class EntityList extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	ArrayList <JLabel> nodeLabels = new ArrayList<JLabel>();
	ArrayList <String> nodeLocations = new ArrayList<String>();
	NetworkConfig configFile = null;
	private Logger rtLogger = Logger.getRootLogger();

	public EntityList() {
		setSize(259, 415);
		setLocation(10, 117);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		FormLayout layout = new FormLayout(new ColumnSpec[]{}, new RowSpec[]{});
		int numCols = 8;
		int numRows = 32;
		
		for(int col = 1; col <= numCols; col++)	{
			layout.appendColumn(FormFactory.RELATED_GAP_COLSPEC);
			layout.appendColumn(FormFactory.DEFAULT_COLSPEC );
		}

		for(int row = 1; row <= numRows; row++) {
			layout.appendRow(FormFactory.RELATED_GAP_ROWSPEC);
			layout.appendRow(FormFactory.DEFAULT_ROWSPEC);
		}

		setLayout(layout);
		
		// adding to the panel means its location is "col, row"
		JLabel lblListOfNodes = new JLabel("List of Nodes");
		add(lblListOfNodes, "2, 2");
	}

	private void populateLabelList() {
	
		//set all nodes to invisbile
		for (int index = 0; index < nodeLabels.size(); index++) {
			nodeLabels.get(index).setVisible(false);
		}
		
		//this.repaint();
		
		int column = 2;
		int row = 4;
		//show current/alive nodes
		for (int index = 0; index < this.configFile.size(); index++) {
			Node currentNode = this.configFile.get(index);
			System.out.println("Populating entity list with this node: " + currentNode.getName());
            JLabel nodeLabel = new JLabel("INDEX " + index + ". " + currentNode.getName());
            String nodeLocation = Integer.toString(column) + ", " + Integer.toString(row);

            row+=2;
            nodeLabels.add(nodeLabel);
            nodeLocations.add(nodeLocation);
            this.add(nodeLabel, nodeLocation);
		}

	}

	
	@Override
	public void update(NetworkConfig netConfigFile) {
		this.configFile = netConfigFile;
		System.out.println("EntityList successfully loaded the network config file");		
		this.populateLabelList();
	}

	@Override
	public void update(Message message, int flag) {
		//updating individual nodes does not happen in the Entity List. This method intentionally left blank.
	}

}
