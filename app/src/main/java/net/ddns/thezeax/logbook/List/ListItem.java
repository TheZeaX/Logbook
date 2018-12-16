package net.ddns.thezeax.logbook.List;

public class ListItem {

    private int id;
    private double price;
    private String timestamp, desc, origin, category;

    public ListItem(int id, double price, String timestamp, String desc, String origin, String category) {
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

    public double getPrice() {
        return price;
    }

    public String getOrigin() {
        return origin;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {

        if(getPrice() == 0) {
            return "#ffffff";
        } else if(getPrice() >= 50) {
            return "#16aa3d";
        } else if(getPrice() >= 10) {
            return "#6bef8f";
        } else if(getPrice() > 0) {
            return "#b3f2c4";
        } else if(getPrice() > (-10)) {
            return "#e0949d";
        } else if(getPrice() > (-50)) {
            return "#e05969";
        } else {
            return "#f40925";
        }
    }
}
