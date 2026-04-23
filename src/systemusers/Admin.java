package systemusers;

public class Admin extends Librarian {

    // ===== Constructeur sans ID =====
    public Admin(String nom, String motDePasse) {
        super(nom, motDePasse);
    }

    // ===== Constructeur avec ID =====
    public Admin(int id, String nom, String motDePasse,String role) {
        super(id, nom, motDePasse,role);
    }
}

 