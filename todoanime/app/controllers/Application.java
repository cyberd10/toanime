package controllers;

import java.sql.SQLException;
import java.util.*;
import javax.persistence.PrePersist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import play.*;
import play.Play;
import play.mvc.*;

import models.*;
import play.data.validation.*;
import play.libs.*;
import play.cache.*;
import play.db.DB;

public class Application extends Controller {
    Connection conn = DB.getConnection();

    @Before
    static void addDefaults() {
        renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
        renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
    }

    public static void index() {
        Noticia frontNoticia = Noticia.find("order by fechaSubida desc").first();

        List<Noticia> olderNoticias = Noticia.find("order by fechaSubida desc").from(1).fetch(10);
        render(frontNoticia, olderNoticias);
    }

    public static void show(Long id) {
        Noticia noticia = Noticia.findById(id);
        String randomID = Codec.UUID();
        render(noticia, randomID);
    }

    public static void noticiaComentario(Long noticiaId, @Required(message = "Es requerido un autor") String author, @Required(message = "Es requerido un mensaje") String content, @Required(message = "Porfavor escribe el codigo") String code, String randomID) {
        Noticia noticia = Noticia.findById(noticiaId);
        validation.equals(code, Cache.get(randomID)).message("Codigo invalido, porfavor prueba de nuevo");
        if (validation.hasErrors()) {
            render("Application/show.html", noticia, randomID);
        }
        noticia.agregarComentario(author, content);
        flash.success("Gracias por comentar %s", author);
        Cache.delete(randomID);
        show(noticiaId);
    }

    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#E4EAFD");
        Cache.set(id, code, "10mn");
        renderBinary(captcha);
    }

     

}
