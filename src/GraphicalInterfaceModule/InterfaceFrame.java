package GraphicalInterfaceModule;

import Common.TempMockupManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class InterfaceFrame extends JFrame
{

    private JPanel contentPane;
    private WorldPanel worldPanel;
    private InfoPanel infoPanel;

    private boolean connectedToServer;
    private boolean simConnected;
    private boolean ctrlConnected;
    private boolean simAuto;
    private int minCarGenInterval;
    private int maxCarGenInterval;


    /**
     * Create the frame.
     */
    public InterfaceFrame()
    {
        connectedToServer = false;
        simConnected = false;
        ctrlConnected = false;
        simAuto = false;
        minCarGenInterval = 0;
        maxCarGenInterval = 0;
        setMinimumSize(new Dimension(240, 290));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setTitle("Graphical User InterfaceFrame Module");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 0));
        contentPane.add(new Label(""), BorderLayout.PAGE_START);
        contentPane.add(new Label(""), BorderLayout.PAGE_END);
        WindowListener windowAdapter = new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
            }
        };

        this.addWindowListener(windowAdapter);

//      Panel informacyjny
        infoPanel = new InfoPanel();
        contentPane.add(infoPanel, BorderLayout.LINE_START);
//      Panel z widokiem świata
        worldPanel = new WorldPanel();
        contentPane.add(worldPanel, BorderLayout.CENTER);
        worldPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        worldPanel.setLayout(new BorderLayout(0, 0));
//      Panel kontrolny
        final JPanel controlPanel = new JPanel(new GridLayout(0,1,8,8));
        contentPane.add(controlPanel, BorderLayout.LINE_END);
        final JButton btnStart = new JButton("Switch Lights");
        controlPanel.add(btnStart);
        final JButton btnConnect = new JButton("Connect to server");
        controlPanel.add(btnConnect);
        final JButton btnDisconnectSim = new JButton("Disconnect simulation");
        final JButton btnDisconnectCtrl = new JButton("Disconnect lights controller");
        final JButton btnSetMinInterval = new JButton("Set minimum car gen time");
        final JButton btnSetMaxInterval = new JButton("Set maximum car gen time");
        final JButton btnSwitchSim = new JButton("Auto Sim Mode");
        controlPanel.add(btnDisconnectCtrl);
        controlPanel.add(btnDisconnectSim);
        controlPanel.add(btnSwitchSim);
        controlPanel.add(btnSetMaxInterval);
        controlPanel.add(btnSetMinInterval);

        btnStart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                worldPanel.update(TempMockupManager.getInstance().getRandomMockup());
            }
        });

        btnConnect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                if(!connectedToServer)
                {
                    //connect method TODO
                    connectedToServer = true;
                    btnConnect.setText("Disconnect from server");
                }
                else
                {
                    //sc method TODO
                    connectedToServer = false;
                    btnConnect.setText("Connect to server");
                }
            }
        });

        btnSwitchSim.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                if(!simAuto)
                {
                    //switch sim mode method TODO
                    simAuto = true;
                    btnSwitchSim.setText("Manual Sim Mode");
                }
                else
                {
                    //switch sim mode method TODO
                    simAuto = false;
                    btnSwitchSim.setText("Auto Sim Mode");
                }
            }
        });

    }
}