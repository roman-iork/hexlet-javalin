package org.example;

public class NamedRoutes {

    //users
    public static String pathUsers() {
        return "/users";
    }
    public static String pathUsersNew() {
        return "/users/new";
    }
    public static String pathUserDel() {
        return pathUser() + "/del";
    }
    public static String pathUser() {
        return "/users/{id}";
    }
    public static String pathUserEdit() {
        return pathUser() + "/edit";
    }
    public static String pathUserUpdate() {
        return pathUser() + "/update";
    }

    //courses
    public static String pathCourses() {
        return "/courses";
    }
    public static String pathCoursesNew() {
        return "/courses/new";
    }
    public static String pathCoursesDel(Long id) {
        return "/courses/" + id + "/del";
    }
    public static String pathCoursesDel(String id) {
        return "/courses/" + id + "/del";
    }
    public static String pathCourse(String id) {
        return "/courses/" + id;
    }
    public static String pathCourse(Long id) {
        return "/courses/" + id;
    }
    public static String pathCourseEdit(Long id) {
        return "/courses/" + id + "/edit";
    }
    public static String pathCourseEdit(String id) {
        return "/courses/" + id + "/edit";
    }
    public static String pathCourseUpdate(Long id) {
        return pathCourse(id) + "/update";
    }
    public static String pathCourseUpdate(String id) {
        return pathCourse(id) + "/update";
    }

    //session
    public static String pathSessionBuild() {
        return "/session/build";
    }
    public static String pathSessionCheck() {
        return "/session";
    }
    public static String pathSessionOut() {
        return "/session";
    }
}
