package org.example;

import io.javalin.http.Context;
import io.javalin.rendering.template.TemplateUtil;
import io.javalin.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.CourseDAO;
import org.example.dto.BuildCoursePage;
import org.example.dto.CoursePage;
import org.example.dto.CoursesPage;

import java.sql.SQLException;

public class CoursesController {

    public static void show(Context ctx, CourseDAO courseDAO) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = courseDAO.find(id);
        var page = new CoursePage(course);
        ctx.render("coursePage.jte", TemplateUtil.model("page", page));
    }

    public static void index(Context ctx, CourseDAO courseDAO) throws SQLException {
        var courses = courseDAO.receiveAll();
        var page = new CoursesPage(courses);
        var auth = ctx.sessionAttribute("auth");
        if (auth != null) {
            ctx.render("coursesPage.jte", TemplateUtil.model("page", page));
        } else {
            ctx.redirect(NamedRoutes.pathSessionBuild());
        }
    }

    public static void delete(Context ctx, CourseDAO courseDAO) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var name = courseDAO.find(id).getName();
        courseDAO.delete(name);
        ctx.redirect(NamedRoutes.pathCourses());
    }

    public static void edit(Context ctx, CourseDAO courseDAO) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = courseDAO.find(id);
        var page = new CoursePage(course);
        ctx.render("courseEditPage.jte", TemplateUtil.model("page", page));
    }

    public static void update(Context ctx, CourseDAO courseDAO) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = courseDAO.find(id);
        var name = ctx.formParam("name");
        var description = ctx.formParam("description");
        var body = ctx.formParam("body");
        name = name.isEmpty() ? course.getName() : name;
        description = description.isEmpty() ? course.getDescription() : description;
        body = body.isEmpty() ? course.getBody() : body;
        course.setName(name);
        course.setDescription(description);
        course.setBody(body);
        courseDAO.save(course);
        ctx.redirect(NamedRoutes.pathCourses());
    }

    public static void build(Context ctx) {
        var auth = ctx.sessionAttribute("auth");
        if (auth != null) {
            var page = new BuildCoursePage();
            ctx.render("courseBuildPage.jte", TemplateUtil.model("page", page));
        } else {
            ctx.redirect(NamedRoutes.pathSessionBuild());
        }
    }

    public static void create(Context ctx, CourseDAO courseDAO) throws SQLException{
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
            ctx.redirect(NamedRoutes.pathCourses());
        } catch (ValidationException e) {
            name = name.length() > 2 ? name : null;
            description = description.length() > 10 ? description : null;
            var page = new BuildCoursePage(name, description, body, e.getErrors());
            ctx.render("courseBuildPage.jte", TemplateUtil.model("page", page));
        }
    }
}
