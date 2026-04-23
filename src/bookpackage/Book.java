package bookpackage;

public class Book {

    private int id;
    private String nomLivre;
    private String nomAuteur;
    private String langue;
    private String description;
    private int anneeEdition;

    // ===== Constructeur sans ID (insertion) =====
    public Book(String nomLivre,
                String nomAuteur,
                String langue,
                String description,
                int anneeEdition) {

        this.nomLivre = nomLivre;
        this.nomAuteur = nomAuteur;
        this.langue = langue;
        this.description = description;
        this.anneeEdition = anneeEdition;
    }

    // ===== Constructeur avec ID (depuis la BDD) =====
    public Book(int id,
                String nomLivre,
                String nomAuteur,
                String langue,
                String description,
                int anneeEdition) {

        this.id = id;
        this.nomLivre = nomLivre;
        this.nomAuteur = nomAuteur;
        this.langue = langue;
        this.description = description;
        this.anneeEdition = anneeEdition;
    }

    // ===== Getters / Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return nomLivre;
    }

    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }

    public String getAuteur() {
        return nomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAnnee() {
        return anneeEdition;
    }

    public void setAnneeEdition(int anneeEdition) {
        this.anneeEdition = anneeEdition;
    }

	public String getImagePath() {
		// TODO Auto-generated method stub
		return null;
	}


}
