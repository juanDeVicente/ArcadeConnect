package frame;

import frame.fileManager.FileManagerBIOS;
import frame.fileManager.FileManagerROMS;
import properties.Properties;
import ssh.SSH;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame
{

	/**
	 *
	 */
	private static final long serialVersionUID = -7692293339993053523L;
	private JPanel contentPane;
	private GridBagLayout gbl_contentPane;
	private boolean maximized = false;

	/**
	 * Create the frame.
	 */
	public Frame()
	{
		this.setTitle("Arcade connect");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize((Integer) Properties.values.get("width"), (Integer) Properties.values.get("height"));
		if ((Boolean) Properties.values.get("maximized"))
		{
			this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			this.setVisible(true);
			this.setVisible(false);
			this.maximized = true;
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);
		setContentPane(contentPane);
		gbl_contentPane = new GridBagLayout();
		this.changeSize(this.getWidth(), this.getHeight());
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JPanel panels = new JPanel();
		GridBagConstraints gbc_panels = new GridBagConstraints();
		gbc_panels.gridwidth = 4;
		gbc_panels.gridheight = 3;
		gbc_panels.fill = GridBagConstraints.BOTH;
		gbc_panels.gridx = 0;
		gbc_panels.gridy = 0;
		contentPane.add(panels, gbc_panels);
		panels.setLayout(new GridLayout(1, 2, 0, 0));

		Panel romsPanel = new Panel("ROMS", "/home/pi/RetroPie/roms", new FileManagerROMS());
		panels.add(romsPanel);

		Panel biosPanel = new Panel("BIOS", "/home/pi/RetroPie/BIOS", new FileManagerBIOS());
		panels.add(biosPanel);

		JButton btnApplyChanges = new JButton("Apply changes");
		btnApplyChanges.addActionListener
		(
			e ->
			{
				SSH.connection.restartArcade();
			}
		);
		GridBagConstraints gbc_btnUploadContent = new GridBagConstraints();
		gbc_btnUploadContent.gridwidth = 2;
		gbc_btnUploadContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUploadContent.anchor = GridBagConstraints.NORTH;
		gbc_btnUploadContent.gridx = 1;
		gbc_btnUploadContent.gridy = 3;
		contentPane.add(btnApplyChanges, gbc_btnUploadContent);

		this.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent componentEvent)
			{
				JFrame windowRef = (JFrame) componentEvent.getComponent();
				Frame.this.changeSize(windowRef.getWidth(), windowRef.getHeight());
			}
		});
		this.addWindowListener
		(
			new WindowListener()
			{
				@Override
				public void windowClosing(WindowEvent e)
				{
					if (!Frame.this.maximized)
					{
						Rectangle bounds = Frame.this.getBounds();
						Properties.values.put("width", bounds.width);
						Properties.values.put("height", bounds.height);
					}
					Properties.values.save();
					SSH.connection.closeClient();
				}

				@Override
				public void windowIconified(WindowEvent e)
				{
					// TODO Auto-generated method stub
				}

				@Override
				public void windowDeiconified(WindowEvent e)
				{
					// TODO Auto-generated method stub
				}

				@Override
				public void windowActivated(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowClosed(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeactivated(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowOpened(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}
			}
		);
		this.addWindowStateListener
		(
			e ->
			{
				int oldState = e.getOldState();
				int newState = e.getNewState();

				if (((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0))
				{
					Rectangle bounds = Frame.this.getBounds();
					Frame.this.changeSize(bounds.width, bounds.height);
					Properties.values.put("maximized", true);
					this.maximized = true;
				}
				else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0)
				{
					Frame.this.changeSize((Integer) Properties.values.get("width"), (Integer) Properties.values.get("height"));
					Properties.values.put("maximized", false);
					Rectangle bounds = Frame.this.getBounds();
					Properties.values.put("width", bounds.width);
					Properties.values.put("height", bounds.height);
					this.maximized = false;
				}
			}
		);
	}

	private void changeSize(int width, int height)
	{
		this.setSize(width, height);
		int sizeX = width / 4;
		int sizeY = height / 4;
		this.gbl_contentPane.columnWidths = new int[]{sizeX, sizeX, sizeX, sizeX - 20, 0}; //-20 is very important, dont touch it
		this.gbl_contentPane.rowHeights = new int[]{sizeY, sizeY, sizeY, sizeY, 0};
	}
}
