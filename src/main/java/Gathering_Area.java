public class Gathering_Area {
    int id;
    Coordinate location;
    int peoplequantity;

    public Gathering_Area(int id, int peoplequantity,String location) {
        this.id = id;
        this.peoplequantity = peoplequantity;
        String[] parts = location.split(",");
        this.location = new Coordinate(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
    }
}
