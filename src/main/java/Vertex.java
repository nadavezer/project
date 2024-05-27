import java.util.ArrayList;

public class Vertex {
    ArrayList<ItemOrder> itemOrder;
    Coordinate coordinate;

    public Vertex(ArrayList<ItemOrder> itemOrder, Coordinate coordinate) {
        this.itemOrder = itemOrder;
        this.coordinate = coordinate;
    }

    public ArrayList<ItemOrder> getItemOrder() {
        return itemOrder;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        String ret = this.coordinate + "->{";
        for (ItemOrder i : this.itemOrder){
            ret = ret + i.toString() + ",";
        }
        ret = ret + "}";
        return ret;
    }
}
