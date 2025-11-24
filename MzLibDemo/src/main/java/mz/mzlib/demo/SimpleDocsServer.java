package mz.mzlib.demo;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleDocsServer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java mz.mzlib.demo.SimpleDocsServer <deployDir> <port>");
            System.exit(1);
        }
        
        String deployDir = args[0];
        int port = Integer.parseInt(args[1]);
        
        try {
            new SimpleDocsServer(deployDir, port);
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public SimpleDocsServer(String deployDir, int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // 创建文件服务处理器
        HttpHandler fileHandler = exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }

            Path filePath = Paths.get(deployDir + path);

            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                String contentType = getContentType(filePath.toString());
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, Files.size(filePath));

                try (OutputStream os = exchange.getResponseBody()) {
                    Files.copy(filePath, os);
                }
            } else {
                // 尝试添加 .html 扩展名
                Path htmlPath = Paths.get(deployDir + path + ".html");
                if (Files.exists(htmlPath)) {
                    exchange.getResponseHeaders().set("Content-Type", "text/html");
                    exchange.sendResponseHeaders(200, Files.size(htmlPath));

                    try (OutputStream os = exchange.getResponseBody()) {
                        Files.copy(htmlPath, os);
                    }
                } else {
                    // 404
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                }
            }
        };
        
        // 创建目录列表处理器
        HttpHandler dirHandler = new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String path = exchange.getRequestURI().getPath();
                Path dirPath = Paths.get(deployDir + path);
                
                if (Files.exists(dirPath) && Files.isDirectory(dirPath)) {
                    StringBuilder html = new StringBuilder();
                    html.append("<!DOCTYPE html><html><head><title>Directory Listing</title></head><body>");
                    html.append("<h1>Directory Listing: ").append(path).append("</h1>");
                    html.append("<ul>");
                    
                    Files.list(dirPath).sorted().forEach(p -> {
                        String name = p.getFileName().toString();
                        String href = path.endsWith("/") ? path + name : path + "/" + name;
                        if (Files.isDirectory(p)) {
                            html.append("<li><a href=\"").append(href).append("\">").append(name).append("/</a></li>");
                        } else {
                            html.append("<li><a href=\"").append(href).append("\">").append(name).append("</a></li>");
                        }
                    });
                    
                    html.append("</ul></body></html>");
                    
                    String response = html.toString();
                    exchange.getResponseHeaders().set("Content-Type", "text/html");
                    exchange.sendResponseHeaders(200, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } else {
                    fileHandler.handle(exchange);
                }
            }
        };
        
        server.createContext("/", dirHandler);
        server.setExecutor(null);
        server.start();
        
        System.out.println("✅ Server started successfully on port " + port);
        System.out.println("📁 Serving deploy directory: " + deployDir);
        System.out.println("🌐 Open http://localhost:" + port + "/MzLib/index.html in your browser");
        System.out.println("⏹️  Press Ctrl+C to stop the server");
        
        // 保持服务器运行
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("\n⏹️  Shutting down server...");
            server.stop(0);
            Thread.currentThread().interrupt();
            return;
        }
    }
    
    private static String getContentType(String filePath) {
        if (filePath.endsWith(".html")) return "text/html";
        if (filePath.endsWith(".css")) return "text/css";
        if (filePath.endsWith(".js")) return "application/javascript";
        if (filePath.endsWith(".png")) return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
        if (filePath.endsWith(".gif")) return "image/gif";
        if (filePath.endsWith(".svg")) return "image/svg+xml";
        if (filePath.endsWith(".pdf")) return "application/pdf";
        return "text/plain";
    }
}