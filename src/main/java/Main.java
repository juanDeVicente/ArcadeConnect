import java.awt.EventQueue;

import frame.Frame;

public class Main
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() ->
		{
			try
			{
				Frame frame = new Frame();
				frame.setVisible(true);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
