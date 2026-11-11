/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.api;

import sfd.config.ServerConnectionConfig;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 *
 * @author enric
 */



public class ApiClient {

    private final String baseUrl =
            new ServerConnectionConfig().getServerUrl();

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public HttpResponse<String> postJson(
            String endpoint, String json)
            throws IOException, InterruptedException {

        System.out.println("API URL: " + baseUrl + endpoint);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(20))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
    }
    
    
        public HttpResponse<String> getJson(String endpoint)
                throws IOException, InterruptedException {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(20))
                    .GET()
                    .build();

            return client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
        }

}
