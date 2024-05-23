import java.util.*;
import java.sql.*;

public class Delivery {

    static String url = "jdbc:mysql://localhost:3306/project_db";
    static String uname = "root";
    static String password = "password";


    Vehicle vehicle;
    ArrayList<ItemOrder> load;

    Map<Coordinate,ItemOrder> supplierlocs = new HashMap<>();
    Map<Coordinate,ItemOrder> arealocs = new HashMap<>();


    public Delivery(Vehicle vehicle, PriorityQueue<ItemOrder> Items) {
        this.load = new ArrayList<>();
        int cargoweight = 0;
        while (!Items.isEmpty()) {
            System.out.println(cargoweight);
            if (cargoweight + Items.peek().getItem().getWeight()*Items.peek().getQuanity() < vehicle.capacity) {
                cargoweight =cargoweight + Items.peek().getItem().getWeight()*Items.peek().getQuanity();
                load.add(Items.peek());
                Items.remove();
            }
            Items.remove();
        }
        System.out.println(load);


        for (ItemOrder i : load) {
                try {
                    Connection con = DriverManager.getConnection(url, uname, password);
                    Statement statement = con.createStatement();
                    System.out.println("select oir.item_id,s.location as sloc, g.location as gloc from order_item_relation oir \n" +
                            "join item i on oir.item_id = i.item_id\n" +
                            "join Supplier s on s.supplier_id = I.supplier_id \n" +
                            "join project_db.order o on o.order_id = oir.order_id \n" +
                            "join gathering_area g on o.gathering_area_id = g.gathering_area_id \n" +
                            "where oir.item_id = "+i.getItem().getId()+" and o.order_id =" + i.getOrderid());
                    ResultSet result = statement.executeQuery("select oir.item_id,s.location as sloc, g.location as gloc from order_item_relation oir \n" +
                            "join item i on oir.item_id = i.item_id\n" +
                            "join Supplier s on s.supplier_id = I.supplier_id \n" +
                            "join project_db.order o on o.order_id = oir.order_id \n" +
                            "join gathering_area g on o.gathering_area_id = g.gathering_area_id \n" +
                            "where oir.item_id = "+i.getItem().getId()+" and o.order_id =" + i.getOrderid());
                    result.next();
                    System.out.println(result.getString(2));
                    System.out.println(result.getString(3));
                    String[] parts = result.getString("sloc").split(",");
                    Coordinate sloc = new Coordinate(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
                    this.supplierlocs.put(sloc, i);
                    parts = result.getString("gloc").split(",");
                    Coordinate gloc = new Coordinate(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
                    this.arealocs.put(gloc, i);


                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }

    }
}
