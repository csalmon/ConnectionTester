package userInterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class EntityList extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;
	ArrayList <JLabel> nodeLabels = new ArrayList<JLabel>();
	NetworkConfig configFile = null;

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

	private void populateEntityList() {
		int column = 2;
		int row = 4;
		for (int index = 0; index < this.configFile.size(); index++) {
			Node currentNode = this.configFile.get(index);
            System.out.println("Populating entity list with this node: " + currentNode.getName());
            JLabel nodeLabel = new JLabel(currentNode.getName());
            String labelLocation = Integer.toString(column) + ", " + Integer.toString(row);

            row+=2;
            nodeLabels.add(nodeLabel);
            this.add(nodeLabel, labelLocation);
		}

	}
	
	@Override
	public void update(NetworkConfig netConfigFile) {
		this.configFile = netConfigFile;
		this.populateEntityList();
		
	}

}
