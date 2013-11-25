package userInterface;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;

public class Viewer extends JPanel {

	private static final long serialVersionUID = 1L;
	int numNodesInSimulation = 16;
	final int ROW_START = 4;
	final int COL_START = 4;
	final int POSITION_CHANGE = 4;
	JLabel[][] nodeNameLabels = null;
	JLabel[][] nodeStatusLabels = null;
	
	public Viewer() {
		setSize(495, 252);
		setLocation(279, 117);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblSimulationActivity = new JLabel("Simulation Activity");
		add(lblSimulationActivity, "2, 2");
		
		int size = (int) Math.sqrt(numNodesInSimulation);
		int counter = 0;
		nodeNameLabels = new JLabel[size][size];
		nodeStatusLabels = new JLabel[size][size];
		
		int xCoor = 0; //col
		int yCoor = 0; //row
		//This nested loop adds 16 labels and their states to the Viewer object.
		/*
		for(int row = 0; row < size; row++) {
			xCoor+=POSITION_CHANGE;
			for(int col = 0; col < size; col++) {
				yCoor+=POSITION_CHANGE;
				
				//follows (Column/xcoor, Row/ycoor) convention
				String nameLocation = Integer.toString(xCoor) + ", " + Integer.toString(yCoor);
				String statusLocation = Integer.toString(xCoor) + ", " + Integer.toString(yCoor+2);
				//System.out.println(nameLocation + " " + statusLocation);
				
				JLabel nodeNameLabel = new JLabel("Node " + Integer.toString(counter));
				JLabel nodeStatusLabel = new JLabel("Inactive");
				nodeNameLabels[row][col] = nodeNameLabel;
				nodeStatusLabels[row][col] = nodeStatusLabel;
				
				add( nodeNameLabels[row][col], nameLocation );
				add( nodeStatusLabels[row][col], statusLocation );
				
				counter+=1;
			}
			yCoor = 0;
		}
		 */
	}

}
