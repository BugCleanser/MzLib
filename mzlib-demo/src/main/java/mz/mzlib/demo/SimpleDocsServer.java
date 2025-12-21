package mz.mzlib.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleDocsServer {
    private static String deployDir;
    private static Vertx vertx;
    
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java mz.mzlib.demo.SimpleDocsServer <deployDir> <port>");
            System.exit(1);
        }
        
        deployDir = args[0];
        int port = Integer.parseInt(args[1]);
        
        // 创建 Vert.x 实例
        vertx = Vertx.vertx();
        
        // 部署服务器 Verticle
        vertx.deployVerticle(new ServerVerticle(port)).onComplete(res -> {
            if (res.succeeded()) {
                System.out.println("✅ Server started successfully on port " + port);
                System.out.println("📁 Serving deploy directory: " + deployDir);
                System.out.println("🌐 Open http://localhost:" + port + "/MzLib/index.html in your browser");
                System.out.println("⏹️  Press Ctrl+C to stop the server");
            } else {
                System.err.println("Failed to start server: " + res.cause().getMessage());
                vertx.close();
                System.exit(1);
            }
        });
        
        // 添加关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n⏹️  Shutting down server...");
            vertx.close();
        }));
    }
    
    private static class ServerVerticle extends AbstractVerticle {
        private final int port;
        
        public ServerVerticle(int port) {
            this.port = port;
        }
        
        @Override
        public void start() {
            HttpServer server = vertx.createHttpServer();
            Router router = Router.router(vertx);
            
            // 处理所有请求
            router.route().handler(this::handleRequest);
            
            server.requestHandler(router).listen(port);
        }
        
        private void handleRequest(RoutingContext ctx) {
            String path = ctx.request().path();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            Path filePath = Paths.get(deployDir + path);
            
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                // 文件存在，直接提供
                String contentType = getContentType(filePath.toString());
                ctx.response()
                    .putHeader("Content-Type", contentType)
                    .sendFile(filePath.toString());
            } else {
                // 尝试添加 .html 扩展名
                Path htmlPath = Paths.get(deployDir + path + ".html");
                if (Files.exists(htmlPath)) {
                    ctx.response()
                        .putHeader("Content-Type", "text/html")
                        .sendFile(htmlPath.toString());
                } else if (Files.exists(filePath) && Files.isDirectory(filePath)) {
                    // 目录列表
                    handleDirectoryListing(ctx, filePath, path);
                } else {
                    // 404
                    ctx.response()
                        .setStatusCode(404)
                        .end("404 Not Found");
                }
            }
        }
        
        private void handleDirectoryListing(RoutingContext ctx, Path dirPath, String requestPath) {
            try {
                StringBuilder html = new StringBuilder();
                html.append("<!DOCTYPE html><html><head><title>Directory Listing</title></head><body>");
                html.append("<h1>Directory Listing: ").append(requestPath).append("</h1>");
                html.append("<ul>");
                
                Files.list(dirPath).sorted().forEach(p -> {
                    String name = p.getFileName().toString();
                    String href = requestPath.endsWith("/") ? requestPath + name : requestPath + "/" + name;
                    if (Files.isDirectory(p)) {
                        html.append("<li><a href=\"").append(href).append("\">").append(name).append("/</a></li>");
                    } else {
                        html.append("<li><a href=\"").append(href).append("\">").append(name).append("</a></li>");
                    }
                });
                
                html.append("</ul></body></html>");
                
                ctx.response()
                    .putHeader("Content-Type", "text/html")
                    .end(html.toString());
            } catch (Exception e) {
                ctx.response()
                    .setStatusCode(500)
                    .end("Error listing directory: " + e.getMessage());
            }
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