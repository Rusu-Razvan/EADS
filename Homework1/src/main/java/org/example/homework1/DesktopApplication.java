package org.example.homework1;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DesktopApplication {
    public static void main(String[] args) {

        String serverUrl = "http://localhost:8080/Homework1_war_exploded/controller-servlet?page=2";


        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl))
                    .header("User-Agent", "Java/17 Desktop Application") // User-Agent-ul conține "Java"
                    .GET()
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Raspuns de la Servlet (Aplicație Desktop)");
            System.out.println("Cod Status HTTP: " + response.statusCode());
            System.out.println("Continut Raspuns: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}