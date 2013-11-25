package driver;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Choice;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import userInterface.HomeBlock;

public class LoginBlock extends JFrame implements ItemListener {
	private static final long serialVersionUID = 1L;
	Choice loginOptions = new Choice();
	static int chosenOption;
	static HomeBlock home = null;
	static LoginBlock login = new LoginBlock();

	public LoginBlock() {
		getContentPane().setLayout(null);
		setTitle("Login");
		setBounds(700, 200, 300, 225);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		loginOptions.setBounds(70, 74, 162, 20);
		loginOptions.add("0. Network Engineer");
		loginOptions.add("1. Tester");
		loginOptions.add("2. Security Officer");
		loginOptions.add("3. Manager");
		loginOptions.addItemListener(this);
		getContentPane().add(loginOptions);
		
		JLabel instructionsLbl = new JLabel("Please Choose Your Role:");
		instructionsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		instructionsLbl.setBounds(70, 54, 162, 14);
		getContentPane().add(instructionsLbl);
			
	}

	public static void main(String[] args) {
		login.start();
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		chosenOption = Integer.valueOf( loginOptions.getSelectedItem().substring(0, 1) );
		System.out.println("User chose: " + chosenOption);
		home = new HomeBlock(chosenOption);
		login.stop();
		home.start();
	}
	
	public void start() {
		setVisible(true);
	}
	
	public void stop() {
		setVisible(false);
	}

}
