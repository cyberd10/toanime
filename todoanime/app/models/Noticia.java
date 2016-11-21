package models;

import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Noticia extends Model {

    @Required
    public String titulo;

    @Required
    public Date fechaSubida;

    @Lob
    @Required
    @MaxSize(10000)
    public String content;

    @Lob
    @Required
    @MaxSize(10000)
    public String imagen;

    @ManyToOne
    public Usuario author;

    @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL)
    public List<Comentario> comentarios;

    public Noticia(Usuario author, String titulo, String content, String imagen) {

        this.comentarios = new ArrayList<Comentario>();
        this.author = author;
        this.titulo = titulo;
        this.content = content;
        this.imagen = imagen;

        this.fechaSubida = new Date();
    }

    public Noticia agregarComentario(String author, String content) {
        Comentario nuevoComentario = new Comentario(this, author, content).save();
        this.comentarios.add(nuevoComentario);
        this.save();
        return this;
    }

    public Noticia previous() {
        return Noticia.find("fechaSubida < ? order by fechaSubida desc", fechaSubida).first();
    }

    public Noticia next() {
        return Noticia.find("fechaSubida > ? order by fechaSubida asc", fechaSubida).first();
    }

    public String toString() {
        return titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Usuario getAuthor() {
        return author;
    }

    public void setAuthor(Usuario author) {
        this.author = author;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    
    
    
}
