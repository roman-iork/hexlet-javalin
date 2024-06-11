package org.example;

import org.example.controllers.CoursesController;
import org.example.controllers.RootController;
import org.example.controllers.SessionController;
import org.example.controllers.UsersController;
import org.example.dao.BaseRepository;
import org.example.dao.CourseRepository;
import org.example.dao.UserRepository;
import org.example.utils.NamedRoutes;


import java.sql.SQLException;


public class App {

    public static void main(String[] args) throws SQLException {
        //create applecation
        var app = BaseRepository.getApp();
        //create DAOs
        var userDAO = new UserRepository();
        var courseDAO = new CourseRepository();
        //set handlers for start page
        app.get(NamedRoutes.pathRoot(), RootController::showRoot);
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
        //set handlers for session
        app.get(NamedRoutes.pathSessionBuildMain(), SessionController::buildMain);
        app.get(NamedRoutes.pathSessionBuild(), SessionController::build);
        app.post(NamedRoutes.pathSessionCheck(), ctx -> SessionController.check(ctx, userDAO));
        app.get(NamedRoutes.pathSessionOut(), SessionController::out);
        //start app at port 7070
        app.start(7070);
    }
}
