package org.example.controllers;
import io.javalin.http.Context;
import io.javalin.rendering.template.TemplateUtil;
import io.javalin.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.example.utils.NamedRoutes;
import org.example.User;
import org.example.dao.UserRepository;
import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.dto.*;

import java.sql.SQLException;

public class UsersController {

    public static void index(Context ctx, UserRepository userDAO) throws SQLException {
        var users = userDAO.receiveAll();
        var page = new UsersPage(users);
        var auth = ctx.sessionAttribute("auth");
        if (auth != null) {
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("usersPage.jte", model("page", page));
        } else {
            ctx.sessionAttribute("path", NamedRoutes.pathUsers());
            ctx.redirect(NamedRoutes.pathSessionBuild());
        }
    }
    public static void show(Context ctx, UserRepository userDAO) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = userDAO.find(id);
        var page = new UserPage(user);
        ctx.render("userPage.jte", model("page", page));
    }
    public static void build(Context ctx) {
        var page = new BuildUserPage();
        ctx.render("userBuildPage.jte", TemplateUtil.model("page", page));
    }

    public static void create(Context ctx, UserRepository userDAO) throws SQLException {
        var email = ctx.formParam("email").trim().toLowerCase();
        try {
            var passwordConfirmation = ctx.formParam("passwordConfirmation");
            var firstName = ctx.formParamAsClass("firstName", String.class)
                    .check(nm -> !userDAO.contains(nm), "Such name already exists")
                    .check(nm -> nm.equals(StringUtils.capitalize(nm.toLowerCase())), "Name should be in Lower case and Capitalised!")
                    .get()
                    .trim();
            var password = ctx.formParamAsClass("password", String.class)
                    .check(pswd -> pswd.equals(passwordConfirmation), "Passwords don't match, ups!")
                    .get();
            var newUser = new User(firstName, email, password);
            userDAO.save(newUser);
            ctx.sessionAttribute("flash", "User was successfully added!");
            ctx.sessionAttribute("auth", "done");
            ctx.redirect(NamedRoutes.pathUsers());
        } catch (ValidationException e) {
            var firstName = ctx.formParam("firstName").trim();
            var page = new BuildUserPage(firstName, email, e.getErrors());
            page.setFlash("User was NOT added!");
            ctx.render("userBuildPage.jte", TemplateUtil.model("page", page));
        }
    }
    public static void edit(Context ctx, UserRepository userDAO) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var page = new UserPage(userDAO.find(id));
        ctx.render("userEditPage.jte", model("page", page));
    }
    public static void update(Context ctx, UserRepository userDAO) throws SQLException{
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = userDAO.find(id);
        var newFirstName = ctx.formParam("firstName").isEmpty() ? user.getFirstName() : ctx.formParam("firstName");
        var newEmail = ctx.formParam("email").isEmpty() ? user.getEmail() : ctx.formParam("email");
        var newPassword = ctx.formParam("password").isEmpty() ? user.getPassword() : ctx.formParam("password");
        user.setFirstName(newFirstName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        userDAO.save(user);
        ctx.redirect(NamedRoutes.pathUsers() + "/" + user.getId());
    }
    public static void delete(Context ctx, UserRepository userDAO) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = userDAO.find(id);
        userDAO.delete(user.getFirstName());
        ctx.redirect(NamedRoutes.pathUsers());
    }
}
