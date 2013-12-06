package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import simulator.Node;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

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
	private JTextField listIPField;
	private JTextField listPortField;
	private JTextField initFromPortField;
	private JTextField initToIPField;
	private JTextField initToPortField;
	
	Node newNode = null;
	
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

	}
	
	private void addListener() {
		
	}
	
	private void addInitiator() {
			
	}
	
	private void showListenersAndInitiators() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addListenerBtn ) {			
			System.out.println("Pressed ADD LISTENER button");
			this.addListener();
		
		} else if(e.getSource() == addInitiatorBtn) {
			System.out.println("Pressed ADD INITIATOR button");
			this.addInitiator();
			
		} else {			
			System.out.println("Pressed Show Current Listeners & Initiators button");
			this.showListenersAndInitiators();
			
		}
		
	}
	
	public Node getCreatedNode() {
		return null;
	}
	

}
