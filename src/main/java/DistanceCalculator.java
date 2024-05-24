    public static double distance(Coodinate s, Coodinate e) throws URISyntaxException, IOException, InterruptedException {
        double distance = 0;
        String ORSurl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248c6cd819e9caa4c3eab19abf5496e0a1a&start="+s.getLatitude()+","+s.getLongitude()+"&end="+s.getLatitude()+","+ e.getLongitude();
        System.out.println(ORSurl);

        HttpRequest getRequest = HttpRequest.newBuilder().uri(new URI(ORSurl)).build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(getResponse.body());

        String geoJsonString = getResponse.body();
        JSONObject geoJsonObject = new JSONObject(geoJsonString);
        JSONArray segmentsArray = geoJsonObject.getJSONArray("features").getJSONObject(0)
                .getJSONObject("properties").getJSONArray("segments");
        distance = segmentsArray.getJSONObject(0).getDouble("distance");

        System.out.println("Distance: " + distance);


        return distance;


    }
