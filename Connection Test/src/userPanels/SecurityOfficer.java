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

public class SecurityOfficer extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;

	JButton approveBtn = null;
	JButton denyBtn = null;
	NetworkConfig configFile = null;
	
	public SecurityOfficer() {
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

		approveBtn = new JButton("Approve Config File");
		approveBtn.addActionListener(this);
		add(approveBtn, "2, 4, left, top");
		
		denyBtn = new JButton("Deny Config File");
		denyBtn.addActionListener(this);
		add(denyBtn, "4, 4, left, top");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.configFile == null) {
			JOptionPane.showMessageDialog(null, "Please load a Network Config File. You haven't loaded one yet.");
			return;
		}
		
		if(e.getSource() == approveBtn ) {
			this.configFile.setApproval(true);
			JOptionPane.showMessageDialog(null, "This Network Configuration File has been APPROVED.");
			
		} else if(e.getSource() == denyBtn) {
			this.configFile.setApproval(false);
			JOptionPane.showMessageDialog(null, "This Network Configuration File has been DENIED.");
			
		}
	}

	@Override
	public void update(NetworkConfig netConfigFile) {
		this.configFile = netConfigFile;
	}

	@Override
	public void update(Message message, int flag) {
		// This class does not need to see messages
		
	}
}

