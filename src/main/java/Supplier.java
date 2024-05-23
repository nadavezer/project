public class Supplier {

    int supplierid;
    String name;
    Coordinate location;

    public Supplier(int supplierid, String name, String location) {
        this.supplierid = supplierid;
        this.name = name;
        String[] parts = location.split(",");
        this.location = new Coordinate(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
    }

    public int getSupplierid() {
        return supplierid;
    }

    public String getName() {
        return name;
    }

    public Coordinate getLocation() {
        return location;
    }
}
