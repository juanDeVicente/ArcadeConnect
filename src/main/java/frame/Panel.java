package frame;

import frame.fileManager.IFileManager;
import ssh.SSH;

import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Panel extends JPanel
{

	/**
	 *
	 */
	private static final long serialVersionUID = -170820659799890334L;
	private JScrollPane scrollPane;
	private JTree tree;

	/**
	 * Create the panel.
	 */
	Panel(String panelName, String dirRoot, IFileManager fileManager)
	{
		setLayout(new BorderLayout(0, 0));

		JLabel lblname = new JLabel(panelName);
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
		lblname.setFont(new Font("Consolas", Font.PLAIN, 50));
		add(lblname, BorderLayout.NORTH);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(this.scrollPane, BorderLayout.CENTER);
		this.scrollPane.setViewportBorder(null);
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		createDirectories(dirRoot);

		JButton btnUploadContent = new JButton("Upload " + panelName);
		btnUploadContent.addActionListener(e ->
		{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				fileManager.uploadFile(chooser.getSelectedFile().getAbsolutePath());
				createDirectories(dirRoot);
			}
		});
		add(btnUploadContent, BorderLayout.SOUTH);

	}

	public void createDirectories(String dirRoot)
	{
		this.tree = new JTree(SSH.connection.getDirectories(dirRoot));
		this.tree.setFont(new Font("Tahoma", Font.PLAIN, 30));
		this.scrollPane.setViewportView(tree);
	}

}
