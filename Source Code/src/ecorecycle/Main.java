package ecorecycle;

import GUI.UserView.CustomerInterface;
import GUI.AdminView.Login;

public class Main {
    public static void main(String[] args) {
        Login login = Login.getInstance();
        RecycleStation station = new RecycleStation();
        RCM rm = new RCM("100", "Santa Clara", 100, 1000);
        station.addRecycleMachine(rm);
        login.getLoginFrame(station);
        new CustomerInterface(station);
    }
}