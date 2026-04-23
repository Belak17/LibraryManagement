package systemusers;

public class Librarian extends User {

    // ===== Constructeur sans ID =====
    public Librarian(String nom, String motDePasse) {
        super(nom, motDePasse);
    }

    // ===== Constructeur avec ID =====
    public Librarian(int id, String nom, String motDePasse, String role) {
        super(id, nom, motDePasse,role);
    }
}
