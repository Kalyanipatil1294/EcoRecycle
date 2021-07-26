package GUI.AdminView;

import Interface.OperationGUIInterface;
import Model.MachineItem;
import Model.RecycleItem;
import Interface.RecyclableItem;
import Interface.RecyclingStation;
import ecorecycle.RCM;

import javax.swing.*;
import java.awt.*;

public class UpdateRCMInterface implements OperationGUIInterface {
    RecyclingStation st;
    JComboBox machineMenu = new JComboBox();
    JComboBox recyclableItemMenu = new JComboBox();
    JPanel addPanel = new JPanel(new BorderLayout());
    JLabel itemNameLabel = new JLabel("Item Name");
    JLabel PriceLabel = new JLabel("Price per lb");
    JTextField itemNameField = new JTextField();
    JTextField priceField = new JTextField();
    JButton newButton = new JButton("Reset");
    JButton changeButton = new JButton("Save Item");
    JButton addButton = new JButton("");

    public UpdateRCMInterface(RecyclingStation st) {
        this.st = st;
        addPanel.add(machineMenu);
        addPanel.add(recyclableItemMenu);
    }

    public void setLocationAndSize() {
        machineMenu.setBounds(50, 50, 150, 20);
        recyclableItemMenu.setBounds(200, 50, 200, 20);
        newButton.setBounds(50, 250, 100, 20);
        changeButton.setBounds(180, 250, 100, 20);
        itemNameLabel.setBounds(50, 100, 100, 20);
        PriceLabel.setBounds(50, 150, 100, 20);
        itemNameField.setBounds(150, 100, 150, 20);
        priceField.setBounds(150, 150, 150, 20);
        addButton.setBounds(150, 250, 100, 20);
        addButton.setBorder(BorderFactory.createEtchedBorder());
    }

    public void addComponentsToPanel() {
        addPanel.add(newButton);
        addPanel.add(changeButton);
        addPanel.add(itemNameLabel);
        addPanel.add(PriceLabel);
        addPanel.add(itemNameField);
        addPanel.add(priceField);
        addPanel.add(addButton);
    }

    public void addActionEvent() {
        newButton.addActionListener(e -> {
            itemNameField.setEditable(true);
            itemNameField.setText("");
            priceField.setText("");
        });

        changeButton.addActionListener(e -> {
            String machineId = "" + machineMenu.getItemAt(machineMenu.getSelectedIndex());
            RCM selectedRCM = getSelectedRCM();
            if (selectedRCM.checkIfItemAvailable(itemNameField.getText())) {
                setRecyclableItemData(selectedRCM, itemNameField.getText());
            } else {
                RecyclableItem rm = new RecycleItem(
                        itemNameField.getText(),
                        ParseDouble(priceField.getText()),
                        ParseDouble("")
                );
                st.updateRecyclableItem(machineId, rm);
                addRecyclableItems();
            }
        });

        recyclableItemMenu.addActionListener(e -> {
            String itemId = ""
                    + recyclableItemMenu.getItemAt(recyclableItemMenu.getSelectedIndex());
            setItemData(itemId);
        });
        machineMenu.addActionListener(e -> {
            addRecyclableItems();
        });

    }

    private void setRecyclableItemData(RCM selectedRCM, String itemName) {
        for (RecyclableItem rt : selectedRCM.getRecyclableItemsList()) {
            if (rt.getItemName().equalsIgnoreCase(itemName)) {
                rt.setPrice(ParseDouble(priceField.getText()));
            }
        }
    }
    private RCM getSelectedRCM() {
        String machineId = "" + machineMenu.getItemAt(machineMenu.getSelectedIndex());
        RCM rm =  st.getMachines().get(0);
        for (RCM recyclingMachine : st.getMachines()) {
            if(machineId.equalsIgnoreCase(recyclingMachine.getMachineLocation())) {
                rm = recyclingMachine;
            }
        }
        return rm;
    }

    private void setItemData(String itemId) {
        itemNameField.setEditable(false);
        String data = "" + machineMenu.getItemAt(machineMenu.getSelectedIndex());

        for (RCM recyclingMachine : st.getMachines()) {
            if (data.equalsIgnoreCase(recyclingMachine.getMachineId())) {
                for (RecyclableItem item : recyclingMachine.getRecyclableItemsList()) {
                    if (itemId.equalsIgnoreCase(item.getItemName())) {
                        itemNameField.setText(item.getItemName());
                        priceField.setText(item.getPrice().toString());
                    }
                }
            }
            recyclableItemMenu.setVisible(true);
        }
    }

    private void addRCMItems() {
        machineMenu.removeAllItems();
        machineMenu.setVisible(false);
        for (RCM recyclingMachine : st.getMachines()) {
            machineMenu.addItem(new MachineItem(recyclingMachine.getMachineId(), "123"));
        }
        machineMenu.setVisible(true);
    }

    private void addRecyclableItems() {
        recyclableItemMenu.removeAllItems();
        recyclableItemMenu.setVisible(false);
        String data = "" + machineMenu.getItemAt(machineMenu.getSelectedIndex());
        recyclableItemMenu.addItem(new MachineItem("Select items to edit", "edit"));
        for (RCM recyclingMachine : st.getMachines()) {
            if (data.equalsIgnoreCase(recyclingMachine.getMachineId())) {
                for (RecyclableItem item : recyclingMachine.getRecyclableItemsList()) {
                    recyclableItemMenu.addItem(new MachineItem(item.getItemName(), item.getItemName()));
                }
            }
            recyclableItemMenu.setVisible(true);
        }
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }

    @Override
    public JPanel getPanel() {
        addRCMItems();
        addRecyclableItems();
        setLocationAndSize();
        addComponentsToPanel();
        addActionEvent();
        addPanel.setPreferredSize(new Dimension(640, 480));
        return addPanel;
    }
}
