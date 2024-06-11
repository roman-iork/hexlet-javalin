package org.example.controllers;

import io.javalin.http.Context;
import io.javalin.rendering.template.TemplateUtil;
import io.javalin.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.example.Course;
import org.example.utils.NamedRoutes;
import org.example.dao.CourseRepository;
import org.example.dto.BuildCoursePage;
import org.example.dto.CoursePage;
import org.example.dto.CoursesPage;

import java.sql.SQLException;

public class CoursesController {

    public static void index(Context ctx, CourseRepository courseRepository) throws SQLException {
        var auth = ctx.sessionAttribute("auth");
        if (auth != null) {
            var courses = courseRepository.receiveAll();
            var page = new CoursesPage(courses);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("coursesPage.jte", TemplateUtil.model("page", page));
        } else {
            ctx.sessionAttribute("path", NamedRoutes.pathCourses());
            ctx.redirect(NamedRoutes.pathSessionBuild());
        }
    }

    public static void show(Context ctx, CourseRepository courseRepository) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = courseRepository.find(id);
        var page = new CoursePage(course);
        ctx.render("coursePage.jte", TemplateUtil.model("page", page));
    }

    public static void build(Context ctx) {
        var auth = ctx.sessionAttribute("auth");
        if (auth != null) {
            var page = new BuildCoursePage();
            ctx.render("courseBuildPage.jte", TemplateUtil.model("page", page));
        } else {
            ctx.sessionAttribute("path", NamedRoutes.pathCoursesNew());
            ctx.redirect(NamedRoutes.pathSessionBuild());
        }
    }

    public static void create(Context ctx, CourseRepository courseRepository) throws SQLException{
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
            courseRepository.save(new_course);
            ctx.sessionAttribute("flash", "Course was successfully created!");
            ctx.redirect(NamedRoutes.pathCourses());
        } catch (ValidationException e) {
            name = name.length() > 2 ? name : null;
            description = description.length() > 10 ? description : null;
            var page = new BuildCoursePage(name, description, body, e.getErrors());
            page.setFlash("Course was NOT created due to errors!");
            ctx.render("courseBuildPage.jte", TemplateUtil.model("page", page));
        }
    }

    public static void edit(Context ctx, CourseRepository courseRepository) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = courseRepository.find(id);
        var page = new CoursePage(course);
        ctx.render("courseEditPage.jte", TemplateUtil.model("page", page));
    }

    public static void update(Context ctx, CourseRepository courseRepository) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = courseRepository.find(id);
        var name = ctx.formParam("name");
        var description = ctx.formParam("description");
        var body = ctx.formParam("body");
        name = name.isEmpty() ? course.getName() : name;
        description = description.isEmpty() ? course.getDescription() : description;
        body = body.isEmpty() ? course.getBody() : body;
        course.setName(name);
        course.setDescription(description);
        course.setBody(body);
        courseRepository.save(course);
        ctx.redirect(NamedRoutes.pathCourses());
    }

    public static void delete(Context ctx, CourseRepository courseRepository) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var name = courseRepository.find(id).getName();
        courseRepository.delete(name);
        ctx.redirect(NamedRoutes.pathCourses());
    }
}
