
package org.example;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        app.get("/", ctx -> ctx.result("Hello Mister-Twister!"));
        app.start(7070);
    }
}
