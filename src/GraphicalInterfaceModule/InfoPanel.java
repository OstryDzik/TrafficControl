package GraphicalInterfaceModule;

import Common.ReadOnlyCheckBox;

import javax.swing.*;
import java.awt.*;

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
        Label connectionStats = new Label("Connections Status:");
        serverStatus = new  ReadOnlyCheckBox("Server connected", false);
        controllerStatus = new  ReadOnlyCheckBox("Controller connected", true);
        simulationStatus = new  ReadOnlyCheckBox("Simulation connected", false);
        Label simulationStats = new Label("Simulation Status:");
        manualModeStatus = new  ReadOnlyCheckBox("Manual mode", true);
        autoModeStatus = new  ReadOnlyCheckBox("Auto mode", false);
        this.add(connectionStats);
        this.add(serverStatus);
        this.add(controllerStatus);
        this.add(simulationStatus);
        this.add(placeHolder1);
        this.add(simulationStats);
        this.add(manualModeStatus);
        this.add(autoModeStatus);
        this.add(placeHolder2);
        minTimeInterval = new Label("Min car gen time: 0");
        maxTimeInterval = new Label("Max car gen time: 0");
        this.add(minTimeInterval);
        this.add(maxTimeInterval);
    }
}
