import java.sql.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class Main {
    static String url = "jdbc:mysql://localhost:3306/project_db";
    static String uname = "root";
    static String password = "password";


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
                System.out.println("cap-");
                break;
            }
        }
        System.out.println(totalw);
        for (Vehicle v : availablevehicles){
            if (v.getCapacity() > totalw){
                return v;
            }
        }
        return availablevehicles.get(availablevehicles.size()-1);
    }

    public static void main(String[] args) throws SQLException {
        String querry = "select * from item where type =1 or type =3 or type =2";
        Map<Item, Integer> items = new HashMap<>();
        PriorityQueue<ItemOrder> itemorder = new PriorityQueue<ItemOrder>(new Comparator<ItemOrder>() {
            @Override
            public int compare(ItemOrder o1, ItemOrder o2) {
                return -((o1.quanity/o1.item.getType()) - (o2.quanity/o2.item.getType()));
            }
        });
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
                items.put(new Item(id, name, weight,expdate,cost,type,supplierid),500);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        order(items,1,itemorder);
        System.out.println(itemorder);
        Vehicle v= (choosevehicle(itemorder));
        System.out.println(v);
        Delivery d = new Delivery(v,itemorder);
        System.out.println(d.supplierlocs);

    }
}
