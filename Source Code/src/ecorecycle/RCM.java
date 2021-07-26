package ecorecycle;

import Interface.RecyclableItem;
import Interface.RecyclingMachineInterface;
import Model.RecycledItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;

public class RCM extends Observable implements RecyclingMachineInterface {
    String machineId;
    String location;
    double weight;
    double currentWeight;
    double availableMoney;
    double money;
    String status;
    int noOfTimesUsed = 0;

    LocalDateTime lastEmptied;
    ArrayList<RecyclableItem> recyclableItems = new ArrayList<>();

    public RCM() {
    }

    public RCM(String machineId, String location, double weight, double money) {
        this.machineId = machineId;
        this.location = location;
        this.weight = weight;
        this.money = money;
        this.status= "active";
        this.currentWeight = 0.0;
    }

    public String getStatus() {
        return status;
    }

    public int getNoOfTimesUsed() {
        return noOfTimesUsed;
    }

    public void setStatus(String status) {
        this.status = status;
        setChanged();
        notifyObservers();
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void addItems(RecyclableItem item) {
        recyclableItems.add(item);
    }

    @Override
    public void recycle(RecyclableItem items) {

    }

    @Override
    public String getMachineId() {
        return machineId;
    }

    @Override
    public String getMachineLocation() {
        return location;
    }

    @Override
    public double getTotalWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        setChanged();
        notifyObservers();
    }

    @Override
    public ArrayList<RecyclableItem> getRecyclableItemsList() {
        return recyclableItems;
    }

    @Override
    public LocalDateTime getLastEmptiedTime() {
        System.out.println("lastEmptied");
        System.out.println(lastEmptied);
        return lastEmptied;
    }

    @Override
    public double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(double availableMoney) {
        this.availableMoney = availableMoney;
        setChanged();
        notifyObservers();
    }

    @Override
    public void addItemsToRCM(RecyclableItem item) {
        recyclableItems.add(item);
        setChanged();
        notifyObservers();
    }

    @Override
    public void emptyRCM() {
        currentWeight = 0;
        this.lastEmptied = LocalDateTime.now();
        System.out.println("in empty");
        System.out.println(lastEmptied);
        setChanged();
        notifyObservers();
    }

    public boolean validateWeight(RecycledItem item) {
        double newWeight = currentWeight + item.getWeight();
        return weight >= newWeight;
    };

    public void setLocation(String location) {
        this.location = location;
        setChanged();
        notifyObservers();
    }

    public void addRecycledItem(ArrayList<RecycledItem> items) {
        for (RecycledItem item : items) {
            currentWeight = currentWeight + item.getWeight();
        }
    }

    public Double calculateUserReward(ArrayList<RecycledItem> items) {
        double count = 0.0;
        double transactionWeight = 0.0;
        for (RecycledItem item : items) {
            for (RecyclableItem rt : recyclableItems) {
                if (rt.getItemName().equalsIgnoreCase(item.getName())) {
                    count = count + rt.getPrice() * item.getWeight();
                    transactionWeight = transactionWeight + item.getWeight();
                }
            }
        }
        noOfTimesUsed++;
        currentWeight = currentWeight + transactionWeight;
        setChanged();
        notifyObservers();
        return count;
    }

    public boolean checkIfCashAvailable(double price) {
        if (availableMoney - price < 0) {
            return false;
        } else {
            availableMoney = availableMoney - price;
            return true;
        }
    }
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
        setChanged();
        notifyObservers();
    }

    public String toString() {
        System.out.println("machineId" + machineId + "location" + location);
        System.out.println("size" + recyclableItems.size());
        System.out.println("Last Empty" + lastEmptied);
        return "";
    }
    public boolean checkIfItemAvailable(String itemName) {
        int count = 0;
        for (RecyclableItem rt : recyclableItems) {
            if (rt.getItemName().equalsIgnoreCase(itemName)) {
                count++;
            }
        }
        return count > 0;
    }

}
