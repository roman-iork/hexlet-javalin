package org.example;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.rendering.template.TemplateUtil;
import org.example.dao.CourseDAO;
import org.example.dao.UserDAO;
import org.example.dto.MainPage;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class App {
    private static final String url = "";
    private static final String username = "";
    private static final String password = "";

    public static void main(String[] args) throws SQLException {
        //create connection
        var connection = DriverManager.getConnection(url, username, password);
        //create userDAO
        var userDAO = new UserDAO(connection);
        var courseDAO = new CourseDAO(connection);
        //now make Javalin application:
        //configure app
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        //set handlers for start page
        app.get("/", ctx -> {
            var date = ctx.cookie("date");
            var page = new MainPage(date);
            ctx.render("startPage.jte", TemplateUtil.model("page", page));
            var currentDate = LocalDateTime.now();
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy___HH:mm:ss");
            var curDateFrmtd = currentDate.format(formatter);
            ctx.cookie("date", curDateFrmtd);
        });
        //set handlers for users
        app.get(NamedRoutes.pathUsers(), ctx -> UsersController.index(ctx, userDAO));
        app.get(NamedRoutes.pathUsersNew(), UsersController::build);
        app.get(NamedRoutes.pathUser(), ctx -> UsersController.show(ctx, userDAO));
        app.get(NamedRoutes.pathUserEdit(), ctx -> UsersController.edit(ctx, userDAO));
        app.post(NamedRoutes.pathUsers(), ctx -> UsersController.create(ctx, userDAO));
        app.post(NamedRoutes.pathUserDel(), ctx -> UsersController.delete(ctx, userDAO));
        app.post(NamedRoutes.pathUserUpdate(), ctx -> UsersController.update(ctx, userDAO));
        //set handlers for courses
        app.get(NamedRoutes.pathCourses(), ctx -> CoursesController.index(ctx, courseDAO));
        app.get(NamedRoutes.pathCoursesNew(), CoursesController::build);
        app.get(NamedRoutes.pathCourse("{id}"), ctx -> CoursesController.show(ctx, courseDAO));
        app.post(NamedRoutes.pathCourses(), ctx -> CoursesController.create(ctx, courseDAO));
        app.post(NamedRoutes.pathCoursesDel("{id}"), ctx -> CoursesController.delete(ctx, courseDAO));
        app.get(NamedRoutes.pathCourseEdit("{id}"), ctx -> CoursesController.edit(ctx, courseDAO));
        app.post(NamedRoutes.pathCourseUpdate("{id}"), ctx -> CoursesController.update(ctx, courseDAO));
        //start app at port 7070
        app.start(7070);
    }
}
