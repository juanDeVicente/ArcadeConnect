package frame;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

class Panel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -170820659799890334L;

	/**
	 * Create the panel.
	 */
	Panel(String panelName)
	{
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblname = new JLabel(panelName);
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
		lblname.setFont(new Font("Consolas", Font.PLAIN, 50));
		add(lblname, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTree tree = new JTree();
		tree.setFont(new Font("Tahoma", Font.PLAIN, 30));
		scrollPane.setViewportView(tree);
		
		JButton btnUploadContent = new JButton("Upload " + panelName);
		add(btnUploadContent, BorderLayout.SOUTH);

	}

}
