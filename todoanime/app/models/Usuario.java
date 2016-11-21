package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
import play.data.validation.*;
 
@Entity
public class Usuario extends Model {
 
 	@Email
    @Required
    public String email;

    @Required
    public String password;
   
    public String nombreCompleto;
    public boolean isAdmin;
    public boolean isPeriodista;
    
    public Usuario(String email, String password, String nombreCompleto) {
        this.email = email;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
    }

    //METODO QUE REALIZA LA CONEXION
    public static Usuario connect(String email, String password) {

    	return find("byEmailAndPassword", email, password).first();
	
	}

    public Usuario(String email, String password, String nombreCompleto, boolean isAdmin, boolean isPeriodista) {
        this.email = email;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.isAdmin = isAdmin;
        this.isPeriodista = isPeriodista;
    }
    

    public Usuario() {
        
    }

	public String toString() {
    	return nombreCompleto;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsPeriodista() {
        return isPeriodista;
    }

    public void setIsPeriodista(boolean isPeriodista) {
        this.isPeriodista = isPeriodista;
    }

        
}
 
