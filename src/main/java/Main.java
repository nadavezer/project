import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class Main {
    static String url = "jdbc:mysql://localhost:3306/project_db";
    static String uname = "root";
    static String password = "password";


    public static double distance(Coordinate s, Coordinate e) throws IOException, InterruptedException, URISyntaxException {
        double distance = 0;
        String ORSurl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248c6cd819e9caa4c3eab19abf5496e0a1a&start="+s.getLatitude()+","+s.getLongitude()+"&end="+s.getLatitude()+","+ e.getLongitude();
        System.out.println(ORSurl);

        HttpRequest getRequest = HttpRequest.newBuilder().uri(new URI(ORSurl)).build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        //System.out.println(getResponse.body());

        String geoJsonString = getResponse.body();
        JSONObject geoJsonObject = new JSONObject(geoJsonString);
        JSONArray segmentsArray = geoJsonObject.getJSONArray("features").getJSONObject(0)
                .getJSONObject("properties").getJSONArray("segments");
        distance = segmentsArray.getJSONObject(0).getDouble("distance");

        //System.out.println("Distance: " + distance);


        return distance;


    }

    public static void order(Map<Item, Integer> items,int gathering_area_id,PriorityQueue<ItemOrder> itemorder){

        String date = (DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(LocalDateTime.now());
        String order_insertion = "INSERT INTO `project_db`.`order` (`order_date`, `gathering_area_id`) VALUES ('"+date+"', '"+gathering_area_id+"');";
        String q = "SELECT max(order_id)FROM `order`";
        int order_id = 1;
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeUpdate(order_insertion);
            ResultSet result = statement.executeQuery(q);
            while(result.next()){
                order_id = (result.getInt(1));
            }

            for (Map.Entry<Item, Integer> entery : items.entrySet()){
                order_insertion = "INSERT INTO `project_db`.`order_item_relation` (`order_id`, `item_id`, `quantity`) VALUES ('"+ order_id+"', '"+entery.getKey().id+"', '"+entery.getValue() +"');";
                itemorder.add(new ItemOrder(entery.getKey(),entery.getValue(),order_id));
                statement.executeUpdate(order_insertion);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

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
                System.out.println("cap at: ");
                break;
            }
        }
        System.out.println("load weight"+totalw);
        for (Vehicle v : availablevehicles){
            if (v.getCapacity() > totalw){
                return v;
            }
        }
        return availablevehicles.get(availablevehicles.size()-1);
    }
    public static Map<Item,Integer> exampleorder(String querry,int quantity){
        Map<Item, Integer> items = new HashMap<>();
        try {
            Connection con = DriverManager.getConnection(url,uname,password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(querry);
            while (result.next()){
                int id = result.getInt("item_id");
                String name= result.getString("name");
                int weight = result.getInt("weight");
                java.sql.Date expdate = result.getDate("exp_date");
                int cost = result.getInt("cost");
                int type = result.getInt("type");
                int supplierid = result.getInt("supplier_id");
                items.put(new Item(id, name, weight,expdate,cost,type,supplierid),quantity);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return items;
    }


    public static void main(String[] args) throws SQLException, IOException, URISyntaxException, InterruptedException {
        //String querry = "SELECT * FROM project_db.item order by cost asc, weight desc limit 10";
        //Map<Item, Integer> items = new HashMap<>();
        PriorityQueue<ItemOrder> itemorder = new PriorityQueue<ItemOrder>(new Comparator<ItemOrder>() {
            @Override
            public int compare(ItemOrder o1, ItemOrder o2) {
                return -((o1.quanity/o1.item.getType()) - (o2.quanity/o2.item.getType()));
            }
        });
       /* try {
            Connection con = DriverManager.getConnection(url,uname,password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(querry);
            while (result.next()){
                int id = result.getInt("item_id");
                String name= result.getString("name");
                int weight = result.getInt("weight");
                java.sql.Date expdate = result.getDate("exp_date");
                int cost = result.getInt("cost");
                int type = result.getInt("type");
                int supplierid = result.getInt("supplier_id");
                items.put(new Item(id, name, weight,expdate,cost,type,supplierid),10);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }*/
        String querry1 = "SELECT * FROM project_db.item order by cost asc, weight desc limit 10";
        String querry2 = "SELECT * FROM project_db.item where type != 1 order by weight desc limit 7";

        Map<Item,Integer> items1 = exampleorder(querry1,5);
        Map<Item,Integer> items2 = exampleorder(querry2,7);
        System.out.println(items1);
        System.out.println(items2);
        System.out.println("-------------");

        order(items1,1,itemorder);
        order(items2,2,itemorder);
        System.out.println("itmens in order queue:"+itemorder);


        System.out.println("-----------------");
        Delivery d = new Delivery(itemorder);
        d.suppliergraph.printGraph();
        System.out.println();
        d.areagraph.printGraph();
        System.out.println("-----------------");
        for (Coordinate coordinate : (d.areagraph.getPath())) {
            System.out.println(coordinate);
        }
        System.out.println("==");

        for (Coordinate coordinate : (d.suppliergraph.getPath())) {
            System.out.println(coordinate);

        }


    }
}
