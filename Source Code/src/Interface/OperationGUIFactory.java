package Interface;

import GUI.AdminView.*;
import GUI.AdminView.TrackRCMGUI;
import GUI.AdminView.Statistics;

public class OperationGUIFactory {
    public OperationGUIInterface createUserInterface(String dataType, RecyclingStation rs) {
        switch (dataType) {
            case "Add/Edit RCM":
                return new AddRCMGUI(rs);

            case "Remove RCM":
                return new RemoveRCMInterface(rs);

            case "Activate RCM":
                return new ChangeRCMStatus(rs, "inactive");

            case "Deactivate RCM":
                return new ChangeRCMStatus(rs, "active");

            case "Update RCM":
                return new UpdateRCMInterface(rs);

            case "Empty RCM":
                return new EmptyInterface(rs);

            case "Track RCM Status":
                return new TrackRCMGUI(rs);

            case "View Statistics":
                return new Statistics(rs);

            default:
                break;
        }
        return null;
    }
}
