package GraphicalInterfaceModule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import Model.Module;

public class InterfaceFrame extends JFrame
{

	private static JTextArea console;
    private JPanel contentPane;
    private WorldPanel worldPanel;
    private InfoPanel infoPanel;

    private boolean connectedToServer;
    private boolean simConnected;
    private static boolean ctrlConnected;
    private static boolean simAuto;
    private int minCarGenInterval;
    private int maxCarGenInterval;
    /** Określa czy można wykonać już kolejny krok symulacji */
    public static AtomicBoolean step = new AtomicBoolean(false);

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
        setTitle("Projekt SCZR");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 0));
        WindowListener windowAdapter = new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
            }
        };

        this.addWindowListener(windowAdapter);

        // Panel informacyjny
        infoPanel = new InfoPanel();
        contentPane.add(infoPanel, BorderLayout.LINE_START);
        // Panel z widokiem świata
        worldPanel = new WorldPanel();
        contentPane.add(worldPanel, BorderLayout.CENTER);
        worldPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        worldPanel.setLayout(new BorderLayout(0, 0));
        // Panel kontrolny
        final JPanel controlPanel = new JPanel(new GridLayout(0,1,8,8));
        contentPane.add(controlPanel, BorderLayout.LINE_END);
        final JButton btnConnect = new JButton("Połącz");
        controlPanel.add(btnConnect);
        final JButton btnDisconnectSim = new JButton("Odłącz symulację");
        final JButton btnDisconnectCtrl = new JButton("Odłącz sterownik świateł");
        final JButton btnSetMinInterval = new JButton("Ustaw min. czas gen. sam.");
        final JButton btnSetMaxInterval = new JButton("Ustaw maks. czas gen. sam.");
        final JButton btnSwitchSim = new JButton("Włącz automatyczna symulację");
        final JButton btnNextStep = new JButton("Następny krok");
        controlPanel.add(btnDisconnectCtrl);
        controlPanel.add(btnDisconnectSim);
        controlPanel.add(btnSwitchSim);
        controlPanel.add(btnNextStep);
        controlPanel.add(btnSetMaxInterval);
        controlPanel.add(btnSetMinInterval);
        
        btnNextStep.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                step.set(true);
            }
        });

        btnConnect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                if(!connectedToServer)
                {
                	GUIConnection.getInstance().connectToServer(GraphicalInterfaceMain.DEFAULT_IP);
                    connectedToServer = true;
                    btnConnect.setText("Rozłącz");
                    infoPanel.serverStatus.setSelected(true);
                }
                else
                {
                	GUIConnection.getInstance().disconnectFromServer();
                    connectedToServer = false;
                    btnConnect.setText("Połącz");
                    infoPanel.serverStatus.setSelected(false);
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
                    btnSwitchSim.setText("Wyłącz automatyczną symulację");
                    infoPanel.autoModeStatus.setSelected(true);
                    infoPanel.manualModeStatus.setSelected(false);
                    btnNextStep.setEnabled(false);
                }
                else
                {
                    //switch sim mode method TODO
                    simAuto = false;
                    btnSwitchSim.setText("Włącz automatyczną symulację");
                    infoPanel.autoModeStatus.setSelected(false);
                    infoPanel.manualModeStatus.setSelected(true);
                    btnNextStep.setEnabled(true);
                }
            }
        });
        
        btnDisconnectCtrl.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                GUIConnection.modulesToKill.offer(new Module(Module.ModuleType.LIGHTS));
            }
        });
        
        btnDisconnectSim.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                GUIConnection.modulesToKill.offer(new Module(Module.ModuleType.SIM));
            }
        });
        
        // Panel z konsolą
        console = new JTextArea("",8,100);
        console.setBackground(new Color(0, 0, 0));
        console.setForeground(new Color(255, 255, 255));
        console.setBorder(new EmptyBorder(5,5,5,5));
        console.setEditable(false);
        console.setAutoscrolls(true);
        
        JScrollPane consolePanel = new JScrollPane( console );
        consolePanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        consolePanel.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        
        contentPane.add(consolePanel, BorderLayout.SOUTH);
    }
    
    public static synchronized boolean isSimAuto()
	{
		return simAuto;
	}

	public static void writeToConsole(String text)
    {
    	if(console!=null)
    	{
    		int bufferLength = console.getText().length();
    		if(bufferLength>1024)
    		{
    			try
				{
					console.setText(console.getText(0, 512));
				} catch (BadLocationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		console.setText(text + "\n" + console.getText());
    	}
    }
}