/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.api;

import java.io.File;
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
        
        
        public HttpResponse<String> uploadEncryptedFile(
                File file, byte[] encryptedBytes,
                int ownerId, int folderId, String encryptedAesKey)
                throws IOException, InterruptedException {

            String boundary = "SFD" + System.currentTimeMillis();

            String data =
                    "--" + boundary + "\r\nContent-Disposition: form-data; name=\"ownerId\"\r\n\r\n" + ownerId + "\r\n" +
                    "--" + boundary + "\r\nContent-Disposition: form-data; name=\"folderId\"\r\n\r\n" + folderId + "\r\n" +
                    "--" + boundary + "\r\nContent-Disposition: form-data; name=\"encryptedAesKey\"\r\n\r\n" + encryptedAesKey + "\r\n" +
                    "--" + boundary + "\r\nContent-Disposition: form-data; name=\"file\"; filename=\"" + file.getName().replace("\"", "_").replace("\r", "_").replace("\n", "_") + "\"\r\n" +
                    "Content-Type: application/octet-stream\r\n\r\n";

            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.concat(
                    HttpRequest.BodyPublishers.ofString(data),
                    HttpRequest.BodyPublishers.ofByteArray(encryptedBytes),
                    HttpRequest.BodyPublishers.ofString("\r\n--" + boundary + "--\r\n")
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/file/upload"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(body)
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        
    
        public HttpResponse<String> downloadEncryptedFile(
                int userId, int fileId)
                throws IOException, InterruptedException {

            String json = "{\"userId\":" + userId
                    + ",\"fileId\":" + fileId + "}";

            return postJson("/api/file/download", json);
        }
}
