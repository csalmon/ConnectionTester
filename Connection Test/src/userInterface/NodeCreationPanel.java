package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import enums.NodeState;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import simulator.ContactInfo;
import simulator.Initiator;
import simulator.Listener;
import simulator.MessageQueue;
import simulator.Node;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class NodeCreationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextField nameField;
	private JTextField eIPField;
	private JTextField iIPField;
	private JTextField conOrgField;
	private JTextField conPersonField;
	private JTextField conPhoneField;
	String name = null;
	String eIP = null;
	String iIP = null;
	String cOrg = null;
	String cPerson = null;
	String cPhone = null;
	JButton addListenerBtn = null;
	JButton addInitiatorBtn = null;
	JButton showListInitiatBtn = null;
	JButton btnSaveNode = null;
	private JTextField listIPField;
	private JTextField listPortField;
	private JTextField initFromPortField;
	private JTextField initToIPField;
	private JTextField initToPortField;
	
	Node newNode = null;
	NodeState state = null;
	
	public NodeCreationPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(110dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(130dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		addLabels();
		addtextFields();
		addButtons();
		
		this.newNode = new Node("1.0");
		System.out.println("Created a new node for the node creation process. File version sent: " + newNode.getFileVersion() );
	}
	
	private void addLabels() {
		JLabel lblName = new JLabel("Name:");
		add(lblName, "2, 2, right, default");
		
		JLabel lblExternalIp = new JLabel("External IP:");
		add(lblExternalIp, "2, 4, right, default");
		
		JLabel lblInternalIp = new JLabel("Internal IP:");
		add(lblInternalIp, "2, 6, right, default");
		
		JLabel lblOrg = new JLabel("Contact Organization:");
		add(lblOrg, "2, 8, right, top");
		
		JLabel lblNewLabel = new JLabel("Contact Person:");
		add(lblNewLabel, "2, 10, right, default");
		
		JLabel lblContactPhone = new JLabel("Contact Phone:");
		add(lblContactPhone, "2, 12, right, default");
		
		JLabel lblHoverOverThe = new JLabel("*Hover over each word below for an explanation of it*");
		lblHoverOverThe.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHoverOverThe.setForeground(Color.RED);
		add(lblHoverOverThe, "4, 16");
		
		JLabel lblListenerInformation = new JLabel("Listener Information");
		lblListenerInformation.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblListenerInformation, "2, 18");
		
		JLabel lblNewLabel_1 = new JLabel("IP Address:");
		lblNewLabel_1.setToolTipText("This is the listening machine's IP address");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_1, "2, 20, right, default");
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setToolTipText("This is the port that listening machine will listen on.");
		lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblPort, "2, 22, right, default");
		
		JLabel lblInitiatorInformation = new JLabel("Initiator Information");
		lblInitiatorInformation.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblInitiatorInformation, "2, 26");
		
		JLabel lblPorttoSend = new JLabel("Port:");
		lblPorttoSend.setToolTipText("This is the port that THIS node will send the message out of.");
		lblPorttoSend.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblPorttoSend, "2, 28, right, default");
		
		JLabel lblIpofListening = new JLabel("IP Address:");
		lblIpofListening.setToolTipText("This is the IP of the machine that will receive the message.");
		lblIpofListening.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblIpofListening, "2, 30, right, default");		
		
		JLabel lblPort_1 = new JLabel("Port:");
		lblPort_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPort_1.setToolTipText("This is the port that the listening machine will listen for the message on.");
		add(lblPort_1, "2, 32, right, default");
		
		JLabel lblpressTheSave = new JLabel("*Press the Save Button below before pressing OK*");
		lblpressTheSave.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblpressTheSave.setForeground(Color.RED);
		add(lblpressTheSave, "4, 40");
	}
	
	private void addtextFields() {
		nameField = new JTextField();
		add(nameField, "4, 2");
		nameField.setColumns(5);

		eIPField = new JTextField();
		add(eIPField, "4, 4");
		eIPField.setColumns(5);

		iIPField = new JTextField();
		add(iIPField, "4, 6");
		iIPField.setColumns(5);

		conOrgField = new JTextField();
		add(conOrgField, "4, 8");
		conOrgField.setColumns(5);

		conPersonField = new JTextField();
		add(conPersonField, "4, 10");
		conPersonField.setColumns(5);

		conPhoneField = new JTextField();
		add(conPhoneField, "4, 12");
		conPhoneField.setColumns(5);
		
		listIPField = new JTextField();
		add(listIPField, "4, 20");
		listIPField.setColumns(10);
		
		listPortField = new JTextField();
		add(listPortField, "4, 22");
		listPortField.setColumns(10);
		
		
		initFromPortField = new JTextField();
		add(initFromPortField, "4, 28");
		initFromPortField.setColumns(10);
		
		initToIPField = new JTextField();
		add(initToIPField, "4, 30");
		initToIPField.setColumns(10);
		
		initToPortField = new JTextField();
		add(initToPortField, "4, 32");
		initToPortField.setColumns(10);
		
	}
	
	private void addButtons() {	
		addInitiatorBtn = new JButton("ADD INITIATOR");
		addInitiatorBtn.addActionListener(this);
		add(addInitiatorBtn, "4, 36");
		
		addListenerBtn = new JButton("ADD LISTENER");
		addListenerBtn.addActionListener(this);
		add(addListenerBtn, "4, 24");
		
		showListInitiatBtn = new JButton("Show Current Listeners & Initiators");
		showListInitiatBtn.addActionListener(this);
		add(showListInitiatBtn, "4, 38");
		
		btnSaveNode = new JButton("SAVE NODE INFO");
		btnSaveNode.addActionListener(this);
		add(btnSaveNode, "4, 42");

	}
	
	private void addListener() {
		InetAddress convertedExtIP = null;
		InetAddress convertedListIP = null;
		int port = 0;
		try {
			convertedExtIP = InetAddress.getByName(eIPField.getText());
			convertedListIP = InetAddress.getByName(listIPField.getText());
			port = Integer.parseInt(listPortField.getText());
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Didn't work. Reasons:\n- Didn't have an external IP typed in the initial info or\n- Didn't have a Listener IP typed");
			return;
		}
			
		InetSocketAddress initiatorAddress = new InetSocketAddress(convertedExtIP, port);
		InetSocketAddress listenerAddress = new InetSocketAddress(convertedListIP, port);
		
		Listener newListener = new Listener(initiatorAddress, listenerAddress, new MessageQueue());
		this.newNode.addListener(newListener);
		JOptionPane.showMessageDialog(null, "Listener added!");
	}
	
	private void addInitiator() {
		InetAddress convertedExtIP = null;
		InetAddress convertedListIP = null;
		int initPort = 0;
		int listPort = 0;
		try {
			convertedExtIP = InetAddress.getByName(eIPField.getText());
			convertedListIP = InetAddress.getByName(initToIPField.getText());
			initPort = Integer.parseInt(initFromPortField.getText());
			listPort = Integer.parseInt(initToPortField.getText());
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "At least one of the IPs in the initiator data entry fields was not resolvable.");
			return;
		}
			
		InetSocketAddress initiatorAddress = new InetSocketAddress(convertedExtIP, initPort);
		InetSocketAddress listenerAddress = new InetSocketAddress(convertedListIP, listPort);
		
		Initiator newInitiator = new Initiator(initiatorAddress, listenerAddress);
		this.newNode.addInitiator(newInitiator);
		JOptionPane.showMessageDialog(null, "Initiator added!");
			
	}
	
	private void showListenersAndInitiators() {
		JOptionPane.showMessageDialog(null, "Well, aren't you thorough.\nI didn't want to do this, so it is not done.\n"
				+ "To verify Listener & Initiator, save the file after adding the node and see if the initiator/listener\n"
				+ "is there. I assure you that it will be.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addListenerBtn ) {			
			System.out.println("Pressed ADD LISTENER button");
			if(!listIPField.getText().trim().equals("") || !listPortField.getText().trim().equals("") ) {
				this.addListener();
			} else {
				JOptionPane.showMessageDialog(null, "Hey, you didn't fill that Listener out right. No Listener added.");
			}
			listIPField.setText("");
			listPortField.setText("");
			
		} else if(e.getSource() == addInitiatorBtn) {
			System.out.println("Pressed ADD INITIATOR button");
			if(!initFromPortField.getText().trim().equals("") || !initToIPField.getText().trim().equals("") || !initToPortField.getText().trim().equals("")) {
				this.addInitiator();
			} else {
				JOptionPane.showMessageDialog(null, "Hey, you didn't fill that Initiator out right. No Initiator added.");
			}
			initFromPortField.setText("");
			initToIPField.setText("");
			initToPortField.setText("");
			
		} else if(e.getSource() == showListInitiatBtn) {			
			System.out.println("Pressed Show Current Listeners & Initiators button");
			this.showListenersAndInitiators();
			
		} else if(e.getSource() == btnSaveNode) {
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setOrganization(conOrgField.getText());
			contactInfo.setContactPerson(conPersonField.getText());
			contactInfo.setContactPhone(conPhoneField.getText());
			
			this.newNode.setContactInfo(contactInfo);
			this.newNode.setName(nameField.getText());
			
			InetAddress convertedExtIP = null;
			InetAddress convertedIntIP = null;
			try {
				convertedExtIP = InetAddress.getByName(eIPField.getText());
				convertedIntIP = InetAddress.getByName(iIPField.getText());
			} catch (UnknownHostException ex) {
				JOptionPane.showMessageDialog(null, "The external/internal IP was not resolvable.");
				return;
			}
			
			this.newNode.addExternalIP(convertedExtIP);
			this.newNode.addInternalIP(convertedIntIP);
			this.newNode.setState(state.ACTIVE);
			JOptionPane.showMessageDialog(null, "Node saved!");
		}
		
	}
	
	public Node getCreatedNode() {
		System.out.println(this.newNode.getName() + " " + this.newNode.getContactInfo().toString());
		return this.newNode;
	}
	

}
