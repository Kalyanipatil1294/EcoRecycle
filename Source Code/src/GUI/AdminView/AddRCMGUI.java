package GUI.AdminView;

import Interface.OperationGUIInterface;
import Model.MachineItem;
import ecorecycle.RCM;
import Interface.RecyclingStation;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class AddRCMGUI implements OperationGUIInterface, Observer {
    RecyclingStation st;
    JPanel addPanel = new JPanel(new BorderLayout());
    String operations[] = {"Add RCM", " Edit RCM"};
    final JComboBox operationMenu = new JComboBox(operations);
    final JComboBox rcmMenu = new JComboBox();
    JLabel idLabel = new JLabel("Id");
    JLabel locationLabel = new JLabel("Location");
    JLabel weightLabel = new JLabel("Max Weight");
    JLabel moneyLabel = new JLabel("Money");
    JTextField idTextField = new JTextField();
    JTextField locationField = new JTextField();
    JTextField weightField = new JTextField();
    JTextField moneyField = new JTextField();
    JButton addNewRCMButton = new JButton("Add RCM");
    JButton addButton = new JButton("");
    RCM selectedRCM = new RCM();

    public AddRCMGUI(RecyclingStation st) {
        this.st = st;
    }

    public void setLocationAndSize() {
        addNewRCMButton.setBounds(270, 300, 100, 20);
        operationMenu.setBounds(200, 10, 120, 20);
        rcmMenu.setBounds(350, 10, 100, 20);
        idLabel.setBounds(200, 70, 100, 20);
        locationLabel.setBounds(200, 120, 100, 20);
        weightLabel.setBounds(200, 170, 100, 20);
        moneyLabel.setBounds(200, 220, 100, 20);
        idTextField.setBounds(300, 70, 150, 20);
        locationField.setBounds(300, 120, 150, 20);
        weightField.setBounds(300, 170, 150, 20);
        moneyField.setBounds(300, 220, 150, 20);
        addButton.setBounds(370, 250, 100, 20);
        addButton.setBorder(BorderFactory.createEtchedBorder());
    }

    public void resetRCMData() {
        idTextField.setText("");
        locationField.setText("");
        weightField.setText("");
        moneyField.setText("");
        System.out.println("");
    }

    public void addActionEvent() {
        addButton.addActionListener(e -> {
        });
        rcmMenu.addActionListener(e -> {
            String operation = ""
                    + operationMenu.getItemAt(operationMenu.getSelectedIndex());
            if (operation.equalsIgnoreCase("Edit RCM")) {
                setSelectedRCMData();
            }
        });
        operationMenu.addActionListener(e -> {
            String operation = ""
                    + operationMenu.getItemAt(operationMenu.getSelectedIndex());
            if (operation.equalsIgnoreCase("Add RCM")) {
                selectedRCM = new RCM();
                addPanel.setVisible(false);
                idTextField.setEditable(true);
                resetRCMData();
                rcmMenu.removeAllItems();
                rcmMenu.setVisible(false);
                addNewRCMButton.setText("Add RCM");
                addPanel.setVisible(true);
                addPanel.repaint();
            } else {
                addRCMItems();
                setSelectedRCMData();
                addNewRCMButton.setText("Save");
            }
        });
        addNewRCMButton.addActionListener(e -> {

            String operation = ""
                    + operationMenu.getItemAt(operationMenu.getSelectedIndex());
            if (operation.equalsIgnoreCase("Add RCM")) {

                RCM rm = new RCM(
                        idTextField.getText(), locationField.getText(),
                        ParseDouble(weightField.getText()),
                        ParseDouble(moneyField.getText()));
                if (st.validateRCM(rm) && validateData()) {
                    st.addRecycleMachine(rm);
                    resetRCMData();
                    JOptionPane.showMessageDialog(addPanel, "RCM Added Successfully");
                } else if (!validateData()) {
                    JOptionPane.showMessageDialog(addPanel, "Please Enter Valid Values");
                } else {
                    JOptionPane.showMessageDialog(addPanel, "RCM already exists !!!");
                }
            } else {
                if (validateData()) {
                    selectedRCM.setMoney(ParseDouble(moneyField.getText()));
                    selectedRCM.setWeight(ParseDouble(weightField.getText()));
                    selectedRCM.setLocation(locationField.getText());
                    JOptionPane.showMessageDialog(addPanel, "RCM Data Updated Successfully");
                } else {
                    JOptionPane.showMessageDialog(addPanel, "Please Enter Valid Values");
                }
            }
        });
    }

    private void setSelectedRCMData() {
        selectedRCM = getSelectedRCM();
        idTextField.setText(selectedRCM.getMachineId());
        idTextField.setEditable(false);
        locationField.setText(selectedRCM.getMachineLocation());
        weightField.setText(String.valueOf(selectedRCM.getTotalWeight()));
        moneyField.setText(String.valueOf(selectedRCM.getMoney()));
        rcmMenu.setVisible(true);
    }

    private RCM getSelectedRCM() {
        String machineId = "" + rcmMenu.getItemAt(rcmMenu.getSelectedIndex());
        RCM rm =  st.getMachines().get(0);
        for (RCM recyclingMachine : st.getMachines()) {
            if(machineId.equalsIgnoreCase(recyclingMachine.getMachineLocation())) {
                rm = recyclingMachine;
            }
        }
        rm.addObserver(this);
        return rm;
    }

    private boolean validateData() {
        return !idTextField.getText().isBlank() &&
                !locationField.getText().isBlank() &&
                !weightField.getText().isBlank() &&
                !moneyField.getText().isBlank();
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


    private void addRCMItems() {
        rcmMenu.removeAllItems();
        rcmMenu.setVisible(false);
        for (RCM recyclingMachine : st.getMachines()) {
            rcmMenu.addItem(new MachineItem(recyclingMachine.getMachineId(), "123"));
        }
//        for(Iterator iter =  st.getIterator(); iter.hasNext();){
//            System.out.println("iter.next()");
//            System.out.println(iter.next());
//            rcmMenu.addItem(new MachineItem(iter.next().getMachineId(), "123"));
//            System.out.println("Name : " + iter.next().getMachineId());
//        }
        rcmMenu.setVisible(true);
    }

    @Override
    public JPanel getPanel() {
        setLocationAndSize();
        addComponentsToPanel();
        addActionEvent();
        addPanel.setPreferredSize(new Dimension(640, 600));
        return addPanel;
    }

    @Override
    public void addComponentsToPanel() {
        addPanel.add(operationMenu);
        addPanel.add(rcmMenu);
        rcmMenu.setVisible(false);
        addPanel.add(idLabel);
        addPanel.add(locationLabel);
        addPanel.add(weightLabel);
        addPanel.add(moneyLabel);
        addPanel.add(idTextField);
        addPanel.add(locationField);
        addPanel.add(weightField);
        addPanel.add(moneyField);
        addPanel.add(addNewRCMButton);
        addPanel.add(addButton);
    }

    @Override
    public void update(Observable o, Object arg) {
        addRCMItems();
    }
}
