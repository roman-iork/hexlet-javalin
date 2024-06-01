package org.example;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.rendering.template.TemplateUtil;
import io.javalin.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.CourseDAO;
import org.example.dao.UserDAO;
import org.example.dto.BuildCoursePage;
import org.example.dto.BuildUserPage;


import java.sql.DriverManager;
import java.sql.SQLException;


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
        app.get("/", ctx -> ctx.render("startPage.jte"));
        //set handlers for users
        app.get("/users", ctx -> {
            var users = userDAO.receiveAll();
            ctx.render("usersPage.jte", TemplateUtil.model("users", users));
        });
        app.get("/users/new", ctx -> {
            var page = new BuildUserPage();
            ctx.render("userBuildPage.jte", TemplateUtil.model("page", page));
        });
        app.post("/users", ctx -> {
            var firstName = ctx.formParam("firstName").trim();
            var email = ctx.formParam("email").trim().toLowerCase();
            try {
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(pswd -> pswd.equals(passwordConfirmation), "Passwords don't match, ups!")
                        .get();
                var newUser = new User(firstName, email, password);
                userDAO.save(newUser);
                ctx.redirect("/users");
            } catch (ValidationException e) {
                var page = new BuildUserPage(firstName, email, e.getErrors());
                ctx.render("userBuildPage.jte", TemplateUtil.model("page", page));
            }
        });
        app.post("/users/del", ctx -> {
            var firstName = ctx.formParam("firstName").trim();
            userDAO.delete(firstName);
            ctx.redirect("/users");
        });
        //set handlers for courses
        app.get("/courses", ctx -> {
            var courses = courseDAO.receiveAll();
            ctx.render("coursesPage.jte", TemplateUtil.model("courses", courses));
        });
        app.get("/courses/new", ctx -> {
            var page = new BuildCoursePage();
            ctx.render("courseBuildPage.jte", TemplateUtil.model("page", page));
        });
        app.post("/courses", ctx -> {
            var body = ctx.formParam("body");
            var name = StringUtils.capitalize(ctx.formParam("name").trim().toLowerCase());
            var description = ctx.formParam("description");
            try {
                name = StringUtils.capitalize(ctx.formParamAsClass("name", String.class)
                        .check(nm -> nm.length() > 2, "Name should be more then two symbols!")
                        .get().trim().toLowerCase());
                description = ctx.formParamAsClass("description", String.class)
                        .check(dsc -> dsc.length() > 10, "Description should be more than ten symbols")
                        .get();
                var new_course = new Course(name, description, body);
                courseDAO.save(new_course);
                ctx.redirect("/courses");
            } catch (ValidationException e) {
                name = name.length() > 2 ? name : null;
                description = description.length() > 10 ? description : null;
                var page = new BuildCoursePage(name, description, body, e.getErrors());
                ctx.render("courseBuildPage.jte", TemplateUtil.model("page", page));
            }
        });
        app.post("/courses/del", ctx -> {
            var name = StringUtils.capitalize(ctx.formParam("name").trim().toLowerCase());
            courseDAO.delete(name);
            ctx.redirect("/courses");
        });
        //start app at port 7070
        app.start(7070);
    }
}
