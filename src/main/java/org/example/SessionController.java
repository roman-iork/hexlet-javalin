package org.example;

import io.javalin.http.Context;
import io.javalin.rendering.template.TemplateUtil;
import io.javalin.validation.ValidationException;
import org.example.dao.UserDAO;
import org.example.dto.LoginPage;

import java.sql.SQLException;

public class SessionController {
    public static void build(Context ctx) {
        var page = new LoginPage();
        ctx.render("loginPage.jte", TemplateUtil.model("page", page));
    }

    public static void check(Context ctx, UserDAO userDAO) throws SQLException {
        try {
            var firstName = ctx.formParamAsClass("firstName", String.class)
                    .check(userDAO::contains, "No such user!")
                    .get();
            var user = userDAO.find(firstName);
            var validPsw = user.getPassword();

            ctx.formParamAsClass("password", String.class)
                    .check(etps -> etps.equals(validPsw), "Wrong password!").get();
            String jsessionid = "123456789";
            ctx.sessionAttribute("auth", jsessionid);
            ctx.redirect("/");
        } catch (ValidationException e) {
            var firstName = ctx.formParam("firstName");
            var page = new LoginPage(firstName, e.getErrors());
            ctx.render("loginPage.jte", TemplateUtil.model("page", page));
        }
    }

    public static void out(Context ctx) {
        ctx.sessionAttribute("auth", null);
        ctx.redirect("/");
    }
}
