package org.example;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

import java.util.Objects;

public class App {

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        app.get("/users/{id}/post/{postId}", ctx -> {
            new Data();
            var users = Data.users;
            var posts = Data.posts;
            var usersPosts = Data.usersPosts;
            var userIdParam = ctx.pathParamAsClass("id", Integer.class).get();
            var postIdParam = ctx.pathParamAsClass("postId", Integer.class).get();
            if (users.containsKey(userIdParam)
                    && posts.containsKey(postIdParam)
                    && Objects.equals(usersPosts.get(userIdParam), postIdParam)) {
                var usersPostId = usersPosts.get(userIdParam);
                var post = posts.get(usersPostId);
                ctx.result("Here the post: " + post);
            } else {
                throw new NotFoundResponse("No such user, post or no user has such post id!");
            }
        });
        app.start(7070);
    }
}
