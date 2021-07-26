package GUI.UserView;

import Model.MachineItem;
import ecorecycle.RCM;
import ecorecycle.RecycleStation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class CustomerInterface extends JFrame implements ActionListener, Observer {
    final JComboBox machineSelectionMenu = new JComboBox();
    RecycleStation recyclingStation;
    JPanel displayPanel;
    JPanel selectedPanel = new JPanel();
    JPanel recyclePanel = new JPanel();
    private Container container;
    JButton recycleButton = new JButton("Recycle");
    JButton displayButton = new JButton("Check Machine Weight");
    RCM selectedRCM = new RCM();
    int count = 0;


    public CustomerInterface(RecycleStation station) {
        recyclingStation = station;
        recyclingStation.addObserver(this);

        setForeground(Color.CYAN);
        setFont(new Font("Dialog", Font.ROMAN_BASELINE, 15));
        setTitle("Eco Recycling Machine ");
        setPreferredSize(new Dimension(550, 500));

        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        getDisplayPanel();
        getButtonPanel();

        displayPanel.setPreferredSize(new Dimension(100, 100));
        container.add(displayPanel);

        getSelectionPanel();

        setVisible(true);
        addMachines();
        addActionEvent();
        selectedRCM = getSelectedRCM();
        machineSelectionMenu.addActionListener(e -> {
            String data = "" + machineSelectionMenu.getItemAt(machineSelectionMenu.getSelectedIndex());
            performOperation(data);
        });
    }

    private void getButtonPanel() {
        recycleButton.setVisible(true);
        displayButton.setVisible(true);

        recycleButton.setBounds(50, 0, 100, 20);
        recycleButton.setBounds(100, 0, 100, 20);

        displayPanel.add(recycleButton);
        displayPanel.add(displayButton);
        displayPanel.setVisible(true);
    }

    private void addMachines() {
        int count = 0;
        machineSelectionMenu.removeAllItems();
        machineSelectionMenu.setVisible(false);
        for (RCM recyclingMachine : recyclingStation.getMachines()) {
            if (recyclingMachine.getStatus().equalsIgnoreCase("active")) {
                count ++;
                machineSelectionMenu.addItem(new MachineItem(recyclingMachine.getMachineLocation(), recyclingMachine.getMachineId()));
            }
        }

        if ( count < 1) {
            machineSelectionMenu.addItem(new MachineItem("No Machine Available", "NA"));
            recyclePanel.removeAll();
        }
        machineSelectionMenu.setVisible(true);
    }

    private void getSelectionPanel() {

    }

    void performOperation(String selectedOperation) {
        switch (selectedOperation) {
            default:
                selectedPanel.removeAll();
                selectedPanel.repaint();
                break;
        }
    }

    private void getDisplayPanel() {
        displayPanel = new JPanel();

        JLabel sl3 = new JLabel("<html>Welcome to Recycling Machine!<br/></html>", SwingConstants.CENTER);
        sl3.setIcon(null);
        sl3.setForeground(Color.getHSBColor(75, 27, 64));
        sl3.setFont(new Font("Serif", Font.PLAIN, 25));
        machineSelectionMenu.setBounds(50, 100, 100, 25);
        displayPanel.add(sl3);
        displayPanel.add(machineSelectionMenu);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String data = ""
                + machineSelectionMenu.getItemAt(machineSelectionMenu.getSelectedIndex());
        performOperation(data);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update");
        addMachines();
        if(count > 0) {
            recyclePanel.setVisible(false);
            RecycleGUI recycle = new RecycleGUI(getSelectedRCM());
            recyclePanel = recycle.displayCreateWindow();
            recyclePanel.setVisible(true);
            container.add(recyclePanel);
            container.invalidate();
            container.validate();
            container.repaint();
        }
    }

    public void addActionEvent() {
        recycleButton.addActionListener(e -> {
            count++;
            recyclePanel.removeAll();
            recyclePanel.setVisible(false);
            RecycleGUI recycle = new RecycleGUI(getSelectedRCM());
            recyclePanel = recycle.displayCreateWindow();
            recyclePanel.setVisible(true);
            container.add(recyclePanel);
            recyclePanel.repaint();
            container.invalidate();
            container.validate();
            container.repaint();
        });

        displayButton.addActionListener(e -> {
            double machineCapacity = getSelectedRCM().getTotalWeight();
            double curWeight = getSelectedRCM().getCurrentWeight();
            JOptionPane.showMessageDialog(recyclePanel, "<html><div>Total Machine Capacity=>"+ machineCapacity +"lb <div><div> Current Occupied Weight</div>" + curWeight);
            });
    }

    private RCM getSelectedRCM() {
        String machineId = machineSelectionMenu.getItemAt(machineSelectionMenu.getSelectedIndex()).toString();
        RCM rm = new RCM();
        for (RCM recyclingMachine : recyclingStation.getMachines()) {
            if(machineId.equalsIgnoreCase(recyclingMachine.getMachineLocation())) {
                rm = recyclingMachine;
            }
        }
        rm.addObserver(this);
        return rm;
    }
}
