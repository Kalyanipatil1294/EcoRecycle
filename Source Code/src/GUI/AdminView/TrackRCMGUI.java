package GUI.AdminView;

import Interface.OperationGUIInterface;
import Interface.RecyclableItem;
import Interface.RecyclingStation;
import Model.MachineItem;
import Model.RecycleItem;
import ecorecycle.RCM;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class TrackRCMGUI implements OperationGUIInterface, Observer {
    String operations[] = {"Select Operation", "No of times machine used", "Check Last Emptied", "Track Status"};
    RecyclingStation recyclingStation;
    JComboBox machineMenu = new JComboBox();
    JComboBox operationsMenu = new JComboBox(operations);
    JLabel machineDetails = new JLabel();
    JLabel statusLabel = new JLabel();

    JPanel trackRecordsPanel = new JPanel(new BorderLayout());
    JLabel PriceLabel = new JLabel("Price per lb");
    JTextField itemNameField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField weightField = new JTextField();
    JButton changeButton = new JButton("Save");
    JButton addButton = new JButton("");
    RCM selectedRCM = new RCM();

    public TrackRCMGUI(RecyclingStation st) {
        this.recyclingStation = st;
        trackRecordsPanel.add(machineMenu);
        trackRecordsPanel.add(operationsMenu);
    }

    public void setLocationAndSize() {
        machineMenu.setBounds(100, 50, 150, 20);
        operationsMenu.setBounds(250, 50, 200, 20);
        machineDetails.setBounds(150, 100, 300, 20);
        statusLabel.setBounds(150, 150, 300, 20);
        addButton.setBorder(BorderFactory.createEtchedBorder());
        statusLabel.setVisible(false);
        machineDetails.setForeground(Color.blue);
        machineDetails.setFont(new Font("Serif", Font.PLAIN, 20));
    }

    public void addComponentsToPanel() {
        trackRecordsPanel.add(changeButton);
        trackRecordsPanel.add(machineDetails);
        trackRecordsPanel.add(statusLabel);
        trackRecordsPanel.add(PriceLabel);
        trackRecordsPanel.add(itemNameField);
        trackRecordsPanel.add(priceField);
        trackRecordsPanel.add(addButton);
    }

    public void addActionEvent() {
        changeButton.addActionListener(e -> {
            RecyclableItem rm = new RecycleItem(
                    itemNameField.getText(),
                    ParseDouble(priceField.getText()),
                    ParseDouble(weightField.getText()));
            String machineId = "" + machineMenu.getItemAt(machineMenu.getSelectedIndex());

            recyclingStation.updateRecyclableItem(machineId, rm);
        });

        operationsMenu.addActionListener(e -> {
            String operation = ""
                    + operationsMenu.getItemAt(operationsMenu.getSelectedIndex());
            performTask(operation);
        });
        machineMenu.addActionListener(e -> {
            selectedRCM = getSelectedRCM();
        });

    }

    private void performTask(String operation) {
        switch (operation) {
            case "Track Status":
                trackStatus();
                break;

            case "Check Last Emptied":
                emptiedData();
                break;

            case "No of times machine used":
                usage();
                break;
        }
        statusLabel.setForeground(Color.blue);
        statusLabel.setFont(new Font("Serif", Font.PLAIN, 20));
    }

    private void usage() {
        statusLabel.setVisible(false);
        int usage = selectedRCM.getNoOfTimesUsed();
        statusLabel.setText("No of times Machine was used => " + usage);
        statusLabel.setVisible(true);
    }

    private void emptiedData() {
        statusLabel.setVisible(false);
        LocalDateTime lastEmptiedTime = selectedRCM.getLastEmptiedTime();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateString = lastEmptiedTime == null ? "Never Emptied" : "" + lastEmptiedTime.format(myFormatObj);
        statusLabel.setText("Last Emptied => " + dateString);
        statusLabel.setVisible(true);
    }

    private void trackStatus() {
        statusLabel.setVisible(false);
        String status = selectedRCM.getStatus();
        statusLabel.setText("Machine Working Status => " + status);
        statusLabel.setVisible(true);
    }

    private RCM getSelectedRCM() {
        String machineId = "" + machineMenu.getItemAt(machineMenu.getSelectedIndex());
        RCM rm =  recyclingStation.getMachines().get(0);
        for (RCM recyclingMachine : recyclingStation.getMachines()) {
            if(machineId.equalsIgnoreCase(recyclingMachine.getMachineLocation())) {
                rm = recyclingMachine;
            }
        }
        rm.addObserver(this);
        return rm;
    }

    private void addRCMItems() {
        machineMenu.removeAllItems();
        machineMenu.setVisible(false);
        for (RCM recyclingMachine : recyclingStation.getMachines()) {
            machineMenu.addItem(new MachineItem(recyclingMachine.getMachineId(), "123"));
        }
        machineMenu.setVisible(true);
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }

    @Override
    public JPanel getPanel() {
        addRCMItems();
        selectedRCM = getSelectedRCM();
        machineDetails.setText("Machine Location => " + selectedRCM.getMachineLocation());
        setLocationAndSize();
        addComponentsToPanel();
        addActionEvent();
        trackRecordsPanel.setPreferredSize(new Dimension(640, 480));
        return trackRecordsPanel;
    }

    @Override
    public void update(Observable o, Object arg) {
        addRCMItems();
    }
}
