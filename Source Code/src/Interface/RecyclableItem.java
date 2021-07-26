package Interface;

public interface RecyclableItem {
    Double getPrice();
    void setPrice(Double price);
    void setWeight(Double weight);
    Double getWeight();
    Double calculatePrice(Double weight);
    String getItemName();
}
