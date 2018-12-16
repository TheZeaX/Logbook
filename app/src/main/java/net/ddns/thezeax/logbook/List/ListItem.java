package net.ddns.thezeax.logbook.List;

public class ListItem {

    private int id;
    private String price, timestamp, desc, origin, category;

    public ListItem(int id, String price, String timestamp, String desc, String origin, String category) {
        this.id = id;
        this.desc = desc;
        this.timestamp = timestamp;
        this.price = price;
        this.origin = origin;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPrice() {
        return price;
    }

    public String getOrigin() {
        return origin;
    }

    public String getCategory() {
        return category;
    }
}
