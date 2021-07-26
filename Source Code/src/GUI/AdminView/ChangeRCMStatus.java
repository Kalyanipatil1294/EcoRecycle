package GUI.AdminView;

import Interface.OperationGUIInterface;
import Model.MachineItem;
import Interface.RecyclingStation;
import ecorecycle.RCM;

import javax.swing.*;
import java.awt.*;

public class ChangeRCMStatus implements OperationGUIInterface {
    RecyclingStation st;
    JPanel statusPanel = new JPanel();
    JButton actionButton;
    JComboBox machineMenu = new JComboBox();
    String statusType;


    public ChangeRCMStatus(RecyclingStation st, String status) {
        this.st = st;
        this.statusType = status;
    }

    private void addRCMItems() {
        machineMenu.setVisible(false);
        machineMenu.removeAllItems();
        int count = 0;
        for (RCM recyclingMachine : st.getMachines()) {
            if (recyclingMachine.getStatus().equalsIgnoreCase(statusType)) {
                count ++;
                machineMenu.addItem(new MachineItem(recyclingMachine.getMachineId(), "123"));
            }
        }

        if (count > 0) {
            machineMenu.addItem(new MachineItem("All", "all"));
        } else {
            machineMenu.addItem(new MachineItem("No Machines", "NA"));

        }
        machineMenu.setVisible(true);
    }

    public JPanel getStatusPanel() {
        statusPanel.setPreferredSize(new Dimension(640, 480));
        addRCMItems();
        String label = statusType.equalsIgnoreCase("inactive") ? "Activate" : "Deactivate";
        JLabel removeLabel = new JLabel(label + " RCM");
        removeLabel.setBounds(50, 120, 100, 25);
        JButton actionButton = new JButton(label);
        actionButton.setSize(100, 50);
        machineMenu.setBounds(70, 160, 100, 25);
        actionButton.setBounds(90, 200, 100, 30);
        machineMenu.setVisible(true);
        statusPanel.add(removeLabel);
        statusPanel.add(machineMenu);
        statusPanel.add(actionButton);
        statusPanel.setVisible(true);

        actionButton.addActionListener(event -> {
            String data = ""
                    + machineMenu.getItemAt(machineMenu.getSelectedIndex());
            if(!data.equalsIgnoreCase("NA")) {
                String status = statusType.equalsIgnoreCase("inactive") ? "active" : "inactive";

                st.updateRecycleMachineData(data, status);
                addRCMItems();
            }
        });
        return statusPanel;
    }

    @Override
    public JPanel getPanel() {
        statusPanel.setPreferredSize(new Dimension(640, 480));
        addRCMItems();
        addComponentsToPanel();
        actionButton.addActionListener(event -> {
            String data = ""
                    + machineMenu.getItemAt(machineMenu.getSelectedIndex());
            if(!data.equalsIgnoreCase("NA")) {
                String status = statusType.equalsIgnoreCase("inactive") ? "active" : "inactive";

                st.updateRecycleMachineData(data, status);
                addRCMItems();
            }
        });
        return statusPanel;
    }

    @Override
    public void addComponentsToPanel() {
        String label = statusType.equalsIgnoreCase("inactive") ? "Activate" : "Deactivate";
        JLabel removeLabel = new JLabel(label + " RCM");
        removeLabel.setBounds(50, 120, 100, 25);
         actionButton = new JButton(label);
        actionButton.setSize(100, 50);
        machineMenu.setBounds(70, 160, 100, 25);
        actionButton.setBounds(90, 200, 100, 30);
        machineMenu.setVisible(true);
        statusPanel.add(removeLabel);
        statusPanel.add(machineMenu);
        statusPanel.add(actionButton);
        statusPanel.setVisible(true);
    }
}

