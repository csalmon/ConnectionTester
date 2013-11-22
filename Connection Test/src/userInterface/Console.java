package userInterface;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;

public class Console extends JPanel {
	private static final long serialVersionUID = 1L;

	public Console() {
		setBounds(279, 380, 495, 170);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(318dlu;default)"),},
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblConsole = new JLabel("Console");
		add(lblConsole, "2, 2");
		/*
		JLabel lblNewLabel = new JLabel("New label");
		add(lblNewLabel, "2, 4");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		add(lblNewLabel_1, "2, 6");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		add(lblNewLabel_2, "2, 8");
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		add(lblNewLabel_3, "2, 10");
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		add(lblNewLabel_4, "2, 12");
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		add(lblNewLabel_5, "2, 14");
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		add(lblNewLabel_6, "2, 16");
		*/
	}

}
