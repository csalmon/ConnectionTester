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
import simulator.ContactInfo;
import simulator.Message;
import simulator.Node;

public class Manager extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;

	JButton editInfoBtn = null;
	JButton showInfoBtn = null;
	NetworkConfig configFile = null;
	
	public Manager() {
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

		editInfoBtn = new JButton("Edit Information");
		editInfoBtn.addActionListener(this);
		//add(editInfoBtn, "2, 4, left, top");
		
		showInfoBtn = new JButton("Show Information");
		showInfoBtn.addActionListener(this);
		add(showInfoBtn, "4, 4, left, top");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.configFile == null) {
			JOptionPane.showMessageDialog(null, "Please load a Network Config File. You haven't loaded one yet.");
			return;
		}
		
		if(e.getSource() == editInfoBtn ) {
			JOptionPane.showMessageDialog(null, "This functionality is not implemented.");
			
			
			
		} else if(e.getSource() == showInfoBtn) {
			try {
				Node activeNode = this.configFile.getActiveNode();
				ContactInfo contactInfo = activeNode.getContactInfo();
				JOptionPane.showMessageDialog(null, "Contact Info: " + contactInfo.toString());
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "The client/node you are running this software on is not found in the Network Config File.");
			}
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

