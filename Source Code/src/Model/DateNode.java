package Model;

import java.util.Date;

public class DateNode {
    DateNode next;

    public DateNode(DateNode next, Date emptiedDate) {
        this.next = null;
        this.emptiedDate = emptiedDate;
    }

    Date emptiedDate;
}
