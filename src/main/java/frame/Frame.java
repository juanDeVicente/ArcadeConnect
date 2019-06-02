package frame;

import properties.Properties;

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

		JPanel romsPanel = new Panel("ROMS");
		panels.add(romsPanel);

		JPanel biosPanel = new Panel("BIOS");
		panels.add(biosPanel);

		JButton btnUploadContent = new JButton("Upload content");
		btnUploadContent.addActionListener
		(
			e ->
			{

			}
		);
		GridBagConstraints gbc_btnUploadContent = new GridBagConstraints();
		gbc_btnUploadContent.gridwidth = 2;
		gbc_btnUploadContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUploadContent.anchor = GridBagConstraints.NORTH;
		gbc_btnUploadContent.gridx = 1;
		gbc_btnUploadContent.gridy = 3;
		contentPane.add(btnUploadContent, gbc_btnUploadContent);

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
					if ((e.getNewState() & Frame.MAXIMIZED_BOTH) != 0)
					{
						Rectangle bounds = Frame.this.getBounds();
						Properties.values.set("width", bounds.width);
						Properties.values.set("height", bounds.height);
					}
					Properties.values.save();
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
					Properties.values.set("maximized", true);
				} else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0)
				{
					Frame.this.changeSize((Integer) Properties.values.get("width"), (Integer) Properties.values.get("height"));
					Properties.values.set("maximized", false);
				}
			}
		);
	}

	private void changeSize(int width, int height)
	{
		this.setSize(width, height);
		int sizeX = width / 4;
		int sizeY = height / 4;
		gbl_contentPane.columnWidths = new int[]{sizeX, sizeX, sizeX, sizeX - 20, 0}; //-20 is very important, dont touch it
		gbl_contentPane.rowHeights = new int[]{sizeY, sizeY, sizeY, sizeY, 0};
	}
}
