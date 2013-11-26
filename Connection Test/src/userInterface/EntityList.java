package userInterface;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;

public class EntityList extends JPanel {
	private static final long serialVersionUID = 1L;

	public EntityList() {
		setSize(259, 415);
		setLocation(10, 117);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblListOfNodes = new JLabel("List of Nodes");
		add(lblListOfNodes, "2, 2");
	}

}
