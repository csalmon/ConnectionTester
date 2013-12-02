package userInterface;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import fileIO.NetworkConfig;

import javax.swing.JLabel;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import observation.Observer;

import simulator.Node;
import simulator.UIConsoleAppender;

public class Console extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	public ArrayList<String> textLines;
	public JList textList;
	private Logger rtLogger = Logger.getRootLogger();
	public UIConsoleAppender appender;
	public String PLACE_HOLDER_STR = "                                                                                                                                                            ";

	public Console() {
		setBounds(279, 380, 495, 155);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel lblConsole = new JLabel("Console");
		add(lblConsole, "2, 2");
		
		textLines = new ArrayList<String>();
			
		textLines.add(PLACE_HOLDER_STR);
//		textLines.add(" ");
//		textLines.add(" ");
//		textLines.add(" ");
//		textLines.add("5");
//		textLines.add("6");
//		textLines.add("7");
//		textLines.add("8");
		
		textList = new JList();
		textList.setVisibleRowCount(7);
		JScrollPane scrollPane = new JScrollPane(textList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		//textLines.add("How'd this get here?");
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
		//System.out.println("\r\n!!!!!!!!! Console ADD Was Called !!!!!!!!!!!\r\n");
		textLines.add(strArg);
		textList.setListData(textLines.toArray());
		
		int size = textList.getModel().getSize();
		textList.ensureIndexIsVisible(size-1);
	}
	
	public void update(NetworkConfig netConfigFile)
	{
		System.out.println("\r\n!!!!!!!!! Console Update Was Called !!!!!!!!!!!\r\n");
		// Intentionally left blank for now...
	}

	@Override
	public void update(Node node, int flag) {
		// Intentionally left blank for now...
	}
	
}
