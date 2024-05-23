import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class DistanceCalculator {

    private static final String API_KEY = "YOUR_OPENROUTESERVICE_API_KEY";
    private static final String BASE_URL = "https://api.openrouteservice.org/v2/matrix/driving-car";

    public static double[][] calculateDistances(List<Coordinate> coordinates) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // Prepare the JSON payload
        StringBuilder locationsBuilder = new StringBuilder("[");
        for (Coordinate coordinate : coordinates) {
            locationsBuilder.append("[").append(coordinate.getLongitude()).append(",").append(coordinate.getLatitude()).append("],");
        }
        locationsBuilder.deleteCharAt(locationsBuilder.length() - 1).append("]");

        String jsonPayload = "{\"locations\":" + locationsBuilder.toString() + "}";

        RequestBody body = RequestBody.create(
                jsonPayload,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .addHeader("Authorization", API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        String responseBody = response.body().string();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode distancesNode = rootNode.path("distances");

        int size = coordinates.size();
        double[][] distances = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                distances[i][j] = distancesNode.get(i).get(j).asDouble();
            }
        }

        return distances;
    }
}
