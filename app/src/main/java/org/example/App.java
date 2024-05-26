package org.example;

import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        app.get("/hello", ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            var responseString = String.format("Hello %s!", name);
            ctx.result(responseString);
        });
        app.start(7070);
    }
}
