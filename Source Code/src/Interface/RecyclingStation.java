package Interface;

import ecorecycle.RCM;

import java.util.ArrayList;

public interface RecyclingStation {
    void addRecycleMachine(RCM rm);
    boolean validateRCM(RCM rm);
    void removeRecycleMachine(String rmId);
    void updateRecycleMachineData(String machineId, String status);
    void updateRecyclableItem(String machineId, RecyclableItem item);
    void deactivateRecycleMachine(RCM rm);
    void activateRecycleMachine(RCM rm);
    void emptyRecycleMachine(String rmId);
    void checkRCMStatus(RCM rm, String operation);
    ArrayList<RCM> getMachines();
    Iterator getIterator();
}
