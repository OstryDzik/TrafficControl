package GraphicalInterfaceModule;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import Common.ReadOnlyCheckBox;

/**
 * Created by Filip on 2014-11-02.
 */
public class InfoPanel extends JPanel
{
    ReadOnlyCheckBox serverStatus;
    ReadOnlyCheckBox controllerStatus;
    ReadOnlyCheckBox simulationStatus;
    ReadOnlyCheckBox manualModeStatus;
    ReadOnlyCheckBox autoModeStatus;
    Label minTimeInterval;
    Label maxTimeInterval;


    public InfoPanel()
    {
        super(new GridLayout(0,1,0,0));
        Label placeHolder1 = new Label("");
        Label placeHolder2 = new Label("");
        Label connectionStats = new Label("Status sieci:");
        serverStatus = new  ReadOnlyCheckBox("Serwer połączony", false);
        controllerStatus = new  ReadOnlyCheckBox("Kontroler połączony", false);
        simulationStatus = new  ReadOnlyCheckBox("Symulacja połączona", false);
        Label simulationStats = new Label("Tryb symulacji:");
        manualModeStatus = new  ReadOnlyCheckBox("Tryb ręczny", true);
        autoModeStatus = new  ReadOnlyCheckBox("Tryb automatyczny", false);
        this.add(connectionStats);
        this.add(serverStatus);
        this.add(controllerStatus);
        this.add(simulationStatus);
        this.add(placeHolder1);
        this.add(simulationStats);
        this.add(manualModeStatus);
        this.add(autoModeStatus);
        this.add(placeHolder2);
        minTimeInterval = new Label("Min czas gen. samochodów: 0");
        maxTimeInterval = new Label("Maks czas gen. samochodów: 0");
        this.add(minTimeInterval);
        this.add(maxTimeInterval);
    }
}
