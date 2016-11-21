package controllers;
 
import play.*;
import play.mvc.*;
 
import java.util.*;
 
import models.*;
 
@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            Usuario usuario = Usuario.find("byEmail", Security.connected()).first();
            renderArgs.put("usuario", usuario.nombreCompleto);
        }
    }
 
    public static void index() {
        String usuario = Security.connected();
        List<Noticia> noticias = Noticia.find("author.email", usuario).fetch();
        render(noticias);
    }

    public static void form(Long id) {        
        if(id != null) {
            Noticia noticia = Noticia.findById(id);
            render(noticia);
        }
        render();
    }
 
    public static void save(Long id, String titulo, String content, String imagen) {
    Noticia noticia;
    if(id == null) {
        // Crear noticia
        Usuario author = Usuario.find("byEmail", Security.connected()).first();
        noticia = new Noticia(author, titulo, content, imagen);

    } else {
        // Recuperar noticia
        noticia = Noticia.findById(id);
        
        // Editar
        noticia.titulo = titulo;
        noticia.content = content;
        noticia.imagen = imagen;
    }
    
    // Validar
    validation.valid(noticia);
    if(validation.hasErrors()) {
        render("@form", noticia);
    }
    // Save
    noticia.save();
    index();
}


    
    


}