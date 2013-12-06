package userInterface;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JLabel;
import org.apache.log4j.Logger;

import fileIO.NetworkConfig;
import observation.Observer;
import simulator.Message;
import simulator.UIConsoleAppender;

public class Console extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> textLines;
	private JList textList;
	private JScrollPane scrollPane;
	private Logger rtLogger = Logger.getRootLogger();
	private UIConsoleAppender appender;
	private String PLACE_HOLDER_STR = "                                                                                                                                                            ";

	public Console() {
		setBounds(279, 380, 495, 155);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel lblConsole = new JLabel("Console");
		add(lblConsole, "2, 2");
		
		textLines = new ArrayList<String>();
		textLines.add(PLACE_HOLDER_STR);
		
		textList = new JList();
		textList.setVisibleRowCount(7);
		scrollPane = new JScrollPane(textList);
		scrollPane.setDoubleBuffered(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		textList.setListData(textLines.toArray());
		
		int size = textList.getModel().getSize();
		textList.ensureIndexIsVisible(size-1);
		
		initAppender();
	}

	public void initAppender()
	{
		appender = (UIConsoleAppender) rtLogger.getAppender("uiConsole");
		appender.setConsole(this);
	}
	
	public void add(String strArg)
	{
		textLines.add(strArg);
		this.update();
	}
	
	public void update() {
		textList.setListData(textLines.toArray());
		
		int size = textList.getModel().getSize();
		textList.ensureIndexIsVisible(size-1);
		
		scrollPane.setViewportView(textList);
	}
	
	@Override
	public void update(NetworkConfig netConfigFile)
	{
		// Intentionally left blank. Console does not do anything with the network config file.
	}

	@Override
	public void update(Message message, int flag) {
		// Intentionally left blank for now
	}
	
}
