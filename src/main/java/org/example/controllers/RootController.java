package org.example.controllers;

import io.javalin.http.Context;
import io.javalin.rendering.template.TemplateUtil;
import org.example.dto.MainPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RootController{

    public static void showRoot(Context ctx) {
        var date = ctx.cookie("date");
        var page = new MainPage(date);
        ctx.render("startPage.jte", TemplateUtil.model("page", page));
        var currentDate = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy___HH:mm:ss");
        var curDateFrmtd = currentDate.format(formatter);
        ctx.cookie("date", curDateFrmtd);
    }
}
