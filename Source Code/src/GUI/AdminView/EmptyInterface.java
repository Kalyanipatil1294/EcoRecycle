package GUI.AdminView;

import Interface.OperationGUIInterface;
import Model.MachineItem;
import Interface.RecyclingStation;
import ecorecycle.RCM;

import javax.swing.*;
import java.awt.*;

public class EmptyInterface implements OperationGUIInterface {
    RecyclingStation st;
    JPanel removePanel = new JPanel();
    JComboBox machineMenu = new JComboBox();

    public EmptyInterface(RecyclingStation st) {
        this.st = st;
    }

    private void addRCMItems() {
        machineMenu.removeAllItems();
        machineMenu.setVisible(false);
        for (RCM recyclingMachine : st.getMachines()) {
            machineMenu.addItem(new MachineItem(recyclingMachine.getMachineId(), "123"));
        }
        if (st.getMachines().size() > 0) {
            machineMenu.addItem(new MachineItem("ALL", "all"));
        } else {
            machineMenu.addItem(new MachineItem("No Machines available", "NA"));
        }
        machineMenu.setVisible(true);
    }

    public JPanel getRemovePanel() {
        removePanel.setPreferredSize(new Dimension(640, 480));
        addRCMItems();
        JLabel removeLabel = new JLabel("Empty RCM");
        removeLabel.setBounds(50, 120, 100, 25);
        JButton removeButton = new JButton("Empty");
        machineMenu.setBounds(70, 160, 200, 25);
        removeButton.setBounds(90, 200, 100, 30);
        machineMenu.setVisible(true);
        removePanel.add(removeLabel);
        removePanel.add(machineMenu);
        removePanel.add(removeButton);
        removePanel.setVisible(true);

        removeButton.addActionListener(event -> {
            String data = machineMenu.getItemAt(machineMenu.getSelectedIndex()).toString();
            if(!data.equalsIgnoreCase("NA")) {
                st.emptyRecycleMachine(data);
                addRCMItems();
                JOptionPane.showMessageDialog(removePanel, "RCM Emptied !!!");
            }
        });
        return removePanel;
    }

    @Override
    public JPanel getPanel() {
        removePanel.setPreferredSize(new Dimension(640, 480));
        addRCMItems();
        addComponentsToPanel();
        return removePanel;
    }

    @Override
    public void addComponentsToPanel() {
        JLabel removeLabel = new JLabel("Empty RCM");
        removeLabel.setBounds(50, 120, 100, 25);
        JButton removeButton = new JButton("Empty");
        machineMenu.setBounds(70, 160, 200, 25);
        removeButton.setBounds(90, 200, 100, 30);
        machineMenu.setVisible(true);
        removePanel.add(removeLabel);
        removePanel.add(machineMenu);
        removePanel.add(removeButton);
        removePanel.setVisible(true);

        removeButton.addActionListener(event -> {
            String data = machineMenu.getItemAt(machineMenu.getSelectedIndex()).toString();
            if(!data.equalsIgnoreCase("NA")) {
                st.emptyRecycleMachine(data);
                addRCMItems();
                JOptionPane.showMessageDialog(removePanel, "RCM Emptied !!!");
            }
        });
    }
}
