package GUI.AdminView;

import GUI.BarCharts;
import Interface.OperationGUIInterface;
import Interface.RecyclingStation;

import javax.swing.*;
import java.awt.*;

public class Statistics implements OperationGUIInterface {
    RecyclingStation recyclingStation;
    JPanel statisticsPanel;

    public Statistics(RecyclingStation station) {
        this.recyclingStation = station;
    }

    @Override
    public JPanel getPanel() {
        double[] value= new double[recyclingStation.getMachines().size()];
        String[] machines = new String[recyclingStation.getMachines().size()];
        for (int i = 0; i < recyclingStation.getMachines().size(); i++) {
            value[i] = recyclingStation.getMachines().get(i).getNoOfTimesUsed();
            machines[i] = recyclingStation.getMachines().get(i).getMachineLocation();
        }
        statisticsPanel = new BarCharts(value, machines);
        statisticsPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        statisticsPanel.setPreferredSize(new Dimension(640, 600));
        return statisticsPanel;
    }

    @Override
    public void addComponentsToPanel() {

    }
}
