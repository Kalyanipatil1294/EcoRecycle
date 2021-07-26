package GUI.AdminView;

import Interface.OperationGUIFactory;
import Interface.OperationGUIInterface;
import Interface.RecyclingStation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StationUserInterface extends JFrame implements ActionListener {
    OperationGUIFactory guiFactory = new OperationGUIFactory();
    String operations[] = {"Select Operation", "Add/Edit RCM", "Track RCM Status", "Update RCM",  "View Statistics", "Remove RCM", "Empty RCM", "Activate RCM", "Deactivate RCM"};
    final JComboBox operationMenu = new JComboBox(operations);
    RecyclingStation recyclingStation;
    JPanel displayPanel;
    JPanel selectedPanel = new JPanel();
    private Container container;


    public StationUserInterface(RecyclingStation recyclingStation) {
        this.recyclingStation = recyclingStation;
        setContainer();
        getDisplayPanel();
        setOperationPanel();
    }

    private void setOperationPanel() {
        displayPanel.setPreferredSize(new Dimension(150, 150));
        container.add(displayPanel, BorderLayout.CENTER);
        setVisible(true);
        operationMenu.addActionListener(e -> {
            String data = ""
                    + operationMenu.getItemAt(operationMenu.getSelectedIndex());
            performOperation(data);
        });
    }

    private void setContainer() {
        setForeground(Color.CYAN);
        setFont(new Font("Dialog", Font.ROMAN_BASELINE, 15));
        setTitle("Eco Recycle System (Admin View)");
        setPreferredSize(new Dimension(700, 500));
        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    }

    void performOperation(String selectedOperation) {
        if (selectedOperation.equalsIgnoreCase("Select Operation")) {
            selectedPanel.removeAll();
            selectedPanel.repaint();
        } else {
            selectedPanel.removeAll();
            selectedPanel.setVisible(false);
            OperationGUIInterface operationUI = guiFactory.createUserInterface(selectedOperation, recyclingStation);
            selectedPanel = operationUI.getPanel();
            container.add(selectedPanel);
            selectedPanel.revalidate();
            selectedPanel.repaint();
            selectedPanel.setVisible(true);
            pack();
            setLocationRelativeTo(null);
        }
    }

    private void getDisplayPanel() {
        displayPanel = new JPanel();

        JLabel header = new JLabel("<html>Welcome to Recycling Station!<br/>Please select an option to proceed<br/></html>", SwingConstants.CENTER);
        header.setIcon(null);
        header.setForeground(Color.pink);
        header.setFont(new Font("Serif", Font.PLAIN, 25));
        operationMenu.setBounds(50, 100, 100, 25);
        displayPanel.add(header);
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        displayPanel.add(operationMenu);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String data = ""
                + operationMenu.getItemAt(operationMenu.getSelectedIndex());
        performOperation(data);
    }
}
