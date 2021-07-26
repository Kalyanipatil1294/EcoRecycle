package Interface;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface RecyclingMachineInterface {
    void recycle(RecyclableItem items);
    String getMachineId();
    String getStatus();
    void setStatus(String st);
    String getMachineLocation();
    double getTotalWeight();
    ArrayList<RecyclableItem> getRecyclableItemsList();
    LocalDateTime getLastEmptiedTime();
    double getAvailableMoney();
    void addItemsToRCM(RecyclableItem items);
    void emptyRCM();
}
