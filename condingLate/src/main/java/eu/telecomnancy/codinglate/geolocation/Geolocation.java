package eu.telecomnancy.codinglate.geolocation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;

import java.io.InputStreamReader;
import java.net.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Geolocation {

    public static Coordinates getCoordinatesFromAddress(String address) {
        String apiKey = "AIzaSyCntVqBXFiHySULm0rjibkaHbvRO6ZakXA";
        try {
            address = address.replace(" ", "+");
            address = URLEncoder.encode(address, StandardCharsets.UTF_8);
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?key=" + apiKey + "&address=" + address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();

                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Parse JSON response
                String jsonResponse = response.toString();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                if (jsonObject.has("results")) {
                    try {
                        JsonObject results = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject();
                        JsonObject geometry = results.getAsJsonObject("geometry");
                        JsonObject location = geometry.getAsJsonObject("location");
                        double latitude = location.get("lat").getAsDouble();
                        double longitude = location.get("lng").getAsDouble();
                        return new Coordinates(latitude, longitude);
                    }catch (IndexOutOfBoundsException e) {
                        System.out.println("No results found.");}

                } else {
                    System.out.println("No results found.");
                }

            } else {
                System.out.println("HTTP Request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static double getDistance(Person person, Offer offer) {
        if (person.getAddress() == null || offer.getUser() == null || offer.getUser().getAddress() == null) {
            return -1;
        }
        try {
            Coordinates coordinatesPerson = Geolocation.getCoordinatesFromAddress(person.getAddress().getAddress());
            Coordinates coordinatesOffer = Geolocation.getCoordinatesFromAddress(offer.getUser().getAddress().getAddress());
            assert coordinatesPerson != null;
            assert coordinatesOffer != null;
            return coordinatesPerson.distance(coordinatesOffer);
        } catch (NullPointerException e) {
            return -1;
        }

    }
    public static void main(String[] args) {
        Coordinates coordinates1 = Geolocation.getCoordinatesFromAddress("6 rue des Roses, 67520 Wangen");
        Coordinates coordinates2 = Geolocation.getCoordinatesFromAddress("193 Av. Paul Muller, 54600 Villers-lès-Nancy");
        assert coordinates1 != null;
        assert coordinates2 != null;
        System.out.println(coordinates1.distance(coordinates2));
    }
}

