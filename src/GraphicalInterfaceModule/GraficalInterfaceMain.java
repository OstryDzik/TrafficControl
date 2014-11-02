package GraphicalInterfaceModule;

import java.awt.*;

public class GraficalInterfaceMain
{
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
            }
        });
    }
}
