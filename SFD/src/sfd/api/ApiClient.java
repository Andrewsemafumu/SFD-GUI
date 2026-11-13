/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.api;

import java.io.ByteArrayOutputStream;
import sfd.config.ServerConnectionConfig;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import sfd.dto.FileUploadDto;

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

    public HttpResponse<String> postJson(String endpoint, String json)
            throws IOException, InterruptedException {

        System.out.println("API URL: " + baseUrl + endpoint);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(20))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(request,HttpResponse.BodyHandlers.ofString());
    }
    
    
        public HttpResponse<String> getJson(String endpoint) throws IOException, InterruptedException {

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
                byte[] encryptedBytes,
                FileUploadDto fileUploadDto)
                throws IOException, InterruptedException {
            
            String boundary = "----Boundary" + UUID.randomUUID();
            String CRLF = "\r\n";
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            // Fields bound by @ModelAttribute
            writeField(out, boundary, "ownerId", String.valueOf(fileUploadDto.getOwnerId()));
            writeField(out, boundary, "folderId", String.valueOf(fileUploadDto.getFolderId()));
            writeField(out, boundary, "encryptedAesKey", fileUploadDto.getEncryptedAesKey());
            
            // File part bound by @RequestParam("file")
            out.write(("--" + boundary + CRLF).getBytes(StandardCharsets.UTF_8));
            out.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileUploadDto.getFileName() + "\"" + CRLF).getBytes(StandardCharsets.UTF_8));
            out.write(("Content-Type: application/octet-stream" + CRLF + CRLF).getBytes(StandardCharsets.UTF_8));
            out.write(encryptedBytes);
            out.write(CRLF.getBytes(StandardCharsets.UTF_8));
            
            // Closing boundardy
            out.write(("--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8));

            

            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/api/file/upload"))
            .header("Content-Type", "multipart/form-data; boundary=" + boundary)
            .POST(HttpRequest.BodyPublishers.ofByteArray(out.toByteArray()))
            .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        
        private void writeField(ByteArrayOutputStream out, String boundary, String name, String value) throws IOException {
            String CRLF = "\r\n";
            out.write(("--" + boundary + CRLF).getBytes(StandardCharsets.UTF_8));
            out.write(("Content-Disposition: form-data; name=\"" + name + "\"" + CRLF + CRLF)
                    .getBytes(StandardCharsets.UTF_8));
            out.write(value.getBytes(StandardCharsets.UTF_8));
            out.write(CRLF.getBytes(StandardCharsets.UTF_8));
        }
        
    
        public HttpResponse<String> downloadEncryptedFile(
                int userId, int fileId)
                throws IOException, InterruptedException {

            String json = "{\"userId\":" + userId
                    + ",\"fileId\":" + fileId + "}";

            return postJson("/api/file/download", json);
        }
}
