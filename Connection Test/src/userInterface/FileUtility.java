package userInterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import observation.Observable;
import observation.Observer;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import fileIO.NetworkConfig;
import fileIO.XMLWriter;

public class FileUtility extends JPanel implements ActionListener, Observable {
	private static final long serialVersionUID = 1L;
	private final JFileChooser browser = new JFileChooser();
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private NetworkConfig networkConfig = null;
	
	
	JButton loadBtn = null;
	JButton saveBtn = null;
	JLabel fileNameLbl = null;
	File xmlFile = null;
	

	public FileUtility() {
		setSize(259, 115);
		setLocation(10, 11);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("13px"),
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblFileHandler = new JLabel("File Handler: ");
		add(lblFileHandler, "2, 2, left, center");
		
		loadBtn = new JButton("Load");
		loadBtn.addActionListener(this);
		add(loadBtn, "2, 4");
		
		saveBtn = new JButton("Save");
		saveBtn.addActionListener(this);
		add(saveBtn, "4, 4");
		
		fileNameLbl = new JLabel(" ");
		add(fileNameLbl, "4, 2, right, center");
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		
		if(action.getSource() == loadBtn) {
			System.out.println("Load button pressed");
			try {
				if (browser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					xmlFile = browser.getSelectedFile();
					this.networkConfig = new NetworkConfig(xmlFile.getAbsolutePath());
					JOptionPane.showMessageDialog(null, "Loaded " + networkConfig.size() + " nodes from the xml file.");
					
					System.out.println("File chosen and loaded: " + xmlFile.getName());
					System.out.println("File full path: " + xmlFile.toString());
					String newLabelText = xmlFile.getName();
					fileNameLbl.setText(newLabelText);
					
					//NOTIFY ANY OBSERVER THAT MY NETWORK CONFIG FILE HAS CHANGED.
					this.notifyObservers();
					
				}
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "File could not be loaded, try again.");
				ex.printStackTrace();
			}
		} else if(action.getSource() == saveBtn) {
			
			try {
				browser.setSelectedFile(xmlFile);
				if (browser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					FileWriter writer = new FileWriter(browser.getSelectedFile());
					XMLWriter xmlWriter = new XMLWriter();
					String saveStr = xmlWriter.writeXML(this.networkConfig);
					writer.append(saveStr);
					System.out.println("Network Configuration saved as XML file " + browser.getSelectedFile().getAbsolutePath());
	                writer.flush();
	                writer.close();
				}else if(browser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION) {
					System.out.println("Operation Cancelled");
	            }
			} catch (Exception ex) {
	            JOptionPane.showMessageDialog(null, "File could not be written, try again.");
	        }

		}
			
	}

	public NetworkConfig getNetworkConfig() {
		return(this.networkConfig);
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
		
	}

	@Override
	public void notifyObservers() {
		for (Observer ob : observers) {
			System.out.println("Notifying Observers about network config file change");
            ob.update(this.networkConfig);
		}
		
	}
}
