import java.sql.Date;

public class Item {
    int id;
    String name;
    int weight;
    java.sql.Date expdate;
    int cost;
    int type;
    int supplierid;

    public Item(int id, String name, int weight, Date expdate, int cost, int type, int supplierid) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.expdate = expdate;
        this.cost = cost;
        this.type = type;
        this.supplierid = supplierid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Date getExpdate() {
        return expdate;
    }

    public int getCost() {
        return cost;
    }

    public int getType() {
        return type;
    }

    public int getSupplierid() {
        return supplierid;
    }

    public String toString(){//overriding the toString() method
        return this.name;
    }
}

