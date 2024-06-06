package org.example;
import io.javalin.http.Context;
import io.javalin.rendering.template.TemplateUtil;
import io.javalin.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.UserDAO;
import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.dto.*;

import java.sql.SQLException;

public class UsersController {

    public static void index(Context ctx, UserDAO userDAO) throws SQLException {
        var users = userDAO.receiveAll();
        var usersPage = new UsersPage(users);
        var page = new DelUserPage();
        ctx.render("usersPage.jte", model("users", usersPage, "page", page));
    }
    public static void show(Context ctx, UserDAO userDAO) throws SQLException {
        var firstName = ctx.pathParam("firstName");
        var user = userDAO.find(firstName);
        var page = new UserPage(user);
        ctx.render("userPage.jte", model("page", page));
    }
    public static void build(Context ctx) {
        var page = new BuildUserPage();
        ctx.render("userBuildPage.jte", TemplateUtil.model("page", page));
    }
    public static void create(Context ctx, UserDAO userDAO) throws SQLException {
        var firstName = StringUtils.capitalize(ctx.formParam("firstName").trim());
        var email = ctx.formParam("email").trim().toLowerCase();
        try {
            var passwordConfirmation = ctx.formParam("passwordConfirmation");
            var password = ctx.formParamAsClass("password", String.class)
                    .check(pswd -> pswd.equals(passwordConfirmation), "Passwords don't match, ups!")
                    .get();
            var newUser = new User(firstName, email, password);
            userDAO.save(newUser);
            ctx.redirect(NamedRoutes.pathUsers());
        } catch (ValidationException e) {
            var page = new BuildUserPage(firstName, email, e.getErrors());
            ctx.render("userBuildPage.jte", TemplateUtil.model("page", page));
        }
    }
    public static void edit(Context ctx, UserDAO userDAO) throws SQLException {
        var firstName = ctx.pathParam("firstName");
        var page = new UserPage(userDAO.find(firstName));
        ctx.render("userEditPage.jte", model("page", page));
    }
    public static void update(Context ctx, UserDAO userDAO) throws SQLException{
        var firstName = ctx.pathParam("firstName");
        var user = userDAO.find(firstName);
        var newFirstName = ctx.formParam("firstName").isEmpty() ? user.getFirstName() : ctx.formParam("firstName");
        var newEmail = ctx.formParam("email").isEmpty() ? user.getEmail() : ctx.formParam("email");
        var newPassword = ctx.formParam("password").isEmpty() ? user.getPassword() : ctx.formParam("password");
        user.setFirstName(newFirstName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        userDAO.save(user);
        ctx.redirect(NamedRoutes.pathUsers() + "/" + newFirstName);
    }
    public static void delete(Context ctx, UserDAO userDAO) throws SQLException {
        try {
            var usersNames = userDAO.receiveAll();
            var firstName = StringUtils.capitalize(ctx.pathParamAsClass("firstName", String.class)
                    .check(nm -> usersNames.contains(StringUtils.capitalize(nm.toLowerCase())), "No such user, o-ou!")
                    .get().trim());
            userDAO.delete(firstName);
            ctx.redirect(NamedRoutes.pathUsers());
        } catch (ValidationException e) {
            var firstName = ctx.formParam("firstName").trim();
            var usersPage = new UsersPage(userDAO.receiveAll());
            var page = new DelUserPage(firstName, e.getErrors());
            ctx.render("usersPage.jte", TemplateUtil.model("page", page, "users", usersPage));
        }
    }
}
