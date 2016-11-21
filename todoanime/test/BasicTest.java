import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {


	@Before
    public void setup() { //ESTE METODO BORRA TODAS LAS PRUEBAS ANTERIORES PARA EMPEZAR EN BLANCO LAS NUEVAS
    
    	    Fixtures.deleteAll();
    }


    /*@Test
	public void crearYrecuperarUsuario() { //PRUEBA PARA CREAR UN USUARIO Y GUARDARLO
    
    	new Usuario("alexcrest@hotmail.com", "orosco69", "Alexis").save(); //LO CREO Y LO GUARDO
    	Usuario alexes = Usuario.find("byEmail", "alexcrest@hotmail.com").first(); //LO BUSCO
    	
    	//TESTEO
    	assertNotNull(alexes);
    	assertEquals("Alexis", alexes.nombreCompleto);

	}*/


	/*@Test
	public void conectarseComoUsuario() { 	//VERIFICAR SI EXISTE UN USUARIO, REFERENCIANDO EMAIL Y CONTRASEÑA
    
    	new Usuario("alexcrest@hotmail.com", "orosco69", "Alexis").save();
    	
    	// TESTEO 
    	assertNotNull(Usuario.connect("alexcrest@hotmail.com", "orosco69"));
    	assertNull(Usuario.connect("alexcrest@hotmail.com", "malcontrasenia"));
    	assertNull(Usuario.connect("malEmail@hotmail.com", "orosco69"));

	}*/


	/*@Test
	public void crearNoticia() {
  
    	Usuario alexis = new Usuario("alexcrest@hotmail.com", "orosco69", "Alexis").save(); //CREO UN USUARIO NUEVO Y LO GUARDO
    	new Noticia(alexis, "Mi primera noticia", "Este es el contenido de la primera noticia", "URL DE LA IMAGEN").save();
    
    	assertEquals(1, Noticia.count());
    
    	//LISTO TODAS LAS NOTICIAS CREADAS POR ALEXIS
    	List<Noticia> alexisNoticias = Noticia.find("byAuthor", alexis).fetch();
    
    	// TESTEO
    	assertEquals(1, alexisNoticias.size());
    	Noticia primeraNoticia = alexisNoticias.get(0);
    	assertNotNull(primeraNoticia);
    	assertEquals(alexis, primeraNoticia.author);
    	assertEquals("Mi primera noticia", primeraNoticia.titulo);
    	assertEquals("Este es el contenido de la primera noticia", primeraNoticia.content);
    	assertNotNull(primeraNoticia.fechaSubida);

	}*/



	/*@Test
	public void haciendoComentarios() {

    	Usuario alexis = new Usuario("alexcrest@hotmail.com", "orosco69", "Alexis").save(); //CREO UN USUARIO NUEVO Y LO GUARDO
    	Noticia noticiaDeAlexis = new Noticia(alexis, "Mi primera noticia", "Este es el contenido de la primera noticia", "URL DE LA IMAGEN").save();
 
    	new Comentario(noticiaDeAlexis, "Omar", "Buena noticia papuh").save();
    	new Comentario(noticiaDeAlexis, "Alexis", "Gracias papu, es mi mejor noticia hasta el momento").save();
 

    	//AL IGUAL QUE LAS NOTICIAS, LISTO TODOS LOS COMENTARIOS EN LA NOTICIA ESPECIFICA
    	List<Comentario> alexisNoticiasComentarios = Comentario.find("byNoticia", noticiaDeAlexis).fetch();
 
    	//TESTEO
    	assertEquals(2, alexisNoticiasComentarios.size());
 
    	Comentario primerComentario = alexisNoticiasComentarios.get(0);
    	assertNotNull(primerComentario);
    	assertEquals("Omar", primerComentario.author);
    	assertEquals("Buena noticia papuh", primerComentario.content);
    	assertNotNull(primerComentario.fechaSubida);
 
    	Comentario segundoComentario = alexisNoticiasComentarios.get(1);
    	assertNotNull(segundoComentario);
    	assertEquals("Alexis", segundoComentario.author);
    	assertEquals("Gracias papu, es mi mejor noticia hasta el momento", segundoComentario.content);
    	assertNotNull(segundoComentario.fechaSubida);
	
	}*/



	/*@Test
	public void usarLaRelacionComentarios() {
    
   		Usuario alexis = new Usuario("alexcrest@hotmail.com", "orosco69", "Alexis").save();
 		Noticia noticiaDeAlexis = new Noticia(alexis, "Mi primera noticia", "Este es el contenido de la primera noticia", "URL DE LA IMAGEN").save();
    
    	noticiaDeAlexis.agregarComentario("Omar", "Buena noticia papuh");
    	noticiaDeAlexis.agregarComentario("Aleixs", "Gracias papu, es mi primer noticia");
 
    	assertEquals(1, Usuario.count());
    	assertEquals(1, Noticia.count());
    	assertEquals(2, Comentario.count());
 
    	//RECUPERAR LA NOTICIA DE ALEXIS
    	noticiaDeAlexis = Noticia.find("byAuthor", alexis).first();
    	assertNotNull(noticiaDeAlexis);
 
    	//NAVEGAR POR LOS COMENTARIOS
    	assertEquals(2, noticiaDeAlexis.comentarios.size());
    	assertEquals("Omar", noticiaDeAlexis.comentarios.get(0).author);
    
    	//BORRAR LA NOTICIA
    	noticiaDeAlexis.delete();
    
    	//VERIFICAR QUE TODOS LOS COMENTARIOS SE HAYAN BORRADO AL BORRAR LA NOTICIA
    	assertEquals(1, Usuario.count());
    	assertEquals(0, Noticia.count());
    	assertEquals(0, Comentario.count());
	
	}*/



	@Test
	public void fullTest() { //ESTE TEST TRABAJA CON LA DATA.YML Y HACER LOS ASSRTS AUTOMATICAMENTE SEGUN LA JERGA QUE SE LE APLIQUE
    Fixtures.load("data.yml");
 
    	assertEquals(2, Usuario.count());
    	assertEquals(3, Noticia.count());
    	assertEquals(3, Comentario.count());
 
    	assertNotNull(Usuario.connect("alexis@hotmail.com", "orosco69"));
    	assertNotNull(Usuario.connect("omarsito@hotmail.com", "orosco69"));
    	assertNull(Usuario.connect("jeff@gmail.com", "badpassword"));
    	assertNull(Usuario.connect("tom@gmail.com", "orosco69"));
 
    	//BUSCAR TODAS LAS NOTICAS DE ALEXIS
    	List<Noticia> noticiasDeAlexis = Noticia.find("author.email", "alexis@hotmail.com").fetch();
    	assertEquals(2, noticiasDeAlexis.size());
 
    	//BUSCAR TODOS LOS COMENTARIOS HECHOS EN LAS NOTICIAS DE ALEXIS
    	List<Comentario> alexisNoticiasComentarios = Comentario.find("noticia.author.email", "alexis@hotmail.com").fetch();
    	assertEquals(3, alexisNoticiasComentarios.size());
 
    	//BUSCAR LA NOTICIA MAS RECIENTE
    	Noticia frontNoticia = Noticia.find("order by fechaSubida desc").first();
    	assertNotNull(frontNoticia);
    	assertEquals("Acerca del modelo de este trabajo", frontNoticia.titulo);
 
    	//VERIFICAR QUE ESTE POST TENGA DOS COMENTARIOS
    	assertEquals(2, frontNoticia.comentarios.size());
 
    	//AGREGAR UN NUEVO COMENTARIO
    	frontNoticia.agregarComentario("Jim", "Buena noticia amigos");
    	assertEquals(3, frontNoticia.comentarios.size());
    	assertEquals(4, Comentario.count());
}



}
