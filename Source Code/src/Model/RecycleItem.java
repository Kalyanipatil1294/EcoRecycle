package Model;

import Interface.RecyclableItem;

public class RecycleItem implements RecyclableItem {
    double price;
    String itemName;
    double weight;

    public RecycleItem(String itemName, double price,  double weight) {
        this.price = price;
        this.itemName = itemName;
        this.weight = weight;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public void setPrice(Double price) {
        price = price;
    }

    @Override
    public void setWeight(Double weight) {
        weight = weight;
    }

    @Override
    public Double getWeight() {
        return null;
    }

    @Override
    public Double calculatePrice(Double presentWeight) {
        return price * presentWeight;
    }

    @Override
    public String getItemName() {
        return itemName;
    }
}
