package GUI.AdminView;

import Interface.RecyclingStation;

import javax.swing.*;
import java.awt.*;

public class Login {
    private static Login login = new Login();
    LoginFrame frame;
    RecyclingStation recyclingStation;

    public void getLoginFrame(RecyclingStation rs) {
        this.recyclingStation = rs;
        frame = new LoginFrame(recyclingStation);
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(100, 100, 370, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.lightGray);
        ((JComponent) frame.getContentPane()).setOpaque(false);
    }

    public static Login getInstance() {
        return login;
    }
}
