package org.example;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.rendering.template.TemplateUtil;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        //create a couple of courses
        var course1 = new Course(1L, "Java", "Java is cool.\nIt's a language\nthat is fine.");
        var course2 = new Course(2L, "Git", "Git is for version control.\nIt's very useful \ntool.");
        var course3 = new Course(3L, "SQL", "SQL is for tables.\nIt's common language\nfor DataBases.");
        //now make Javalin application:
        //configure app
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        //set handlers
        app.get("/courses", ctx -> {
            var courses = Stream.of(course1, course2, course3)
                    .map(c -> c.getId().intValue())
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" or "));
            ctx.result("Enter 'course/' followed by course id number."
                    + "\nAvailable courses are:\n" + courses);
        });
        app.get("/course/{id}", ctx -> {
            //get path parameter id
            var id = ctx.pathParamAsClass("id", Long.class)
                    .getOrThrow((a) -> new NotFoundResponse("Not a number! Enter course id number."));
            //set logic that figures the requested course
            var course = Stream.of(course1, course2, course3)
                    .filter(c -> c.getId().equals(id))
                            .findFirst().orElseThrow(() -> new NotFoundResponse("No such course!"));
            ctx.render("index.jte", TemplateUtil.model("course", course));
        });
        //start app on port 7070
        app.start(7070);
    }
}
