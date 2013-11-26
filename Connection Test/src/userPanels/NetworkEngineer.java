package userPanels;

import javax.swing.JPanel;
import javax.swing.JButton;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class NetworkEngineer extends JPanel {
	private static final long serialVersionUID = 1L;

	public NetworkEngineer() {
		setSize(470, 100);
		setVisible(true);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblOperations = new JLabel("Operations");
		lblOperations.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblOperations, "2, 2");
		//JButton btnCreateNode = new JButton("Create Node");
		JButton btnCreateNode = new JButton("Unimplemented");
		add(btnCreateNode, "2, 4, left, top");
		
		//JButton btnDeleteNode = new JButton("Delete Node");
		JButton btnDeleteNode = new JButton("Unimplemented");
		add(btnDeleteNode, "4, 4, left, top");
				
		//JButton btnSetNodeInfo = new JButton("Edit Node");
		JButton btnSetNodeInfo = new JButton("Unimplemented");
		add(btnSetNodeInfo, "6, 4, left, top");

		
	}

}
