package ecorecycle;

import Interface.Iterator;

import java.util.ArrayList;

public class RCMIterator implements Iterator {
    int index;
    ArrayList<RCM> machines;

    public RCMIterator(ArrayList<RCM> machines) {
        this.machines = machines;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        if(index < machines.size()){
            return true;
        }
        return false;
    }

    @Override
    public RCM next() {
        if(this.hasNext()){
            return machines.get(index++);
        }
        return null;
    }
}
