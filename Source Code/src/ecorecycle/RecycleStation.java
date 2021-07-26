package ecorecycle;

import Interface.RecyclableItem;
import Interface.RecyclingStation;

import java.util.*;

public class RecycleStation extends Observable implements RecyclingStation {
    ArrayList<RCM> machines = new ArrayList<>();

    @Override
    public void addRecycleMachine(RCM rm) {
        machines.add(rm);
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean validateRCM(RCM rm) {
        int count = (int) machines.stream().filter(RecycleMachine -> rm.getMachineId().equalsIgnoreCase(RecycleMachine.getMachineId())).count();
        return count < 1;
    }

    @Override
    public void removeRecycleMachine(String rmId) {
        if(rmId.equalsIgnoreCase("all")) {
            machines.removeAll(machines);
        } else {
            for (Iterator<RCM> iterator = machines.iterator(); iterator.hasNext(); ) {
                RCM RecycleMachine = iterator.next();
                if (rmId.equalsIgnoreCase(RecycleMachine.getMachineId())) {
                    iterator.remove();
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void updateRecycleMachineData(String rmId, String status) {
        for (Iterator<RCM> iterator = machines.iterator(); iterator.hasNext(); ) {
            RCM RecycleMachine = iterator.next();
            if (rmId.equalsIgnoreCase(RecycleMachine.getMachineId())) {
                RecycleMachine.setStatus(status);
            }
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void updateRecyclableItem(String machineId, RecyclableItem item) {
        for (Iterator<RCM> iterator = machines.iterator(); iterator.hasNext(); ) {
            RCM RecycleMachine = iterator.next();
            if (machineId.equalsIgnoreCase(RecycleMachine.getMachineId())) {
                RecycleMachine.addItemsToRCM(item);
            }
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void deactivateRecycleMachine(RCM rm) {

    }

    @Override
    public void activateRecycleMachine(RCM rm) {

    }

    @Override
    public void emptyRecycleMachine(String rmId) {
        System.out.println("empty st");
        for (Iterator<RCM> iterator = machines.iterator(); iterator.hasNext(); ) {
            RCM RecycleMachine = iterator.next();
            if (rmId.equalsIgnoreCase(RecycleMachine.getMachineId()) || rmId.equalsIgnoreCase("all")) {
                RecycleMachine.emptyRCM();
            }
        }
        setChanged();
        notifyObservers();

    }

    @Override
    public void checkRCMStatus(RCM rm, String operation) {

    }

    @Override
    public ArrayList<RCM> getMachines() {
        return machines;
    }


    @Override
    public Interface.Iterator getIterator() {
        return new RCMIterator(machines);
    }

}
