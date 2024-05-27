import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.sql.*;

public class Delivery {

    static String url = "jdbc:mysql://localhost:3306/project_db";
    static String uname = "root";
    static String password = "password";

    Graph suppliergraph;
    Graph areagraph;


    Vehicle vehicle;
    ArrayList<ItemOrder> load;

    Map<Coordinate,ArrayList<ItemOrder>> supplierlocs = new HashMap<>();
    Map<Coordinate,ArrayList<ItemOrder>> arealocs = new HashMap<>();


    public static Vehicle choosevehicle(PriorityQueue<ItemOrder>itemorder) {
        ArrayList<Vehicle> availablevehicles = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM project_db.vehicle where availability = 1;");
            while (result.next()) {
                int id = result.getInt(1);
                int cap = result.getInt(2);
                availablevehicles.add(new Vehicle(id, cap));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        availablevehicles.sort(new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return o1.capacity - o2.capacity;
            }
        });
        int totalw =0;
        for(ItemOrder i : itemorder){
            totalw = totalw + i.getItem().getWeight()*i.quanity;
            if(totalw > availablevehicles.getFirst().getCapacity()){
                break;
            }
        }
        for (Vehicle v : availablevehicles){
            if (v.getCapacity() > totalw){
                return v;
            }
        }
        return availablevehicles.get(availablevehicles.size()-1);
    }


    public Delivery(PriorityQueue<ItemOrder> Items) throws IOException, URISyntaxException, InterruptedException {
        Vehicle vehicle = choosevehicle(Items);
        this.load = new ArrayList<>();
        int cargoweight = 0;
        while (!Items.isEmpty()) {
            if (cargoweight + Items.peek().getItem().getWeight()*Items.peek().getQuanity() < vehicle.capacity) {
                cargoweight =cargoweight + Items.peek().getItem().getWeight()*Items.peek().getQuanity();
                load.add(Items.peek());
                Items.poll();
            }
            Items.poll();
        }
        System.out.println("loaded: "+load + "weight:" + cargoweight + "on" + vehicle);


        for (ItemOrder i : load) {
                try {
                    Connection con = DriverManager.getConnection(url, uname, password);
                    Statement statement = con.createStatement();
                    ResultSet result = statement.executeQuery("select oir.item_id,s.location as sloc, g.location as gloc from order_item_relation oir \n" +
                            "join item i on oir.item_id = i.item_id\n" +
                            "join Supplier s on s.supplier_id = I.supplier_id \n" +
                            "join project_db.order o on o.order_id = oir.order_id \n" +
                            "join gathering_area g on o.gathering_area_id = g.gathering_area_id \n" +
                            "where oir.item_id = "+i.getItem().getId()+" and o.order_id =" + i.getOrderid());
                    result.next();
                    String[] parts = result.getString("sloc").split(",");
                    Coordinate sloc = new Coordinate(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
                    if(supplierlocs.get(sloc) == null) {
                        this.supplierlocs.put(sloc, new ArrayList<>());
                    }
                        supplierlocs.get(sloc).add(i);
                    parts = result.getString("gloc").split(",");
                    Coordinate gloc = new Coordinate(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
                    if(arealocs.get(gloc) == null) {
                        this.arealocs.put(gloc, new ArrayList<>());
                    }
                    arealocs.get(gloc).add(i);



                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        System.out.println("arealocs: " + arealocs);
        System.out.println("suplocs: " +supplierlocs);

        System.out.println("-----------------");

        areagraph = new Graph(arealocs);
        suppliergraph = new Graph(supplierlocs);
    }

    public Graph getSuppliergraph() {
        return suppliergraph;
    }

    public Graph getAreagraph() {
        return areagraph;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ArrayList<ItemOrder> getLoad() {
        return load;
    }

    public Map<Coordinate, ArrayList<ItemOrder>> getSupplierlocs() {
        return supplierlocs;
    }

    public Map<Coordinate, ArrayList<ItemOrder>> getArealocs() {
        return arealocs;
    }
}
