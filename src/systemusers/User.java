package systemusers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookpackage.Book;
import bookpackage.Emprunt;
import dao.BookDAO;
import dao.BookEmpruntDAO;
import dao.UserDAO; 


public class User {

    private int id;
    private String nomUser;
    private String motDePasse;
    private String role ;
    // ===== Constructeur sans ID (création) =====
    public User(String nomUser, String motDePasse) {
        this.nomUser = nomUser;
        this.motDePasse = motDePasse;
    }

    // ===== Constructeur avec ID (depuis la BDD) =====
    public User(int id, String nomUser, String motDePasse ,String role ) {
        this.id = id;
        this.nomUser = nomUser;
        this.motDePasse = motDePasse;
        this.setRole(role) ;
        
    }

    // ===== Getters / Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPassword() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
