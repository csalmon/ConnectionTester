package userPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import fileIO.NetworkConfig;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import observation.Observer;
import simulator.Message;
import simulator.Simulation;

public class Tester extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;

	Simulation simulation = null;
	JButton runSimBtn = null;
	JButton stopSimBtn = null;
	boolean simulationStarted = false;
	Thread simulationThread = null;
	NetworkConfig configFile = null;
	
	public Tester(Simulation simulation) {
		setSize(300, 100);
		setVisible(true);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("99px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel operationsLbl = new JLabel("Operations");
		operationsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		add(operationsLbl, "2, 2");

		runSimBtn = new JButton("Run Simulation");
		runSimBtn.addActionListener(this);
		add(runSimBtn, "2, 4, left, top");
		
		stopSimBtn = new JButton("Stop Simulation");
		stopSimBtn.addActionListener(this);
		add(stopSimBtn, "4, 4, left, top");
		
		this.simulation = simulation;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 
		if(e.getSource() == runSimBtn ) {
			
			if(this.configFile == null) {
				JOptionPane.showMessageDialog(null, "Please load a Network Config File. You haven't loaded one yet.");
				return;
			}
			
			if(simulationThread == null || simulationThread.getState() == Thread.State.NEW) {
				simulation.setActiveNode(this.configFile.getActiveNode());
				simulation.setNumNodes(this.configFile.size());
				simulationThread = new Thread(simulation);
				simulationThread.start();
			
			} else {
				JOptionPane.showMessageDialog(null, "You pressed the start button but a simulation had already been ran." + 
						"\nKilling previous simulation. Please run simulation again");
				System.out.println("You pressed the start button but a simulation had already been ran." + 
						"\nKilling previous simulation. Please run simulation again");
				simulationThread.interrupt();
				simulationThread = null;
				
			}
			
		} else if(e.getSource() == stopSimBtn) {
			
			if(simulationThread == null) {
				JOptionPane.showMessageDialog(null, "You pressed the stop button but there was no simulation running. No effect.");
				System.out.println("You pressed the stop button but there was no simulation running. No effect.");
			} else {
				JOptionPane.showMessageDialog(null, "You pressed the stop button. Simulation was running or it had run recently. Interrupting simulation or cleaning up previously run simulation.");
				System.out.println("You pressed the stop button. Simulation was running or it had run recently. Interrupting simulation or cleaning up previously run simulation.");
				simulationThread.interrupt();
				simulationThread = null;
			}
		}
	}

	@Override
	public void update(NetworkConfig netConfigFile) {
		this.configFile = netConfigFile;
	}

	@Override
	public void update(Message message, int flag) {
	}
}
