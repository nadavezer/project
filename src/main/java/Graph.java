import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {

    private ArrayList<Vertex> vertices;
    private int[][] adjacencyMatrix;

    ArrayList<Integer> path;

    public static double distance(Coordinate s, Coordinate e) throws IOException, InterruptedException, URISyntaxException {
        double distance = 0;
        String ORSurl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248c6cd819e9caa4c3eab19abf5496e0a1a&start="+s.getLatitude()+","+s.getLongitude()+"&end="+e.getLatitude()+","+ e.getLongitude();
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

    public static ArrayList<Vertex> maptoverticies(Map<Coordinate, ArrayList<ItemOrder>> map) {
        // Step 1: Create a map to collect items by coordinate
        Map<Coordinate, ArrayList<ItemOrder>> groupedMap = map;

        // Step 2: Convert the grouped map to a list of Vertex objects
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (Map.Entry<Coordinate, ArrayList<ItemOrder>> entry : groupedMap.entrySet()) {
            vertices.add(new Vertex(entry.getValue(),entry.getKey()));
        }

        return vertices;
    }


    public Graph(Map<Coordinate, ArrayList<ItemOrder>> map) throws IOException, URISyntaxException, InterruptedException {


        System.out.println("graphing map: "+map);
        adjacencyMatrix = new int[map.size()][map.size()];
        vertices = maptoverticies(map);
        System.out.println("map verticeis"+vertices);

        for(int i = 0; i < vertices.size(); i++){
            for (int j = 0; j < vertices.size(); j++){

                adjacencyMatrix[i][j] = (int) distance(vertices.get((Integer) i).getCoordinate(),vertices.get((Integer)j).getCoordinate());
            }
        }
        this.path = findMinRoute(adjacencyMatrix);

    }

    public void addEdge(int i, int j, int weight) {
        adjacencyMatrix[i][j] = weight;
        adjacencyMatrix[j][i] = weight; // Since the graph is undirected
    }

    public int getWeight(int i, int j) {
        return adjacencyMatrix[i][j];
    }

    public void printGraph() {
        System.out.println(this.vertices);
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public ArrayList<Coordinate> getPath() {
        ArrayList<Coordinate> ret = new ArrayList<>();
        for (int v : this.path){
            ret.add(vertices.get(v).getCoordinate());
        }
        return ret;
    }

    static ArrayList<Integer> findMinRoute(int[][] tsp) {
        int sum = 0;
        int counter = 0;
        int j = 0, i = 0;
        int min = Integer.MAX_VALUE;
        ArrayList<Integer> visitedRouteList = new ArrayList<>();

        // Starting from the 0th indexed city i.e., the first city
        visitedRouteList.add(0);
        int[] route = new int[tsp.length];

        // Traverse the adjacency matrix tsp[][]
        while (counter < tsp.length - 1) {
            min = Integer.MAX_VALUE;
            for (j = 0; j < tsp.length; j++) {
                if (i != j && !visitedRouteList.contains(j)) {
                    if (tsp[i][j] < min) {
                        min = tsp[i][j];
                        route[counter] = j;
                    }
                }
            }
            sum += min;
            visitedRouteList.add(route[counter]);
            i = route[counter];
            counter++;
        }

        // Print the total minimum cost
        System.out.print("Minimum Cost is : ");
        System.out.println(sum);

        // Return the visited route list
        return visitedRouteList;
    }
}
