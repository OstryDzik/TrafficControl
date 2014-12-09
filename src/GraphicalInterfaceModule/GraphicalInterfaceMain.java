package GraphicalInterfaceModule;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class GraphicalInterfaceMain
{
	public static final String DEFAULT_IP = new String("0.0.0.0");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					InterfaceFrame frame = new InterfaceFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}

				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e)
				{
					e.printStackTrace();
					throw new RuntimeException();
				}
			}
		});
	}
}
