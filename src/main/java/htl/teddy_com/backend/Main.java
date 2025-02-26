package htl.teddy_com.backend;

import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.file.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.createContext("/style.css", new CssHandler());
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
                            <link rel="stylesheet" href="http://localhost:8080/style.css">
                        </head>
                        <body>
                            <div class="login-box">
                                <h2>Login</h2>
                                <form>
                                    <input type="text" placeholder="Username" required>
                                    <input type="password" placeholder="Password" required>
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
}
