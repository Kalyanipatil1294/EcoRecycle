package GUI.UserView;

import Interface.RecyclableItem;
import Model.MachineItem;
import Model.RecycledItem;
import ecorecycle.RCM;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class RecycleGUI implements Observer {
    String rewardOptions[] = {"Cash", "Coupon"};
    RCM recyclingMachine;
    JComboBox recyclableItemMenu = new JComboBox();
    JComboBox rewardMenu = new JComboBox(rewardOptions);
    JPanel recyclePanel = new JPanel(new BorderLayout());
    JLabel weightLabel = new JLabel("Weight(lbs)");
    JTextField weightField = new JTextField();
    JButton newButton = new JButton("Add Item");
    JButton submitButton = new JButton("Submit");
    JButton addButton = new JButton("");
    ArrayList<RecycledItem>items = new ArrayList<>();

    public RecycleGUI(RCM rm) {
        this.recyclingMachine = rm;
        recyclingMachine.addObserver(this);
        recyclePanel.add(recyclableItemMenu);
        recyclePanel.add(rewardMenu);
    }

    public void setLocationAndSize() {
        recyclableItemMenu.setBounds(100, 50, 150, 20);
        rewardMenu.setBounds(250, 50, 150, 20);
        newButton.setBounds(130, 200, 100, 20);
        submitButton.setBounds(250, 200, 100, 20);
        weightLabel.setBounds(130, 120, 100, 20);
        weightField.setBounds(230, 120, 150, 20);
        addButton.setBounds(130, 150, 100, 20);
        addButton.setBorder(BorderFactory.createEtchedBorder());

    }

    public void addComponentsToPanel() {
        recyclePanel.add(newButton);
        recyclePanel.add(submitButton);
        recyclePanel.add(weightLabel);
        recyclePanel.add(weightField);
        recyclePanel.add(addButton);
    }

    public void addActionEvent() {
        newButton.addActionListener(e -> {
                    addItem();
                    weightField.setText("");
                }
        );
        submitButton.addActionListener(e -> {
            if (items.size() < 1) {
                JOptionPane.showMessageDialog(recyclePanel, "<html><div>Please add valid items in the machine</div>");
            } else {
                double price = recyclingMachine.calculateUserReward(items);
                boolean checkIfCashAvailable = recyclingMachine.checkIfCashAvailable(price);
                if (!checkIfCashAvailable) {
                    JOptionPane.showMessageDialog(recyclePanel, "<html><div>Cash is not Available!!</div>" + "<div>Total Money Received=>" + price + "$<div><div> Please Collect your coupon</div>");
                } else {
                    String reward = rewardMenu.getItemAt(rewardMenu.getSelectedIndex()).toString();
                    JOptionPane.showMessageDialog(recyclePanel, "<html><div>Total Money Received=>" + price + "$<div><div> Please Collect your " + reward + "</div>");
                }
                weightField.setText("");
                resetItems();
            }
        });

        recyclableItemMenu.addActionListener(e -> {

        });

        rewardMenu.addActionListener(e -> {

        });

    }

    private void addRecyclableItems() {
        recyclableItemMenu.removeAllItems();
        recyclableItemMenu.setVisible(false);
        if (recyclingMachine.getRecyclableItemsList().size() < 1)  {
            recyclableItemMenu.addItem(new MachineItem("Add recycling items", "edit"));
        } else {
            recyclableItemMenu.addItem(new MachineItem("Available items", "edit"));
        }
        for (RecyclableItem item : recyclingMachine.getRecyclableItemsList()) {
            recyclableItemMenu.addItem(new MachineItem(item.getItemName(), item.getItemName()));
        }
        recyclableItemMenu.setVisible(true);
    }


    public JPanel displayCreateWindow() {
        addRecyclableItems();
        setLocationAndSize();
        addComponentsToPanel();
        addActionEvent();
        recyclePanel.setPreferredSize(new Dimension(640, 480));
        return recyclePanel;
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

    private void addItem() {
        RecycledItem rm =  new RecycledItem(
                recyclableItemMenu.getItemAt(recyclableItemMenu.getSelectedIndex()).toString(),
                ParseDouble(weightField.getText()
                )
        );
        if(recyclingMachine.validateWeight(rm)) {
            items.add(rm);
        } else {
            JOptionPane.showMessageDialog(recyclePanel, "<html><div>Sorry Machine is Full !!</div>");
        }
    }

    private void resetItems() {
        items.removeAll(items);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update");
        addRecyclableItems();
        recyclePanel.repaint();
    }
}
