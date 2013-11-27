package userPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import simulator.Node;
import simulator.Simulation;

public class NetworkEngineer extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JButton createNodeBtn = null;
	JButton deleteNodeBtn = null;
	JButton editNodeBtn = null;
	
	public NetworkEngineer() {
		setSize(470, 100);
		setVisible(true);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblOperations = new JLabel("Operations");
		lblOperations.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblOperations, "2, 2");
		createNodeBtn = new JButton("Create Node");
		createNodeBtn.addActionListener(this);
		add(createNodeBtn, "2, 4, left, top");
		
		deleteNodeBtn = new JButton("Delete Node");
		deleteNodeBtn.addActionListener(this);
		add(deleteNodeBtn, "4, 4, left, top");
				
		editNodeBtn = new JButton("Edit Node");
		editNodeBtn.addActionListener(this);
		add(editNodeBtn, "6, 4, left, top");

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource() == createNodeBtn ) {
			System.out.println("pressed create Node button");
			this.createNode();
		
		} else if(e.getSource() == deleteNodeBtn) {
			System.out.println("pressed delete Node button");
		} else if(e.getSource() == editNodeBtn) {
			System.out.println("pressed edit Node button");
		}	
	}
	
	
	private Node createNode() {
		try {
			UUID newUUID = UUID.randomUUID();
			String newName= JOptionPane.showInputDialog ( "Enter node name:" );
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "There was a mistake entering information for the new node. Please try again.");
		}
			
			return null;
		
	}

}
